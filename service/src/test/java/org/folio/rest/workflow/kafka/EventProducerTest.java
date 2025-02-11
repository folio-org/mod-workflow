package org.folio.rest.workflow.kafka;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.stream.Stream;
import org.folio.spring.messaging.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class EventProducerTest {

  @MockitoBean
  private KafkaTemplate<String, Event> eventTemplate;

  @InjectMocks
  private EventProducer eventProducer;

  @BeforeEach
  void beforeEach() {
    ReflectionTestUtils.setField(eventProducer, "topic", "test.workflow-engine.events");
  }

  @ParameterizedTest
  @MethodSource("provideSendEvents")
  void testSend(Event event, VerificationMode verifyMode) {
    eventProducer.send(event);

    if (verifyMode != null) {
      verify(eventTemplate, verifyMode).send(anyString(), eq(event));
    }
  }

  /**
   * Helper function for parameterized test providing tests for the send events tests.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Event event (the Event to use).
   *     - VerificationMode verifyMode (The verification mode to use, set to NULL to not use).
   */
  private static Stream<Arguments> provideSendEvents() {
    final Event event1 = new Event("triggerId", "pathPattern", "method", "tenant", "path");
    final Event event2 = new Event("triggerId", "pathPattern", "method", "tenant", "path", new HashMap<String, String>());

    return Stream.of(
      Arguments.of(event1, times(1)),
      Arguments.of(event2, times(1))
    );
  }

}
