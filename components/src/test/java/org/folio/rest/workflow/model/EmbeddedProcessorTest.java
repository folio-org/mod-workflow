package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.INT_VALUE;
import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.folio.rest.workflow.enums.ScriptType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EmbeddedProcessorTest {

  private EmbeddedProcessor embeddedProcessor;

  @BeforeEach
  void beforeEach() {
    embeddedProcessor = new EmbeddedProcessor();
  }

  @Test
  void getScriptTypeWorksTest() {
    setField(embeddedProcessor, "scriptType", ScriptType.GROOVY);

    assertEquals(ScriptType.GROOVY, embeddedProcessor.getScriptType());
  }

  @Test
  void setScriptTypeWorksTest() {
    setField(embeddedProcessor, "scriptType", null);

    embeddedProcessor.setScriptType(ScriptType.GROOVY);
    assertEquals(ScriptType.GROOVY, getField(embeddedProcessor, "scriptType"));
  }

  @Test
  void getFunctionNameWorksTest() {
    setField(embeddedProcessor, "functionName", VALUE);

    assertEquals(VALUE, embeddedProcessor.getFunctionName());
  }

  @Test
  void setFunctionNameWorksTest() {
    setField(embeddedProcessor, "functionName", null);

    embeddedProcessor.setFunctionName(VALUE);
    assertEquals(VALUE, getField(embeddedProcessor, "functionName"));
  }

  @Test
  void getCodeWorksTest() {
    setField(embeddedProcessor, "code", VALUE);

    assertEquals(VALUE, embeddedProcessor.getCode());
  }

  @Test
  void setCodeWorksTest() {
    setField(embeddedProcessor, "code", null);

    embeddedProcessor.setCode(VALUE);
    assertEquals(VALUE, getField(embeddedProcessor, "code"));
  }

  @Test
  void getBufferWorksTest() {
    setField(embeddedProcessor, "buffer", 1);

    assertEquals(1, embeddedProcessor.getBuffer());
  }

  @Test
  void setBufferWorksTest() {
    setField(embeddedProcessor, "buffer", 0);

    embeddedProcessor.setBuffer(1);
    assertEquals(1, getField(embeddedProcessor, "buffer"));
  }

  @Test
  void getDelayWorksTest() {
    setField(embeddedProcessor, "delay", 1);

    assertEquals(1, embeddedProcessor.getDelay());
  }

  @Test
  void setDelayWorksTest() {
    setField(embeddedProcessor, "delay", 0);

    embeddedProcessor.setDelay(1);
    assertEquals(1, getField(embeddedProcessor, "delay"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(embeddedProcessor, attribute, value);
    });

    embeddedProcessor.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(embeddedProcessor, attribute));
    });
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect The expected values.
   */
  private static Stream<Arguments> providePrePersistFor() {

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,      NULL_STR, null,      NULL_STR),
        helperFieldMap(0,         "",       0,         "")
      ),
      Arguments.of(
        helperFieldMap(INT_VALUE, NULL_STR, null,      NULL_STR),
        helperFieldMap(INT_VALUE, "",       0,         "")
      ),
      Arguments.of(
        helperFieldMap(null,      VALUE,    null,      NULL_STR),
        helperFieldMap(0,         VALUE,    0,         "")
      ),
      Arguments.of(
        helperFieldMap(null,      NULL_STR, INT_VALUE, NULL_STR),
        helperFieldMap(0,         "",       INT_VALUE, "")
      ),
      Arguments.of(
        helperFieldMap(null,      NULL_STR, null,      VALUE),
        helperFieldMap(0,         "",       0,         VALUE)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param mailFrom The mailFrom value.
   * @param mailText The mailText value.
   * @param mailTo The mailTo value.
   * @param mailSubject The mailSubject value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Integer buffer, String code, Integer delay, String functionName) {
    final Map<String, Object> map = new HashMap<>();

    map.put("buffer", buffer);
    map.put("code", code);
    map.put("delay", delay);
    map.put("functionName", functionName);

    return map;
  }

}
