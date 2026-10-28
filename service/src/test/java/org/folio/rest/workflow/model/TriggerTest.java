package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.folio.rest.workflow.enums.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TriggerTest {

  private static final String METHOD = "method";

  private Trigger trigger;

  @BeforeEach
  void beforeEach() {

    trigger = new Trigger();
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {

    initial.forEach((String attribute, Object value) -> {
      setField(trigger, attribute, value);
    });

    trigger.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(trigger, attribute));
    });
  }

  @Test
  void getIdWorksTest() {
    setField(trigger, "id", VALUE);

    assertEquals(VALUE, trigger.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(trigger, "id", null);

    trigger.setId(VALUE);
    assertEquals(VALUE, getField(trigger, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(trigger, "name", VALUE);

    assertEquals(VALUE, trigger.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(trigger, "name", null);

    trigger.setName(VALUE);
    assertEquals(VALUE, getField(trigger, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(trigger, "description", VALUE);

    assertEquals(VALUE, trigger.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(trigger, "description", null);

    trigger.setDescription(VALUE);
    assertEquals(VALUE, getField(trigger, "description"));
  }

  @Test
  void getPathPatternWorksTest() {
    setField(trigger, "pathPattern", VALUE);

    assertEquals(VALUE, trigger.getPathPattern());
  }

  @Test
  void setPathPatternWorksTest() {
    setField(trigger, "pathPattern", null);

    trigger.setPathPattern(VALUE);
    assertEquals(VALUE, getField(trigger, "pathPattern"));
  }

  @Test
  void getMethodWorksTest() {
    setField(trigger, "method", HttpMethod.DELETE);

    assertEquals(HttpMethod.DELETE, trigger.getMethod());
  }

  @Test
  void setMethodWorksTest() {
    setField(trigger, "method", null);

    trigger.setMethod(HttpMethod.DELETE);
    assertEquals(HttpMethod.DELETE, getField(trigger, "method"));
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect  The expected values.
   */
  private static Stream<Arguments> providePrePersistFor() {

    final HttpMethod method = HttpMethod.DELETE;
    final HttpMethod methodDefault = Trigger.DEFAULT_METHOD;

    final List<Arguments> arguments = new ArrayList<>();

    arguments.add(Arguments.of(
      helperFieldMap(null),
      helperFieldMap(methodDefault)
    ));

    arguments.add(Arguments.of(
      helperFieldMap(null),
      helperFieldMap(methodDefault)
    ));

    arguments.add(Arguments.of(
      helperFieldMap(method),
      helperFieldMap(method)
    ));

    return arguments.stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param method The method value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(HttpMethod method) {

    final Map<String, Object> map = new HashMap<>();

    map.put(METHOD, method);

    return map;
  }

}
