package org.folio.rest.workflow.controller.advice;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.FileSystemException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.folio.rest.workflow.exception.EventPublishException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EventControllerAdviceTest {

  private static final Exception RUNTIME_EXC = new RuntimeException("Runtime failure.");

  private static final EventPublishException EP_EXC = new EventPublishException(VALUE, RUNTIME_EXC);

  private static final FileSystemException FS_EXC = new FileSystemException(VALUE);

  private EventControllerAdvice advice;

  @BeforeEach
  void beforeEach() {
    advice = new EventControllerAdvice();
  }

  @Test
  void handleEventPublishExceptionTest() {

    final String simpleName = EventPublishException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleEventPublishException(EP_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertTrue(matchBody(response, simpleName));
  }

  @Test
  void handleFileSystemExceptionTest() {

    final String simpleName = FileSystemException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleFileSystemException(FS_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertTrue(matchBody(response, simpleName));
  }

  /**
   * Match the class simple name in the response.
   *
   * @param response The response to search.
   * @param simpleName The class name to match.
   *
   * @return TRUE on match; FALSE otherwise.
   */
  private boolean matchBody(ResponseEntity<String> response, String simpleName) {

    final Pattern pattern = Pattern.compile("\"type\":\"" + simpleName + "\"");
    final Matcher matcher = pattern.matcher(response.getBody());

    return matcher.find();
  }

}
