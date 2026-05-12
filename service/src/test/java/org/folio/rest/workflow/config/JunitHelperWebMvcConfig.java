package org.folio.rest.workflow.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

/**
 * Provide custom web MVC settings for use during unit tests.
 */
@TestConfiguration
@ActiveProfiles("test")
public class JunitHelperWebMvcConfig {

  @Bean
  ObjectMapper objectMapper() {
    final ObjectMapper mapper = new ObjectMapper();

    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    return mapper;
  }
}
