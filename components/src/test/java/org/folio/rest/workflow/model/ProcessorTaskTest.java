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

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";
  private static final String PROCESSOR      = "processor";

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
    setField(processorTask, ID, VALUE);

    assertEquals(VALUE, processorTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(processorTask, ID, null);

    processorTask.setId(VALUE);
    assertEquals(VALUE, getField(processorTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(processorTask, NAME, VALUE);

    assertEquals(VALUE, processorTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(processorTask, NAME, null);

    processorTask.setName(VALUE);
    assertEquals(VALUE, getField(processorTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(processorTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, processorTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(processorTask, DESCRIPTION, null);

    processorTask.setDescription(VALUE);
    assertEquals(VALUE, getField(processorTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(processorTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, processorTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(processorTask, DESERIALIZEAS, null);

    processorTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(processorTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(processorTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, processorTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(processorTask, INPUTVARIABLES, null);

    processorTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(processorTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(processorTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, processorTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(processorTask, OUTPUTVARIABLE, null);

    processorTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(processorTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(processorTask, ASYNCBEFORE, true);

    assertEquals(true, processorTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(processorTask, ASYNCBEFORE, false);

    processorTask.setAsyncBefore(true);
    assertEquals(true, getField(processorTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(processorTask, ASYNCAFTER, true);

    assertEquals(true, processorTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(processorTask, ASYNCAFTER, false);

    processorTask.setAsyncAfter(true);
    assertEquals(true, getField(processorTask, ASYNCAFTER));
  }

  @Test
  void getProcessorWorksTest() {
    setField(processorTask, PROCESSOR, embeddedProcessor);

    assertEquals(embeddedProcessor, processorTask.getProcessor());
  }

  @Test
  void setProcessorWorksTest() {
    setField(processorTask, PROCESSOR, null);

    processorTask.setProcessor(embeddedProcessor);
    assertEquals(embeddedProcessor, getField(processorTask, PROCESSOR));
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
        if (attribute == PROCESSOR) {
          verify((EmbeddedProcessor) value).prePersist();
        }
      } else if (Boolean.FALSE.equals(persist.get(attribute))) {
        if (attribute == PROCESSOR) {
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

    map.put(PROCESSOR, processor);

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

    map.put(PROCESSOR, processor);

    return map;
  }

}
