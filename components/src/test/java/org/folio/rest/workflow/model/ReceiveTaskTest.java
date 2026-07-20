package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReceiveTaskTest {

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String MESSAGE        = "message";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private ReceiveTask receiveTask;

  @BeforeEach
  void beforeEach() {
    receiveTask = new ReceiveTask();
  }

  @Test
  void getIdWorksTest() {
    setField(receiveTask, ID, VALUE);

    assertEquals(VALUE, receiveTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(receiveTask, ID, null);

    receiveTask.setId(VALUE);
    assertEquals(VALUE, getField(receiveTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(receiveTask, NAME, VALUE);

    assertEquals(VALUE, receiveTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(receiveTask, NAME, null);

    receiveTask.setName(VALUE);
    assertEquals(VALUE, getField(receiveTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(receiveTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, receiveTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(receiveTask, DESCRIPTION, null);

    receiveTask.setDescription(VALUE);
    assertEquals(VALUE, getField(receiveTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(receiveTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, receiveTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(receiveTask, DESERIALIZEAS, null);

    receiveTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(receiveTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(receiveTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, receiveTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(receiveTask, INPUTVARIABLES, null);

    receiveTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(receiveTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(receiveTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, receiveTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(receiveTask, OUTPUTVARIABLE, null);

    receiveTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(receiveTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(receiveTask, ASYNCBEFORE, true);

    assertEquals(true, receiveTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(receiveTask, ASYNCBEFORE, false);

    receiveTask.setAsyncBefore(true);
    assertEquals(true, getField(receiveTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(receiveTask, ASYNCAFTER, true);

    assertEquals(true, receiveTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(receiveTask, ASYNCAFTER, false);

    receiveTask.setAsyncAfter(true);
    assertEquals(true, getField(receiveTask, ASYNCAFTER));
  }

  @Test
  void getMessageWorksTest() {
    setField(receiveTask, MESSAGE, VALUE);

    assertEquals(VALUE, receiveTask.getMessage());
  }

  @Test
  void setMessageWorksTest() {
    setField(receiveTask, MESSAGE, null);

    receiveTask.setMessage(VALUE);
    assertEquals(VALUE, getField(receiveTask, MESSAGE));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(receiveTask, attribute, value);
    });

    receiveTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(receiveTask, attribute));
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
        helperFieldMap(NULL_STR),
        helperFieldMap("")
      ),
      Arguments.of(
        helperFieldMap(VALUE),
        helperFieldMap(VALUE)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param message The message value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String message) {
    final Map<String, Object> map = new HashMap<>();

    map.put(MESSAGE, message);

    return map;
  }

}
