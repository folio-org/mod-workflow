package org.folio.rest.workflow.controller.advice;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.folio.rest.workflow.exception.WorkflowAlreadyActiveException;
import org.folio.rest.workflow.exception.WorkflowDeploymentException;
import org.folio.rest.workflow.exception.WorkflowEngineServiceException;
import org.folio.rest.workflow.exception.WorkflowImportAlreadyImported;
import org.folio.rest.workflow.exception.WorkflowImportException;
import org.folio.rest.workflow.exception.WorkflowImportInvalidOrMissingProperty;
import org.folio.rest.workflow.exception.WorkflowImportJsonFileIsDirectory;
import org.folio.rest.workflow.exception.WorkflowImportRequiredFileMissing;
import org.folio.rest.workflow.exception.WorkflowNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WorkflowControllerAdviceTest {

  private static final EntityNotFoundException ENF_EXC = new EntityNotFoundException(VALUE);

  private static final WorkflowNotFoundException WNF_EXC = new WorkflowNotFoundException(VALUE);

  private static final WorkflowAlreadyActiveException WAA_EXC = new WorkflowAlreadyActiveException(VALUE);

  private static final WorkflowDeploymentException WD_EXC = new WorkflowDeploymentException();

  private static final WorkflowEngineServiceException WES_EXC = new WorkflowEngineServiceException(VALUE);

  private static final WorkflowImportException WI_EXC = new WorkflowImportException(VALUE);

  private static final WorkflowImportAlreadyImported WIAI_EXC = new WorkflowImportAlreadyImported(VALUE);

  private static final WorkflowImportInvalidOrMissingProperty WIIOMP_EXC = new WorkflowImportInvalidOrMissingProperty(VALUE, VALUE);

  private static final WorkflowImportJsonFileIsDirectory WIJFID_EXC = new WorkflowImportJsonFileIsDirectory(VALUE);

  private static final WorkflowImportRequiredFileMissing WIRFM_EXC = new WorkflowImportRequiredFileMissing(VALUE);

  private WorkflowControllerAdvice advice;

  @BeforeEach
  void beforeEach() {
    advice = new WorkflowControllerAdvice();
  }

  @Test
  void handleEntityNotFoundExceptionTest() {

    final String simpleName = EntityNotFoundException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleEntityNotFoundException(ENF_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    assertTrue(matchBody(response, simpleName));
  }

  @Test
  void handleWorkflowNotFoundExceptionTest() {

    final String simpleName = WorkflowNotFoundException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleWorkflowNotFoundException(WNF_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertTrue(matchBody(response, simpleName));
  }

  @Test
  void handleWorkflowAlreadyActiveExceptionTest() {

    final String simpleName = WorkflowAlreadyActiveException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleWorkflowAlreadyActiveException(WAA_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertTrue(matchBody(response, simpleName));
  }

  @Test
  void handleWorkflowDeploymentExceptionTest() {

    final String simpleName = WorkflowDeploymentException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleWorkflowDeploymentException(WD_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertTrue(matchBody(response, simpleName));
  }

  @Test
  void handleWorkflowEngineServiceExceptionTest() {

    final String simpleName = WorkflowEngineServiceException.class.getSimpleName();
    final ResponseEntity<String> response = advice.handleWorkflowEngineServiceException(WES_EXC);

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
    assertTrue(matchBody(response, simpleName));
  }

  @ParameterizedTest
  @MethodSource("provideWorkflowImportExceptions")
  void handleWorkflowImportExceptionTest(WorkflowImportException exception, String simpleName) {

    final ResponseEntity<String> response = advice.handleWorkflowImportException(exception);

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

  /**
   * Helper function for parameterized test providing different types of WorkflowImportException.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Exception exception.
   *     - String simpleName (exception name to match).
   */
  private static Stream<Arguments> provideWorkflowImportExceptions() {

    return Stream.of(
      Arguments.of(WIAI_EXC,   WorkflowImportAlreadyImported.class.getSimpleName()),
      Arguments.of(WIIOMP_EXC, WorkflowImportInvalidOrMissingProperty.class.getSimpleName()),
      Arguments.of(WIJFID_EXC, WorkflowImportJsonFileIsDirectory.class.getSimpleName()),
      Arguments.of(WIRFM_EXC,  WorkflowImportRequiredFileMissing.class.getSimpleName())
    );
  }


}
