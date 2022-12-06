package org.folio.rest.workflow.controller.advice;

import java.nio.file.FileSystemException;

import org.folio.rest.workflow.exception.EventPublishException;
import org.folio.spring.model.response.ResponseErrors;
import org.folio.spring.utility.ErrorUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EventControllerAdvice {

  private static final Logger logger = LoggerFactory.getLogger(EventControllerAdvice.class);

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(EventPublishException.class)
  public ResponseErrors handleEventPublishException(EventPublishException exception) {
    logger.debug(exception.getMessage(), exception);
    return ErrorUtility.buildError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(FileSystemException.class)
  public ResponseErrors handleFileSystemException(FileSystemException exception) {
    logger.debug(exception.getMessage(), exception);
    return ErrorUtility.buildError(exception, HttpStatus.BAD_REQUEST);
  }

}
