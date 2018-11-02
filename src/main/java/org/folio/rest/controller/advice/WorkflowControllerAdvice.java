package org.folio.rest.controller.advice;

import org.folio.rest.exception.CamundaServiceException;
import org.folio.rest.exception.WorkflowDeploymentException;
import org.folio.rest.exception.WorkflowNotFoundException;
import org.folio.rest.model.response.Errors;
import org.folio.rest.utility.ErrorUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WorkflowControllerAdvice {

  private static final Logger logger = LoggerFactory.getLogger(WorkflowControllerAdvice.class);

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(WorkflowNotFoundException.class)
  public Errors handleWorkflowNotFoundException(WorkflowNotFoundException exception) {
    logger.debug(exception.getMessage(), exception);
    return ErrorUtility.buildError(exception, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(WorkflowDeploymentException.class)
  public Errors handleWorkflowDeploymentException(WorkflowDeploymentException exception) {
    logger.debug(exception.getMessage(), exception);
    return ErrorUtility.buildError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(CamundaServiceException.class)
  public Errors handleCamundaServiceException(CamundaServiceException exception) {
    logger.debug(exception.getMessage(), exception);
    return ErrorUtility.buildError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
