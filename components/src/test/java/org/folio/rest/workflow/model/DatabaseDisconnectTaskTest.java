package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseDisconnectTaskTest {

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String DESIGNATION    = "designation";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private DatabaseDisconnectTask databaseDisconnectTask;

  @BeforeEach
  void beforeEach() {
    databaseDisconnectTask = new DatabaseDisconnectTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(databaseDisconnectTask, ID, VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(databaseDisconnectTask, ID, null);

    databaseDisconnectTask.setId(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(databaseDisconnectTask, NAME, VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(databaseDisconnectTask, NAME, null);

    databaseDisconnectTask.setName(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(databaseDisconnectTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(databaseDisconnectTask, DESCRIPTION, null);

    databaseDisconnectTask.setDescription(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(databaseDisconnectTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(databaseDisconnectTask, DESERIALIZEAS, null);

    databaseDisconnectTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(databaseDisconnectTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, databaseDisconnectTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(databaseDisconnectTask, INPUTVARIABLES, null);

    databaseDisconnectTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(databaseDisconnectTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(databaseDisconnectTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, databaseDisconnectTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(databaseDisconnectTask, OUTPUTVARIABLE, null);

    databaseDisconnectTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(databaseDisconnectTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(databaseDisconnectTask, ASYNCBEFORE, true);

    assertEquals(true, databaseDisconnectTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(databaseDisconnectTask, ASYNCBEFORE, false);

    databaseDisconnectTask.setAsyncBefore(true);
    assertEquals(true, getField(databaseDisconnectTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(databaseDisconnectTask, ASYNCAFTER, true);

    assertEquals(true, databaseDisconnectTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(databaseDisconnectTask, ASYNCAFTER, false);

    databaseDisconnectTask.setAsyncAfter(true);
    assertEquals(true, getField(databaseDisconnectTask, ASYNCAFTER));
  }

  @Test
  void getDesignationWorksTest() {
    setField(databaseDisconnectTask, DESIGNATION, VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getDesignation());
  }

  @Test
  void setDesignationWorksTest() {
    setField(databaseDisconnectTask, DESIGNATION, null);

    databaseDisconnectTask.setDesignation(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, DESIGNATION));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(databaseDisconnectTask, attribute, value);
    });

    databaseDisconnectTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(databaseDisconnectTask, attribute));
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
   * @param designation The designation value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String designation) {
    final Map<String, Object> map = new HashMap<>();

    map.put(DESIGNATION, designation);

    return map;
  }

}
