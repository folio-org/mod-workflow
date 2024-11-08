package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.folio.rest.workflow.enums.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EmbeddedRequestTest {

  private EmbeddedRequest embeddedRequest;

  @BeforeEach
  void beforeEach() {
    embeddedRequest = new EmbeddedRequest();
  }

  @Test
  void getUrlWorksTest() {
    setField(embeddedRequest, "url", VALUE);

    assertEquals(VALUE, embeddedRequest.getUrl());
  }

  @Test
  void setUrlWorksTest() {
    setField(embeddedRequest, "url", null);

    embeddedRequest.setUrl(VALUE);
    assertEquals(VALUE, getField(embeddedRequest, "url"));
  }

  @Test
  void getMethodWorksTest() {
    setField(embeddedRequest, "method", HttpMethod.DELETE);

    assertEquals(HttpMethod.DELETE, embeddedRequest.getMethod());
  }

  @Test
  void setMethodWorksTest() {
    setField(embeddedRequest, "method", null);

    embeddedRequest.setMethod(HttpMethod.DELETE);
    assertEquals(HttpMethod.DELETE, getField(embeddedRequest, "method"));
  }

  @Test
  void getContentTypeWorksTest() {
    setField(embeddedRequest, "contentType", VALUE);

    assertEquals(VALUE, embeddedRequest.getContentType());
  }

  @Test
  void setContentTypeWorksTest() {
    setField(embeddedRequest, "contentType", null);

    embeddedRequest.setContentType(VALUE);
    assertEquals(VALUE, getField(embeddedRequest, "contentType"));
  }

  @Test
  void getAcceptWorksTest() {
    setField(embeddedRequest, "accept", VALUE);

    assertEquals(VALUE, embeddedRequest.getAccept());
  }

  @Test
  void setAcceptWorksTest() {
    setField(embeddedRequest, "accept", null);

    embeddedRequest.setAccept(VALUE);
    assertEquals(VALUE, getField(embeddedRequest, "accept"));
  }

  @Test
  void getBodyTemplateWorksTest() {
    setField(embeddedRequest, "bodyTemplate", VALUE);

    assertEquals(VALUE, embeddedRequest.getBodyTemplate());
  }

  @Test
  void setBodyTemplateWorksTest() {
    setField(embeddedRequest, "bodyTemplate", null);

    embeddedRequest.setBodyTemplate(VALUE);
    assertEquals(VALUE, getField(embeddedRequest, "bodyTemplate"));
  }

  @Test
  void getIterableWorksTest() {
    setField(embeddedRequest, "iterable", true);

    assertEquals(true, embeddedRequest.isIterable());
  }

  @Test
  void setIterableWorksTest() {
    setField(embeddedRequest, "iterable", false);

    embeddedRequest.setIterable(true);
    assertEquals(true, getField(embeddedRequest, "iterable"));
  }

  @Test
  void getResponseKeyWorksTest() {
    setField(embeddedRequest, "responseKey", VALUE);

    assertEquals(VALUE, embeddedRequest.getResponseKey());
  }

  @Test
  void setResponseKeyWorksTest() {
    setField(embeddedRequest, "responseKey", null);

    embeddedRequest.setResponseKey(VALUE);
    assertEquals(VALUE, getField(embeddedRequest, "responseKey"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(embeddedRequest, attribute, value);
    });

    embeddedRequest.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(embeddedRequest, attribute));
    });
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect The expected values.
   */
  private static Stream<Arguments> providePrePersistFor() {
    final String APPLICATION_JSON_VALUE = "application/json";
    final String url = "url";

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,  null, null, null, null),
        helperFieldMap(APPLICATION_JSON_VALUE, "{}", APPLICATION_JSON_VALUE, HttpMethod.GET, "")
      ),
      Arguments.of(
        helperFieldMap(APPLICATION_JSON_VALUE,  null , null, HttpMethod.POST, url),
        helperFieldMap(APPLICATION_JSON_VALUE, "{}", APPLICATION_JSON_VALUE, HttpMethod.POST, url)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param accept The accept value.
   * @param bodyTemplate The bodyTemplate value.
   * @param contentType The contentType value.
   * @param method The method value.
   * @param url The url value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String accept, String bodyTemplate, String contentType, HttpMethod method, String url) {
    final Map<String, Object> map = new HashMap<>();

    map.put("accept", accept);
    map.put("bodyTemplate", bodyTemplate);
    map.put("contentType", contentType);
    map.put("method", method);
    map.put("url", url);

    return map;
  }

}
