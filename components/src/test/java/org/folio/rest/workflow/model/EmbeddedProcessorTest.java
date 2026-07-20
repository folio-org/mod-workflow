package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.INT_VALUE;
import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.folio.rest.workflow.enums.ScriptType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EmbeddedProcessorTest {

  private static final String BUFFER       = "buffer";
  private static final String CODE         = "code";
  private static final String DELAY        = "delay";
  private static final String FUNCTIONNAME = "functionName";
  private static final String SCRIPTTYPE   = "scriptType";

  private EmbeddedProcessor embeddedProcessor;

  @BeforeEach
  void beforeEach() {
    embeddedProcessor = new EmbeddedProcessor();
  }

  @Test
  void getScriptTypeWorksTest() {
    setField(embeddedProcessor, SCRIPTTYPE, ScriptType.GROOVY);

    assertEquals(ScriptType.GROOVY, embeddedProcessor.getScriptType());
  }

  @Test
  void setScriptTypeWorksTest() {
    setField(embeddedProcessor, SCRIPTTYPE, null);

    embeddedProcessor.setScriptType(ScriptType.GROOVY);
    assertEquals(ScriptType.GROOVY, getField(embeddedProcessor, SCRIPTTYPE));
  }

  @Test
  void getFunctionNameWorksTest() {
    setField(embeddedProcessor, FUNCTIONNAME, VALUE);

    assertEquals(VALUE, embeddedProcessor.getFunctionName());
  }

  @Test
  void setFunctionNameWorksTest() {
    setField(embeddedProcessor, FUNCTIONNAME, null);

    embeddedProcessor.setFunctionName(VALUE);
    assertEquals(VALUE, getField(embeddedProcessor, FUNCTIONNAME));
  }

  @Test
  void getCodeWorksTest() {
    setField(embeddedProcessor, CODE, VALUE);

    assertEquals(VALUE, embeddedProcessor.getCode());
  }

  @Test
  void setCodeWorksTest() {
    setField(embeddedProcessor, CODE, null);

    embeddedProcessor.setCode(VALUE);
    assertEquals(VALUE, getField(embeddedProcessor, CODE));
  }

  @Test
  void getBufferWorksTest() {
    setField(embeddedProcessor, BUFFER, 1);

    assertEquals(1, embeddedProcessor.getBuffer());
  }

  @Test
  void setBufferWorksTest() {
    setField(embeddedProcessor, BUFFER, 0);

    embeddedProcessor.setBuffer(1);
    assertEquals(1, getField(embeddedProcessor, BUFFER));
  }

  @Test
  void getDelayWorksTest() {
    setField(embeddedProcessor, DELAY, 1);

    assertEquals(1, embeddedProcessor.getDelay());
  }

  @Test
  void setDelayWorksTest() {
    setField(embeddedProcessor, DELAY, 0);

    embeddedProcessor.setDelay(1);
    assertEquals(1, getField(embeddedProcessor, DELAY));
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

    final ScriptType nullScriptType = null;

    return List.of(
      Arguments.of(
        helperFieldMap(null,      NULL_STR, null,      NULL_STR, nullScriptType),
        helperFieldMap(0,         "",       0,         "",       ScriptType.JS)
      ),
      Arguments.of(
        helperFieldMap(INT_VALUE, NULL_STR, null,      NULL_STR, nullScriptType),
        helperFieldMap(INT_VALUE, "",       0,         "",       ScriptType.JS)
      ),
      Arguments.of(
        helperFieldMap(null,      VALUE,    null,      NULL_STR, nullScriptType),
        helperFieldMap(0,         VALUE,    0,         "",       ScriptType.JS)
      ),
      Arguments.of(
        helperFieldMap(null,      NULL_STR, INT_VALUE, NULL_STR, nullScriptType),
        helperFieldMap(0,         "",       INT_VALUE, "",       ScriptType.JS)
      ),
      Arguments.of(
        helperFieldMap(null,      NULL_STR, null,      VALUE,    nullScriptType),
        helperFieldMap(0,         "",       0,         VALUE,    ScriptType.JS)
      ),
      Arguments.of(
        helperFieldMap(null,      NULL_STR, null,      VALUE,    ScriptType.GROOVY),
        helperFieldMap(0,         "",       0,         VALUE,    ScriptType.GROOVY)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param buffer The buffer value.
   * @param code The code value.
   * @param delay The delay value.
   * @param functionName The functionName value.
   * @param scriptType The scriptType value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Integer buffer, String code, Integer delay, String functionName, ScriptType scriptType) {
    final Map<String, Object> map = new HashMap<>();

    map.put(BUFFER, buffer);
    map.put(CODE, code);
    map.put(DELAY, delay);
    map.put(FUNCTIONNAME, functionName);
    map.put(SCRIPTTYPE, scriptType);

    return map;
  }

}
