package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.HashSet;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InputTaskTest {

  @Mock
  private EmbeddedInput input;

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedInput> inputs;

  private Set<EmbeddedVariable> inputVariables;

  private InputTask inputTask;

  @BeforeEach
  void beforeEach() {
    inputs = new HashSet<>();
    inputs.add(input);

    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);

    inputTask = new InputTask();
  }

  @Test
  void getIdWorksTest() {
    setField(inputTask, "id", VALUE);

    assertEquals(VALUE, inputTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(inputTask, "id", null);

    inputTask.setId(VALUE);
    assertEquals(VALUE, getField(inputTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(inputTask, "name", VALUE);

    assertEquals(VALUE, inputTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(inputTask, "name", null);

    inputTask.setName(VALUE);
    assertEquals(VALUE, getField(inputTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(inputTask, "description", VALUE);

    assertEquals(VALUE, inputTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(inputTask, "description", null);

    inputTask.setDescription(VALUE);
    assertEquals(VALUE, getField(inputTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(inputTask, "deserializeAs", VALUE);

    assertEquals(VALUE, inputTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(inputTask, "deserializeAs", null);

    inputTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(inputTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(inputTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, inputTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(inputTask, "inputVariables", null);

    inputTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(inputTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(inputTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, inputTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(inputTask, "outputVariable", null);

    inputTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(inputTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(inputTask, "asyncBefore", true);

    assertEquals(true, inputTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(inputTask, "asyncBefore", false);

    inputTask.setAsyncBefore(true);
    assertEquals(true, getField(inputTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(inputTask, "asyncAfter", true);

    assertEquals(true, inputTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(inputTask, "asyncAfter", false);

    inputTask.setAsyncAfter(true);
    assertEquals(true, getField(inputTask, "asyncAfter"));
  }

  @Test
  void getInputsWorksTest() {
    setField(inputTask, "inputs", inputs);

    assertEquals(inputs, inputTask.getInputs());
  }

  @Test
  void setInputsWorksTest() {
    setField(inputTask, "inputs", null);

    inputTask.setInputs(inputs);
    assertEquals(inputs, getField(inputTask, "inputs"));
  }

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected, Map<String, Boolean> persist) {

    initial.forEach((String attribute, Object value) -> {
      setField(inputTask, attribute, value);
    });

    inputTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      if (attribute == "inputs") {
        final Set<EmbeddedInput> eps = (Set<EmbeddedInput>) value;

        assertNotNull(getField(inputTask, attribute));
        assertEquals(eps.size(), ((Set<EmbeddedInput>) getField(inputTask, attribute)).size());

        eps.forEach(ep -> {
          if (ep != null) {
            if (Boolean.TRUE.equals(persist.get(attribute))) {
              verify(ep).prePersist();
            } else if (Boolean.FALSE.equals(persist.get(attribute))) {
              verify(ep, never()).prePersist();
            }
          }
        });
      } else {
        assertEquals(value, getField(inputTask, attribute));
      }
    });
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect  The expected values.
   *     - Arguments persist Boolean representing whether or not this will call prePersist() on the object and if so, then true/false depending on verify.
   */
  private static Stream<Arguments> providePrePersistFor() {

    final EmbeddedInput variable = Mockito.spy(new EmbeddedInput());
    final EmbeddedInput nullValue = null;
    final Set<EmbeddedInput> inputs = Set.of(variable);
    final Set<EmbeddedInput> inputsEmpty = Set.of();
    final Set<EmbeddedInput> inputsNull = new HashSet<>();

    inputsNull.add(nullValue);

    return List.of(
      Arguments.of(
        helperFieldMap(null),
        helperFieldMap(inputsEmpty),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(inputsEmpty),
        helperFieldMap(inputsEmpty),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(inputsNull),
        helperFieldMap(inputsNull),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(inputs),
        helperFieldMap(inputs),
        helperPersistMap(true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param inputs The inputs value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Set<EmbeddedInput> inputs) {

    final Map<String, Object> map = new HashMap<>();

    map.put("inputs", inputs);

    return map;
  }

  /**
   * Helper for reducing in line code repetition for assignments for persist setting.
   *
   * @param inputs The headerOutputVariables persist value.
   *
   * @return The built persist map.
   */
  private static Map<String, Object> helperPersistMap(Boolean inputs) {

    final Map<String, Object> map = new HashMap<>();

    map.put("inputs", inputs);

    return map;
  }

}
