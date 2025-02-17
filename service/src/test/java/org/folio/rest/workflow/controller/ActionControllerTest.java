package org.folio.rest.workflow.controller;

import static org.folio.spring.test.mock.MockMvcConstant.APP_JSON;
import static org.folio.spring.test.mock.MockMvcConstant.APP_RAML;
import static org.folio.spring.test.mock.MockMvcConstant.APP_SCHEMA;
import static org.folio.spring.test.mock.MockMvcConstant.APP_STAR;
import static org.folio.spring.test.mock.MockMvcConstant.APP_STREAM;
import static org.folio.spring.test.mock.MockMvcConstant.JSON_OBJECT;
import static org.folio.spring.test.mock.MockMvcConstant.MT_APP_JSON;
import static org.folio.spring.test.mock.MockMvcConstant.NO_PARAM;
import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_HEAD_NO_URL;
import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_HEAD_TENANT;
import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_HEAD_TOKEN;
import static org.folio.spring.test.mock.MockMvcConstant.PLAIN_BODY;
import static org.folio.spring.test.mock.MockMvcConstant.STAR;
import static org.folio.spring.test.mock.MockMvcConstant.TEXT_PLAIN;
import static org.folio.spring.test.mock.MockMvcReflection.DELETE;
import static org.folio.spring.test.mock.MockMvcReflection.PATCH;
import static org.folio.spring.test.mock.MockMvcReflection.POST;
import static org.folio.spring.test.mock.MockMvcReflection.PUT;
import static org.folio.spring.test.mock.MockMvcRequest.appendBody;
import static org.folio.spring.test.mock.MockMvcRequest.appendHeaders;
import static org.folio.spring.test.mock.MockMvcRequest.appendParameters;
import static org.folio.spring.test.mock.MockMvcRequest.buildArguments1;
import static org.folio.spring.test.mock.MockMvcRequest.buildArguments2;
import static org.folio.spring.test.mock.MockMvcRequest.invokeRequestBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.folio.rest.workflow.model.Action;
import org.folio.rest.workflow.service.OkapiDiscoveryService;
import org.folio.spring.tenant.properties.TenantProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;

@WebMvcTest(ActionController.class)
@ExtendWith(MockitoExtension.class)
class ActionControllerTest {

  private static final String PATH = "/actions";

  private MockMvc mvc;

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  private ActionController actionController;

  @MockitoBean
  private OkapiDiscoveryService okapiDiscoveryService;

  @MockitoBean
  private TenantProperties tenantProperties;

  @BeforeEach
  void beforeEach() {
    mvc = MockMvcBuilders.standaloneSetup(actionController).build();
  }

  @ParameterizedTest
  @MethodSource("provideHeadersBodyStatusFor")
  void getActionsTest(HttpHeaders headers, String contentType, String accept, MediaType mediaType, MultiValueMap<String, String> parameters, String body, int status) throws Exception {
    Action action = new Action();
    List<Action> actions = new ArrayList<>(List.of(action));

    lenient().when(okapiDiscoveryService.getActionsByTenant(any())).thenReturn(actions);

    MockHttpServletRequestBuilder request = appendHeaders(get(PATH), headers, contentType, accept);
    request = appendParameters(request, parameters);

    MvcResult result = mvc.perform(appendBody(request, body))
      .andDo(log()).andExpect(status().is(status)).andReturn();

    if (status == 200) {
      MediaType responseType = MediaType.parseMediaType(result.getResponse().getContentType());
      String actionsJson = mapper.writeValueAsString(actions);

      assertTrue(mediaType.isCompatibleWith(responseType));
      assertEquals(actionsJson, result.getResponse().getContentAsString());
    }
  }

  @ParameterizedTest
  @MethodSource("provideDeletePatchPostPutFor")
  void activateWorkflowFailsTest(Method method, HttpHeaders headers, String contentType, String accept, MediaType mediaType, MultiValueMap<String, String> parameters, String body, int status) throws Exception {
    mvc.perform(invokeRequestBuilder(PATH, method, headers, contentType, accept, parameters, body))
    .andDo(log()).andExpect(status().is(status));
  }

  /**
   * Helper function for parameterized test providing DELETE, PATCH, POST, and PUT for several end points.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Method The (reflection) request method.
   *     - HttpHeaders headers.
   *     - String contentType (Content-Type request).
   *     - String accept (ask for this Content-Type on response).
   *       String mediaType (response Content-Type).
   *     - MultiValueMap<String, String> parameters.
   *     - String body (request body).
   *     - int status (response HTTP status code).
   *
   * @throws NoSuchMethodException
   * @throws SecurityException
   */
  private static Stream<Arguments> provideDeletePatchPostPutFor() throws NoSuchMethodException, SecurityException {
    Object[] params = { NO_PARAM };

    return buildHttpDeletePatchPostPut(OKAPI_HEAD_NO_URL, params);
  }

  /**
   * Helper function for parameterized test providing tests with headers, body, and status for several end points.
   *
   * This is intended to be used for when the correct HTTP method is being used in the request.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - HttpHeaders headers.
   *     - String contentType (Content-Type request).
   *     - String accept (ask for this Content-Type on response).
   *       String mediaType (response Content-Type).
   *     - MultiValueMap<String, String> parameters.
   *     - String body (request body).
   *     - int status (response HTTP status code).
   */
  private static Stream<Arguments> provideHeadersBodyStatusFor() {
    Stream<Arguments> stream1 = Stream.of(
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   APP_SCHEMA, MT_APP_JSON, NO_PARAM, JSON_OBJECT, 406),
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   APP_JSON,   MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   TEXT_PLAIN, MT_APP_JSON, NO_PARAM, JSON_OBJECT, 406),
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   APP_STREAM, MT_APP_JSON, NO_PARAM, JSON_OBJECT, 406),
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   NULL_STR,   MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   APP_STAR,   MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_JSON,   STAR,       MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, APP_SCHEMA, MT_APP_JSON, NO_PARAM, PLAIN_BODY,  406),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, APP_JSON,   MT_APP_JSON, NO_PARAM, PLAIN_BODY,  200),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, TEXT_PLAIN, MT_APP_JSON, NO_PARAM, PLAIN_BODY,  406),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, APP_STREAM, MT_APP_JSON, NO_PARAM, PLAIN_BODY,  406),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, NULL_STR,   MT_APP_JSON, NO_PARAM, PLAIN_BODY,  200),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, STAR,       MT_APP_JSON, NO_PARAM, PLAIN_BODY,  200),
      Arguments.of(OKAPI_HEAD_TENANT, TEXT_PLAIN, APP_STAR,   MT_APP_JSON, NO_PARAM, PLAIN_BODY,  200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, APP_SCHEMA, MT_APP_JSON, NO_PARAM, JSON_OBJECT, 406),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, APP_JSON,   MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, TEXT_PLAIN, MT_APP_JSON, NO_PARAM, JSON_OBJECT, 406),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, APP_STREAM, MT_APP_JSON, NO_PARAM, JSON_OBJECT, 406),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, NULL_STR,   MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, APP_STAR,   MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200),
      Arguments.of(OKAPI_HEAD_TENANT, APP_STREAM, STAR,       MT_APP_JSON, NO_PARAM, JSON_OBJECT, 200)
    );

    Object[] params = { }; // NO_PARAM

    Stream<Arguments> stream2 = Stream.concat(stream1, buildAppJsonBodyStatus(OKAPI_HEAD_TOKEN, params));
    return Stream.concat(stream2, buildAppJsonBodyStatus(OKAPI_HEAD_TENANT, params));
  }

  /**
   * Build a common status that returns either HTTP 400 or HTTP 406.
   *
   * @param headers
   *   The HTTP headers to use.
   * @param params
   *   The array of parameter sets that would result in a HTTP 400 (when a 406 is not returned).
   *   Set to an empty array to not have 400 return codes.
   *
   * @return The stream of combinations.
   */
  private static Stream<Arguments> buildAppJsonBodyStatus(HttpHeaders headers, Object[] params) {
    String[] contentTypes = { APP_JSON, TEXT_PLAIN, APP_STREAM };
    String[] bodys = { JSON_OBJECT, PLAIN_BODY, JSON_OBJECT };
    String[] accepts = { APP_JSON, NULL_STR, APP_STAR, STAR };
    String[] acceptsInvalid = { APP_SCHEMA, TEXT_PLAIN, APP_STREAM };
    MediaType[] mediaTypes = { MT_APP_JSON };

    Stream<Arguments> stream1 = buildArguments1(headers, contentTypes, acceptsInvalid, mediaTypes, params, bodys, 406);

    if (params.length == 0) {
      return stream1;
    }

    Stream<Arguments> stream2 = buildArguments1(headers, contentTypes, accepts, mediaTypes, params, bodys, 400);
    return Stream.concat(stream1, stream2);
  }

  /**
   * Build a common status that returns HTTP 405.
   *
   * This is generally for HTTP DELETE, GET, PATCH, POST, and PUT.
   *
   * @param headers
   *   The HTTP headers to use.
   * @param params
   *   The array of parameter sets that would result in a HTTP 405.
   *
   * @return The stream of combinations.
   */
  private static Stream<Arguments> buildHttpDeletePatchPostPut(HttpHeaders headers, Object[] params) {
    String[] contentTypes = { APP_JSON, TEXT_PLAIN, APP_STREAM };
    String[] bodys = { JSON_OBJECT, PLAIN_BODY, JSON_OBJECT };
    String[] accepts = { APP_RAML, APP_SCHEMA, APP_JSON, TEXT_PLAIN, APP_STREAM, NULL_STR, APP_STAR, STAR };
    MediaType[] mediaTypes = { MT_APP_JSON };

    Stream<Arguments> stream1 = buildArguments2(DELETE, headers, contentTypes, accepts, mediaTypes, params, bodys, 405);
    Stream<Arguments> stream2 = buildArguments2(PATCH, headers, contentTypes, accepts, mediaTypes, params, bodys, 405);
    Stream<Arguments> stream3 = Stream.concat(stream1, stream2);

    stream1 = buildArguments2(POST, headers, contentTypes, accepts, mediaTypes, params, bodys, 405);
    stream2 = Stream.concat(stream3, stream1);

    stream1 = buildArguments2(PUT, headers, contentTypes, accepts, mediaTypes, params, bodys, 405);
    return Stream.concat(stream2, stream1);
  }

}
