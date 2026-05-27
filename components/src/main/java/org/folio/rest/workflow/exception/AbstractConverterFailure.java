package org.folio.rest.workflow.exception;

/**
 * Failure in the AbstractConverter.
 */
public class AbstractConverterFailure extends RuntimeException {

  private static final long serialVersionUID = 1062422697623L;

  public AbstractConverterFailure(String message) {
    super(message);
  }

  public AbstractConverterFailure(String message, Exception e) {
    super(message, e);
  }

}
