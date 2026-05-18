package org.folio.rest.workflow.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * Provide custom web MVC settings for use during unit tests.
 */
@TestConfiguration
@ActiveProfiles("test")
public class JunitHelperWebMvcConfig {

  @Bean
  ObjectMapper objectMapper() {
    return JsonMapper
      .builder()
      .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      .build();
  }
}
