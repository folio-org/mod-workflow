package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import org.folio.rest.workflow.enums.HttpMethod;
import org.folio.rest.workflow.model.has.HasUrl;
import org.folio.rest.workflow.model.has.common.HasEmbeddedRequestCommon;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.http.MediaType;

@Embeddable
public class EmbeddedRequest implements HasEmbeddedRequestCommon, HasUrl {

  /**
   * The default accept value.
   */
  static final String DEFAULT_ACCEPT = MediaType.APPLICATION_JSON_VALUE;

  /**
   * The default contentType value.
   */
  static final String DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

  /**
   * The default iterable value.
   */
  static final boolean DEFAULT_ITERABLE = false;

  /**
   * The default method value.
   */
  static final HttpMethod DEFAULT_METHOD = HttpMethod.GET;

  /**
   * The default sendEmptyBody value.
   */
  static final boolean DEFAULT_SEND_EMPTY_BODY = true;

  // Must be designated as nullable even if this is not supposed to be NULL.
  @Column(nullable = true)
  @ColumnDefault("'" + DEFAULT_ACCEPT + "'")
  private String accept;

  @Column(columnDefinition = "TEXT", nullable = true)
  private String bodyTemplate;

  // Must be designated as nullable even if this is not supposed to be NULL.
  @Column(nullable = true)
  @ColumnDefault("'" + DEFAULT_CONTENT_TYPE + "'")
  private String contentType;

  @Column(nullable = false)
  @ColumnDefault("" + DEFAULT_ITERABLE)
  private boolean iterable;

  private String iterableKey;

  // Must be designated as nullable even if this is not supposed to be NULL.
  @Column(nullable = true)
  @ColumnDefault("'GET'")
  @Enumerated(EnumType.STRING)
  private HttpMethod method;

  private String responseKey;

  @Column(nullable = false)
  @ColumnDefault("" + DEFAULT_SEND_EMPTY_BODY)
  private Boolean sendEmptyBody;

  // Must be designated as nullable even if this is not supposed to be NULL.
  @Column(nullable = true)
  private String url;

  public EmbeddedRequest() {
    super();

    accept = DEFAULT_ACCEPT;
    bodyTemplate = "{}";
    contentType = DEFAULT_CONTENT_TYPE;
    iterable = DEFAULT_ITERABLE;
    method = DEFAULT_METHOD;
    sendEmptyBody = DEFAULT_SEND_EMPTY_BODY;
    url = "";
  }

  @PrePersist
  public void prePersist() {
    if (accept == null) {
      accept = DEFAULT_ACCEPT;
    }

    if (sendEmptyBody == null) {
      sendEmptyBody = DEFAULT_SEND_EMPTY_BODY;
    }

    if (bodyTemplate == null || bodyTemplate.isEmpty()) {
      bodyTemplate = Boolean.TRUE.equals(sendEmptyBody) ? "{}" : null;
    }

    if (contentType == null) {
      contentType = DEFAULT_CONTENT_TYPE;
    }

    if (method == null) {
      method = DEFAULT_METHOD;
    }

    if (url == null) {
      url = "";
    }
  }

  @Override
  public String getAccept() {
    return accept;
  }

  @Override
  public String getBodyTemplate() {
    return bodyTemplate;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public boolean getIterable() {
    return iterable;
  }

  @Override
  public String getIterableKey() {
    return iterableKey;
  }

  @Override
  public Boolean getSendEmptyBody() {
    return sendEmptyBody;
  }

  @Override
  public void setAccept(String accept) {
    this.accept = accept;
  }

  @Override
  public void setBodyTemplate(String bodyTemplate) {
    this.bodyTemplate = bodyTemplate;
  }

  @Override
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  @Override
  public void setIterable(boolean iterable) {
    this.iterable = iterable;
  }

  @Override
  public void setIterableKey(String iterableKey) {
    this.iterableKey = iterableKey;
  }

  @Override
  public HttpMethod getMethod() {
    return method;
  }

  @Override
  public String getResponseKey() {
    return responseKey;
  }

  @Override
  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  @Override
  public void setResponseKey(String responseKey) {
    this.responseKey = responseKey;
  }

  @Override
  public String getUrl() {
    return url;
  }

  @Override
  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public void setSendEmptyBody(Boolean sendEmptyBody) {
    this.sendEmptyBody = sendEmptyBody;
  }

}
