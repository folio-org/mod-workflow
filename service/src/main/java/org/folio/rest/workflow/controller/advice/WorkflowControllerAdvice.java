package org.folio.rest.workflow.controller.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EntityNotFoundException;
import org.folio.rest.workflow.exception.WorkflowAlreadyActiveException;
import org.folio.rest.workflow.exception.WorkflowCreateAlreadyExistsException;
import org.folio.rest.workflow.exception.WorkflowDeploymentException;
import org.folio.rest.workflow.exception.WorkflowEngineServiceException;
import org.folio.rest.workflow.exception.WorkflowImportException;
import org.folio.rest.workflow.exception.WorkflowNotFoundException;
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
public class WorkflowControllerAdvice extends AbstractAdvice {

  JsonMapper mapper;

  public WorkflowControllerAdvice() {
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

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exception) {
    return buildError(exception, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({ WorkflowCreateAlreadyExistsException.class })
  public ResponseEntity<String> handleWorkflowCreateAlreadyExistsException(WorkflowCreateAlreadyExistsException ex) {
    return buildError(ex, HttpStatus.CONFLICT);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(WorkflowNotFoundException.class)
  public ResponseEntity<String> handleWorkflowNotFoundException(WorkflowNotFoundException exception) {
    return buildError(exception, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler(WorkflowAlreadyActiveException.class)
  public ResponseEntity<String> handleWorkflowAlreadyActiveException(WorkflowAlreadyActiveException exception) {
    return buildError(exception, HttpStatus.FORBIDDEN);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(WorkflowDeploymentException.class)
  public ResponseEntity<String> handleWorkflowDeploymentException(WorkflowDeploymentException exception) {
    return buildError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(WorkflowEngineServiceException.class)
  public ResponseEntity<String> handleWorkflowEngineServiceException(WorkflowEngineServiceException exception) {
    return buildError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(WorkflowImportException.class)
  public ResponseEntity<String> handleWorkflowImportException(WorkflowImportException exception) {
    return buildError(exception, HttpStatus.BAD_REQUEST);
  }

}
