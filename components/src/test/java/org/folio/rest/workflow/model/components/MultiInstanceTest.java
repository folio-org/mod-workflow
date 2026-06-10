package org.folio.rest.workflow.model.components;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;
import java.util.stream.Stream;
import org.folio.rest.workflow.model.EmbeddedLoopReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MultiInstanceTest {

  private MultiInstance multiInstance;

  @BeforeEach
  void beforeEach() {
    multiInstance = new Impl();
  }

  @ParameterizedTest
  @MethodSource("provideMultiInstanceData")
  void isMultiInstanceWorksTest(EmbeddedLoopReference loopRef, String cardinalityExpression, String dataInputRefExpression, String inputDataName, Boolean expect) {
    setField(multiInstance, "loopRef", loopRef);

    if (loopRef != null) {
      setField(loopRef, "cardinalityExpression", cardinalityExpression);
      setField(loopRef, "dataInputRefExpression", dataInputRefExpression);
      setField(loopRef, "inputDataName", inputDataName);
    }

    assertEquals(expect, multiInstance.isMultiInstance());
  }

  private static class Impl implements MultiInstance {

    private EmbeddedLoopReference loopRef;

    @Override
    public EmbeddedLoopReference getLoopRef() {
      return loopRef;
    }

    @Override
    public void setLoopRef(EmbeddedLoopReference loopRef) {
      this.loopRef = loopRef;
    }
  };

  /**
   * Provide variables for the given isMultiInstance parameterized test.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - loopRef: The Loop Reference class.
   *     - cardinalityExpression: The cardinality expression string.
   *     - dataInputRefExpression: The data input reference expression string.
   *     - inputDataName: The input data name string.
   *     - expect: The returned boolean value to expect.
   */
  private static Stream<Arguments> provideMultiInstanceData() {
    EmbeddedLoopReference embeddedLoopReference = new EmbeddedLoopReference();
    EmbeddedLoopReference nullEmbedded = null;

    return List.of(
      Arguments.of(nullEmbedded,          NULL_STR, NULL_STR, NULL_STR, false),
      Arguments.of(nullEmbedded,          VALUE,    NULL_STR, NULL_STR, false),
      Arguments.of(nullEmbedded,          NULL_STR, VALUE,    NULL_STR, false),
      Arguments.of(nullEmbedded,          NULL_STR, NULL_STR, VALUE,    false),
      Arguments.of(nullEmbedded,          VALUE,    VALUE,    NULL_STR, false),
      Arguments.of(nullEmbedded,          VALUE,    NULL_STR, VALUE,    false),
      Arguments.of(nullEmbedded,          NULL_STR, VALUE,    VALUE,    false),
      Arguments.of(nullEmbedded,          VALUE,    VALUE,    VALUE,    false),
      Arguments.of(embeddedLoopReference, NULL_STR, NULL_STR, NULL_STR, false),
      Arguments.of(embeddedLoopReference, VALUE,    NULL_STR, NULL_STR, true),
      Arguments.of(embeddedLoopReference, NULL_STR, VALUE,    NULL_STR, false),
      Arguments.of(embeddedLoopReference, NULL_STR, NULL_STR, VALUE,    false),
      Arguments.of(embeddedLoopReference, VALUE,    VALUE,    NULL_STR, true),
      Arguments.of(embeddedLoopReference, VALUE,    NULL_STR, VALUE,    true),
      Arguments.of(embeddedLoopReference, NULL_STR, VALUE,    VALUE,    true),
      Arguments.of(embeddedLoopReference, VALUE,    VALUE,    VALUE,    true)
    ).stream();
  }

}
