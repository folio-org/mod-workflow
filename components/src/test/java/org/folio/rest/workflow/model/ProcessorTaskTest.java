package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
class ProcessorTaskTest {

  @Mock
  private EmbeddedVariable embeddedVariable;

  @Mock
  private EmbeddedProcessor embeddedProcessor;

  private Set<EmbeddedVariable> inputVariables;

  private ProcessorTask processorTask;

  @BeforeEach
  void beforeEach() {
    processorTask = new ProcessorTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(processorTask, "id", VALUE);

    assertEquals(VALUE, processorTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(processorTask, "id", null);

    processorTask.setId(VALUE);
    assertEquals(VALUE, getField(processorTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(processorTask, "name", VALUE);

    assertEquals(VALUE, processorTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(processorTask, "name", null);

    processorTask.setName(VALUE);
    assertEquals(VALUE, getField(processorTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(processorTask, "description", VALUE);

    assertEquals(VALUE, processorTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(processorTask, "description", null);

    processorTask.setDescription(VALUE);
    assertEquals(VALUE, getField(processorTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(processorTask, "deserializeAs", VALUE);

    assertEquals(VALUE, processorTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(processorTask, "deserializeAs", null);

    processorTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(processorTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(processorTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, processorTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(processorTask, "inputVariables", null);

    processorTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(processorTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(processorTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, processorTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(processorTask, "outputVariable", null);

    processorTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(processorTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(processorTask, "asyncBefore", true);

    assertEquals(true, processorTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(processorTask, "asyncBefore", false);

    processorTask.setAsyncBefore(true);
    assertEquals(true, getField(processorTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(processorTask, "asyncAfter", true);

    assertEquals(true, processorTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(processorTask, "asyncAfter", false);

    processorTask.setAsyncAfter(true);
    assertEquals(true, getField(processorTask, "asyncAfter"));
  }

  @Test
  void getProcessorWorksTest() {
    setField(processorTask, "processor", embeddedProcessor);

    assertEquals(embeddedProcessor, processorTask.getProcessor());
  }

  @Test
  void setProcessorWorksTest() {
    setField(processorTask, "processor", null);

    processorTask.setProcessor(embeddedProcessor);
    assertEquals(embeddedProcessor, getField(processorTask, "processor"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected, Map<String, Boolean> persist) {

    initial.forEach((String attribute, Object value) -> {
      setField(processorTask, attribute, value);
    });

    processorTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      if (Boolean.TRUE.equals(persist.get(attribute))) {
        if (attribute == "processor") {
          verify((EmbeddedProcessor) value).prePersist();
        }
      } else if (Boolean.FALSE.equals(persist.get(attribute))) {
        if (attribute == "processor") {
          verify((EmbeddedProcessor) value, never()).prePersist();
        }
      } else {
        assertEquals(value, getField(processorTask, attribute));
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

    final EmbeddedProcessor processor = Mockito.spy(new EmbeddedProcessor());

    return List.of(
      Arguments.of(
        helperFieldMap(null),
        helperFieldMap(processor),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(processor),
        helperFieldMap(processor),
        helperPersistMap(true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param processor The processor value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(EmbeddedProcessor processor) {

    final Map<String, Object> map = new HashMap<>();

    map.put("processor", processor);

    return map;
  }

  /**
   * Helper for reducing in line code repetition for assignments for persist setting.
   *
   * @param processor The processor persist value.
   *
   * @return The built persist map.
   */
  private static Map<String, Object> helperPersistMap(Boolean processor) {

    final Map<String, Object> map = new HashMap<>();

    map.put("processor", processor);

    return map;
  }

}
