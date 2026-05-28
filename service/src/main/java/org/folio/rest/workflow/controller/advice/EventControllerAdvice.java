package org.folio.rest.workflow.controller.advice;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.nio.file.FileSystemException;
import org.folio.rest.workflow.exception.EventPublishException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

@RestControllerAdvice
public class EventControllerAdvice extends AbstractAdvice {

  JsonMapper mapper;

  public EventControllerAdvice() {
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

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(EventPublishException.class)
  public ResponseEntity<String>  handleEventPublishException(EventPublishException exception) {
    return buildError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(FileSystemException.class)
  public ResponseEntity<String>  handleFileSystemException(FileSystemException exception) {
    return buildError(exception, HttpStatus.BAD_REQUEST);
  }

}
