package org.folio.rest.workflow.service;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * This runs the OkapiDiscoveryService tests when special application.yml settings need to be tested.
 *
 * This is expected to be empty as the OkapiDiscoveryServiceWithDefaultConfigTest() contains all of the tests.
 */
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@TestPropertySource(properties = {
  "logging.level.org.folio.rest.workflow.service.OkapiDiscoveryService = DEBUG",
  "tenant.headerName = X-Okapi-Tenant",
  "tenant.force-tenant = false",
  "tenant.default-tenant = diku"
})
class OkapiDiscoveryServiceWithCustomPropertiesTest extends OkapiDiscoveryServiceWithDefaultPropertiesTest {

  /**
   * This test is added because `tenant.headerName` is not being lodaded by OkapiDiscoveryService().
   */
  @Test
  void getModuleDescriptorWithTenantHeaderNameWorksTest() {
    ReflectionTestUtils.setField(okapiDiscoveryService, "tenantHeaderName", "X-Okapi-Tenant");

    mockJsonResponseEntity(mockJsonNode, 200);

    final JsonNode descriptor = okapiDiscoveryService.getModuleDescriptor(VALUE, VALUE);

    assertEquals(mockJsonNode, descriptor);
  }
}
