package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConditionTest {

  private static final String ANSWER        = "answer";
  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String EXPRESSION    = "expression";
  private static final String ID            = "id";
  private static final String NAME          = "name";

  private Condition condition;

  @BeforeEach
  void beforeEach() {
    condition = new Condition();
  }

  @Test
  void getIdWorksTest() {
    setField(condition, ID, VALUE);

    assertEquals(VALUE, condition.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(condition, ID, null);

    condition.setId(VALUE);
    assertEquals(VALUE, getField(condition, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(condition, NAME, VALUE);

    assertEquals(VALUE, condition.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(condition, NAME, null);

    condition.setName(VALUE);
    assertEquals(VALUE, getField(condition, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(condition, DESCRIPTION, VALUE);

    assertEquals(VALUE, condition.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(condition, DESCRIPTION, null);

    condition.setDescription(VALUE);
    assertEquals(VALUE, getField(condition, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(condition, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, condition.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(condition, DESERIALIZEAS, null);

    condition.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(condition, DESERIALIZEAS));
  }

  @Test
  void getExpressionWorksTest() {
    setField(condition, EXPRESSION, VALUE);

    assertEquals(VALUE, condition.getExpression());
  }

  @Test
  void setExpressionWorksTest() {
    setField(condition, EXPRESSION, null);

    condition.setExpression(VALUE);
    assertEquals(VALUE, getField(condition, EXPRESSION));
  }

  @Test
  void getAnswerWorksTest() {
    setField(condition, ANSWER, VALUE);

    assertEquals(VALUE, condition.getAnswer());
  }

  @Test
  void setAnswerWorksTest() {
    setField(condition, ANSWER, null);

    condition.setAnswer(VALUE);
    assertEquals(VALUE, getField(condition, ANSWER));
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

    return List.of(
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
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param answer The answer value.
   * @param expression The expression value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String answer, String expression) {
    final Map<String, Object> map = new HashMap<>();

    map.put(ANSWER, answer);
    map.put(EXPRESSION, expression);

    return map;
  }

}
