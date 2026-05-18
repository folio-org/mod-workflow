package org.folio.rest.workflow.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class Request {

  @NotNull
  private String url;

  @NotNull
  private HttpMethod method;

  @NotNull
  private String contentType;

  @NotNull
  private String accept;

  private String bodyTemplate;

  private boolean iterable;

  private String iterableKey;

  private String responseKey;

  public Request() {
    super();
    contentType = MediaType.APPLICATION_JSON_VALUE;
    accept = MediaType.APPLICATION_JSON_VALUE;
    bodyTemplate = "{}";
    iterable = false;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return the method
   */
  public HttpMethod getMethod() {
    return method;
  }

  /**
   * @return the contentType
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * @return the accept
   */
  public String getAccept() {
    return accept;
  }

  /**
   * @return the bodyTemplate
   */
  public String getBodyTemplate() {
    return bodyTemplate;
  }

  /**
   * @return the iterable
   */
  public boolean isIterable() {
    return iterable;
  }

  /**
   * @return the iterableKey
   */
  public String getIterableKey() {
    return iterableKey;
  }

  /**
   * @return the responseKey
   */
  public String getResponseKey() {
    return responseKey;
  }

  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @param method the method to set
   */
  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  /**
   * @param contentType the contentType to set
   */
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  /**
   * @param accept the accept to set
   */
  public void setAccept(String accept) {
    this.accept = accept;
  }

  /**
   * @param bodyTemplate the bodyTemplate to set
   */
  public void setBodyTemplate(String bodyTemplate) {
    this.bodyTemplate = bodyTemplate;
  }

  /**
   * @param iterable the iterable to set
   */
  public void setIterable(boolean iterable) {
    this.iterable = iterable;
  }

  /**
   * @param iterableKey the iterableKey to set
   */
  public void setIterableKey(String iterableKey) {
    this.iterableKey = iterableKey;
  }

  /**
   * @param responseKey the responseKey to set
   */
  public void setResponseKey(String responseKey) {
    this.responseKey = responseKey;
  }

}
