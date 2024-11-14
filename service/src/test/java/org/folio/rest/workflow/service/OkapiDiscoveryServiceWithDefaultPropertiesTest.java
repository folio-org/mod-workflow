package org.folio.rest.workflow.service;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.folio.rest.workflow.model.Handler;
import org.folio.spring.web.service.HttpService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * This runs the OkapiDiscoveryService tests for when there is no properly configuration application.yml.
 *
 * Do no add additional settings loading or alterations to this test (such as @TestPropertySource).
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class OkapiDiscoveryServiceWithDefaultPropertiesTest {

  protected static final String ID = "id";
  protected static final String HANDLERS = "handlers";
  protected static final String PROVIDES = "provides";

  @InjectMocks
  protected OkapiDiscoveryService okapiDiscoveryService;

  @MockBean
  protected HttpService mockHttpService;

  @Mock
  protected JsonNode mockJsonNode;

  @Mock
  protected JsonNode mockInterfaceNode;

  @Mock
  protected Iterator<JsonNode> mockProvidesIter;

  @Test
  void getHandlersWorksWithNoProvidesTest() throws IOException {
    mockJsonResponseEntity(mockJsonNode, 200);

    when(mockJsonNode.get(PROVIDES)).thenReturn(mockInterfaceNode);
    when(mockInterfaceNode.iterator()).thenReturn(mockProvidesIter);
    when(mockProvidesIter.hasNext()).thenReturn(false);

    Map<String, List<Handler>> handlers = okapiDiscoveryService.getHandlers(VALUE, VALUE);

    assertEquals(0, handlers.size());
  }

  @Test
  void getHandlersWorksWithNullProvidesTest() throws IOException {
    mockJsonResponseEntity(mockJsonNode, 200);
    when(mockJsonNode.get(PROVIDES)).thenReturn(null);

    Map<String, List<Handler>> handlers = okapiDiscoveryService.getHandlers(VALUE, VALUE);

    assertEquals(0, handlers.size());
  }

  @Test
  void getModulesWorksTest() {
    mockJsonResponseEntity(mockJsonNode, 200);

    final JsonNode descriptor = okapiDiscoveryService.getModules(VALUE);

    assertEquals(mockJsonNode, descriptor);
  }

  @Test
  void getModuleDescriptorWorksTest() {
    mockJsonResponseEntity(mockJsonNode, 200);

    final JsonNode descriptor = okapiDiscoveryService.getModuleDescriptor(VALUE, VALUE);

    assertEquals(mockJsonNode, descriptor);
  }

  /**
   * A generic mock for ResponseEntity and JsonNode to reduce code repetition.
   *
   * @param jsonNode the JSON Node the mocked response entity shall return.
   * @param httpStatus The HTTP Status code to return.
   */
  protected void mockJsonResponseEntity(JsonNode jsonNode, int httpStatus) {
    ResponseEntity<Object> mockJsonResponseEntity = new ResponseEntity<>(jsonNode, HttpStatusCode.valueOf(httpStatus));

    when(mockHttpService.exchange(anyString(), any(HttpMethod.class), any(), any())).thenReturn(mockJsonResponseEntity);
  }

}
