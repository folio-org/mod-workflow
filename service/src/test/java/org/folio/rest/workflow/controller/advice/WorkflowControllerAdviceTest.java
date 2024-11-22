package org.folio.rest.workflow.controller.advice;

import static org.folio.spring.test.mock.MockMvcConstant.APP_JSON;
import static org.folio.spring.test.mock.MockMvcConstant.JSON_OBJECT;
import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_HEAD;
import static org.folio.spring.test.mock.MockMvcConstant.UUID;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.folio.spring.test.mock.MockMvcRequest.appendBody;
import static org.folio.spring.test.mock.MockMvcRequest.appendHeaders;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.folio.rest.workflow.controller.WorkflowController;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WorkflowControllerAdviceTest {

  private static final String PATH = "/workflows";

  private static final String PATH_ACTIVATE = PATH + "/{id}/activate";

  @Autowired
  private WorkflowControllerAdvice workflowControllerAdvice;

  @Autowired
  @Mock
  private WorkflowController workflowController;

  private MockMvc mvc;

  @BeforeEach
  void beforeEach() {
    mvc = MockMvcBuilders.standaloneSetup(workflowController)
      .setControllerAdvice(workflowControllerAdvice)
      .build();
  }

  @ParameterizedTest
  @MethodSource("provideExceptionsToMatchForActivateWorkflow")
  void exceptionsThrownForActivateWorkflowTest(Exception exception, String simpleName, int status) throws Exception {
    given(workflowController.activateWorkflow(any(), any(), any())).willAnswer(invocation -> { throw exception; });

    MockHttpServletRequestBuilder request = appendHeaders(put(PATH_ACTIVATE, UUID), OKAPI_HEAD, APP_JSON, APP_JSON);

    MvcResult result = mvc.perform(appendBody(request, JSON_OBJECT))
      .andDo(log()).andExpect(status().is(status)).andReturn();

    Pattern pattern = Pattern.compile("\"type\":\"" + simpleName + "\"");
    Matcher matcher = pattern.matcher(result.getResponse().getContentAsString());
    assertTrue(matcher.find());
  }

  /**
   * Helper function for parameterized test providing the exceptions to be matched for activate workflow.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Exception exception.
   *     - String simpleName (exception name to match).
   *     - int status (response HTTP status code for the exception).
   */
  private static Stream<Arguments> provideExceptionsToMatchForActivateWorkflow() {
    return Stream.of(
      Arguments.of(new WorkflowNotFoundException(VALUE), WorkflowNotFoundException.class.getSimpleName(), 404),
      Arguments.of(new EntityNotFoundException(), EntityNotFoundException.class.getSimpleName(), 404),
      Arguments.of(new WorkflowAlreadyActiveException(VALUE), WorkflowAlreadyActiveException.class.getSimpleName(), 403),
      Arguments.of(new WorkflowDeploymentException(), WorkflowDeploymentException.class.getSimpleName(), 500),
      Arguments.of(new WorkflowEngineServiceException(VALUE), WorkflowEngineServiceException.class.getSimpleName(), 500),
      Arguments.of(new WorkflowImportException(VALUE), WorkflowImportException.class.getSimpleName(), 400),
      Arguments.of(new WorkflowImportAlreadyImported(VALUE), WorkflowImportAlreadyImported.class.getSimpleName(), 400),
      Arguments.of(new WorkflowImportInvalidOrMissingProperty(VALUE, VALUE), WorkflowImportInvalidOrMissingProperty.class.getSimpleName(), 400),
      Arguments.of(new WorkflowImportJsonFileIsDirectory(VALUE), WorkflowImportJsonFileIsDirectory.class.getSimpleName(), 400),
      Arguments.of(new WorkflowImportRequiredFileMissing(VALUE), WorkflowImportRequiredFileMissing.class.getSimpleName(), 400)
    );
  }

}
