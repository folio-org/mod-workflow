package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConditionTest {

  private Condition condition;

  @BeforeEach
  void beforeEach() {
    condition = new Condition();
  }

  @Test
  void getIdWorksTest() {
    setField(condition, "id", VALUE);

    assertEquals(VALUE, condition.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(condition, "id", null);

    condition.setId(VALUE);
    assertEquals(VALUE, getField(condition, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(condition, "name", VALUE);

    assertEquals(VALUE, condition.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(condition, "name", null);

    condition.setName(VALUE);
    assertEquals(VALUE, getField(condition, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(condition, "description", VALUE);

    assertEquals(VALUE, condition.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(condition, "description", null);

    condition.setDescription(VALUE);
    assertEquals(VALUE, getField(condition, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(condition, "deserializeAs", VALUE);

    assertEquals(VALUE, condition.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(condition, "deserializeAs", null);

    condition.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(condition, "deserializeAs"));
  }

  @Test
  void getExpressionWorksTest() {
    setField(condition, "expression", VALUE);

    assertEquals(VALUE, condition.getExpression());
  }

  @Test
  void setExpressionWorksTest() {
    setField(condition, "expression", null);

    condition.setExpression(VALUE);
    assertEquals(VALUE, getField(condition, "expression"));
  }

  @Test
  void getAnswerWorksTest() {
    setField(condition, "answer", VALUE);

    assertEquals(VALUE, condition.getAnswer());
  }

  @Test
  void setAnswerWorksTest() {
    setField(condition, "answer", null);

    condition.setAnswer(VALUE);
    assertEquals(VALUE, getField(condition, "answer"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(condition, attribute, value);
    });

    condition.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(condition, attribute));
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
        helperFieldMap(NULL_STR, NULL_STR),
        helperFieldMap("",       "")
      ),
      Arguments.of(
        helperFieldMap(VALUE,    NULL_STR),
        helperFieldMap(VALUE,    "")
      ),
      Arguments.of(
        helperFieldMap(NULL_STR, VALUE),
        helperFieldMap("",       VALUE)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param answer The answer value.
   * @param expression The expression value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String answer, String expression) {
    final Map<String, Object> map = new HashMap<>();

    map.put("answer", answer);
    map.put("expression", expression);

    return map;
  }

}
