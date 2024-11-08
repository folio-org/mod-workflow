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

class EmbeddedLoopReferenceTest {

  private EmbeddedLoopReference embeddedLoopReference;

  @BeforeEach
  void beforeEach() {
    embeddedLoopReference = new EmbeddedLoopReference();
  }

  @Test
  void getCardinalityExpressionWorksTest() {
    setField(embeddedLoopReference, "cardinalityExpression", VALUE);

    assertEquals(VALUE, embeddedLoopReference.getCardinalityExpression());
  }

  @Test
  void setCardinalityExpressionWorksTest() {
    setField(embeddedLoopReference, "cardinalityExpression", null);

    embeddedLoopReference.setCardinalityExpression(VALUE);
    assertEquals(VALUE, getField(embeddedLoopReference, "cardinalityExpression"));
  }

  @Test
  void getDataInputRefExpressionWorksTest() {
    setField(embeddedLoopReference, "dataInputRefExpression", VALUE);

    assertEquals(VALUE, embeddedLoopReference.getDataInputRefExpression());
  }

  @Test
  void setDataInputRefExpressionWorksTest() {
    setField(embeddedLoopReference, "dataInputRefExpression", null);

    embeddedLoopReference.setDataInputRefExpression(VALUE);
    assertEquals(VALUE, getField(embeddedLoopReference, "dataInputRefExpression"));
  }

  @Test
  void getInputDataNameWorksTest() {
    setField(embeddedLoopReference, "inputDataName", VALUE);

    assertEquals(VALUE, embeddedLoopReference.getInputDataName());
  }

  @Test
  void setInputDataNameWorksTest() {
    setField(embeddedLoopReference, "inputDataName", null);

    embeddedLoopReference.setInputDataName(VALUE);
    assertEquals(VALUE, getField(embeddedLoopReference, "inputDataName"));
  }

  @Test
  void getCompleteConditionExpressionWorksTest() {
    setField(embeddedLoopReference, "completeConditionExpression", VALUE);

    assertEquals(VALUE, embeddedLoopReference.getCompleteConditionExpression());
  }

  @Test
  void setCompleteConditionExpressionWorksTest() {
    setField(embeddedLoopReference, "completeConditionExpression", null);

    embeddedLoopReference.setCompleteConditionExpression(VALUE);
    assertEquals(VALUE, getField(embeddedLoopReference, "completeConditionExpression"));
  }

  @Test
  void getParallelWorksTest() {
    setField(embeddedLoopReference, "parallel", true);

    assertEquals(true, embeddedLoopReference.getParallel());
  }

  @Test
  void setParallelWorksTest() {
    setField(embeddedLoopReference, "parallel", false);

    embeddedLoopReference.setParallel(true);
    assertEquals(true, getField(embeddedLoopReference, "parallel"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(embeddedLoopReference, attribute, value);
    });

    embeddedLoopReference.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(embeddedLoopReference, attribute));
    });
  }

  @ParameterizedTest
  @MethodSource("provideSimpleHasAndHasNotFor")
  void hasCardinalityExpressionWorksTest(String value, boolean expect) {
    setField(embeddedLoopReference, "cardinalityExpression", value);

    assertEquals(expect, embeddedLoopReference.hasCardinalityExpression());
  }

  @ParameterizedTest
  @MethodSource("provideSimpleHasAndHasNotFor")
  void hasCompleteConditionExpressionWorksTest(String value, boolean expect) {
    setField(embeddedLoopReference, "completeConditionExpression", value);

    assertEquals(expect, embeddedLoopReference.hasCompleteConditionExpression());
  }

  @ParameterizedTest
  @MethodSource("provideHasDataInputFor")
  void hasDataInput(String expression, String name, boolean expect) {
    setField(embeddedLoopReference, "dataInputRefExpression", expression);
    setField(embeddedLoopReference, "inputDataName", name);

    assertEquals(expect, embeddedLoopReference.hasDataInput());
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
        helperFieldMap(null),
        helperFieldMap(false)
      ),
      Arguments.of(
        helperFieldMap(true),
        helperFieldMap(true)
      )
    );
  }

  /**
   * Helper function for parameterized tests for the simple has and has not functions.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - String value The initial value.
   *     - boolean expect The expected return result.
   */
  private static Stream<Arguments> provideSimpleHasAndHasNotFor() {

    return Stream.of(
      Arguments.of(NULL_STR, false),
      Arguments.of(VALUE,    true)
    );
  }

  /**
   * Helper function for parameterized tests for the hasDataInputFor function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - String value The initial value.
   *     - boolean expect The expected return result.
   */
  private static Stream<Arguments> provideHasDataInputFor() {

    return Stream.of(
      Arguments.of(NULL_STR, NULL_STR, false),
      Arguments.of(VALUE,    NULL_STR, false),
      Arguments.of(NULL_STR, VALUE,    false),
      Arguments.of(VALUE,    VALUE,    true)
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param parallel The parallel value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean parallel) {
    final Map<String, Object> map = new HashMap<>();

    map.put("parallel", parallel);

    return map;
  }

}
