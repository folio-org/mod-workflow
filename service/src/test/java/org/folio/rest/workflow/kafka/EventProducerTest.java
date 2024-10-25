package org.folio.rest.workflow.kafka;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import java.util.HashMap;
import java.util.stream.Stream;

import org.folio.spring.messaging.model.Event;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class EventProducerTest {

  @MockBean
  private KafkaTemplate<String, Event> eventTemplate;

  @InjectMocks
  private EventProducer eventProducer;

  @ParameterizedTest
  @MethodSource("eventStream")
  void testSend(Event event) {
    ReflectionTestUtils.setField(eventProducer, "topic", "test.workflow.events");
    eventProducer.send(event);
    Mockito.verify(eventTemplate, times(1)).send(anyString(), eq(event));
  }

  static Stream<Event> eventStream() {
    return Stream.of(new Event[] {
        new Event(
            "triggerId",
            "pathPattern",
            "method",
            "tenant",
            "path"),
        new Event(
            "triggerId",
            "pathPattern",
            "method",
            "tenant",
            "path",
            new HashMap<String, String>())
    });
  }

}
