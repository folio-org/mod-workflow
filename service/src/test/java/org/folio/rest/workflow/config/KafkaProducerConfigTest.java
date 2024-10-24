package org.folio.rest.workflow.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class KafkaProducerConfigTest {

  @InjectMocks
  private KafkaProducerConfig kafkaProducerConfig;

  @BeforeEach
  void setBootstrapAddress() {
    ReflectionTestUtils.setField(kafkaProducerConfig, "bootstrapAddress", "localhost:9092");
  }

  @Test
  void testEventProducerFactory() {
    assertNotNull(kafkaProducerConfig.eventProducerFactory());
  }

  @Test
  void testEventKafkaTemplate() {
    assertNotNull(kafkaProducerConfig.eventKafkaTemplate());
  }

}
