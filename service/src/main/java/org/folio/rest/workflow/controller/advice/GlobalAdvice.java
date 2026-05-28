package org.folio.rest.workflow.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

@RestControllerAdvice
public class GlobalAdvice extends AbstractAdvice {

  JsonMapper mapper;

  public GlobalAdvice() {
    this.mapper = JsonMapper
      .builderWithJackson2Defaults()
      .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES, true)
      .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
      .configure(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION, true)
      .changeDefaultPropertyInclusion(incl -> incl
        .withValueInclusion(JsonInclude.Include.NON_NULL)
        .withContentInclusion(JsonInclude.Include.NON_NULL)
      )
      .findAndAddModules()
      .build();
  }

  @Override
  protected JsonMapper getMapper() {
    return mapper;
  }

  @ExceptionHandler({ TransactionSystemException.class })
  public ResponseEntity<String> handleConstraintViolation(TransactionSystemException ex) {
    return buildError((Exception) ex.getRootCause(), HttpStatus.BAD_REQUEST);
  }

}
