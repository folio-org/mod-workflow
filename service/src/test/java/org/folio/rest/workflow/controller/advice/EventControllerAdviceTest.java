package org.folio.rest.workflow.controller.advice;

import static org.folio.spring.test.mock.MockMvcConstant.APP_JSON;
import static org.folio.spring.test.mock.MockMvcConstant.JSON_OBJECT;
import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_HEAD;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.folio.spring.test.mock.MockMvcRequest.appendBody;
import static org.folio.spring.test.mock.MockMvcRequest.appendHeaders;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.FileSystemException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.folio.rest.workflow.controller.EventController;
import org.folio.rest.workflow.exception.EventPublishException;
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
class EventControllerAdviceTest {

  private static final String PATH = "/events";

  private static final String PATH_HANDLE = PATH + "/handle";

  @Autowired
  private EventControllerAdvice eventControllerAdvice;

  @Autowired
  @Mock
  private EventController eventController;

  private MockMvc mvc;

  @BeforeEach
  void beforeEach() {
    mvc = MockMvcBuilders.standaloneSetup(eventController)
      .setControllerAdvice(eventControllerAdvice)
      .build();
  }

  @ParameterizedTest
  @MethodSource("provideExceptionsToMatchForActivateWorkflow")
  void exceptionsThrownForActivateWorkflowTest(Exception exception, String simpleName, int status) throws Exception {
    given(eventController.postHandleEvents(any(), any())).willAnswer(invocation -> { throw exception; });

    MockHttpServletRequestBuilder request = appendHeaders(post(PATH_HANDLE), OKAPI_HEAD, APP_JSON, APP_JSON);

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
    Exception runtime = new RuntimeException("Runtime failure.");

    return Stream.of(
      Arguments.of(new EventPublishException(VALUE, runtime), EventPublishException.class.getSimpleName(), 500),
      Arguments.of(new FileSystemException(VALUE), FileSystemException.class.getSimpleName(), 400)
    );
  }

}
