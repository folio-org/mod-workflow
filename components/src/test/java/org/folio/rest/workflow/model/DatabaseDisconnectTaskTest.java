package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.HashSet;
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
    setField(databaseDisconnectTask, "id", VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(databaseDisconnectTask, "id", null);

    databaseDisconnectTask.setId(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(databaseDisconnectTask, "name", VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(databaseDisconnectTask, "name", null);

    databaseDisconnectTask.setName(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(databaseDisconnectTask, "description", VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(databaseDisconnectTask, "description", null);

    databaseDisconnectTask.setDescription(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(databaseDisconnectTask, "deserializeAs", VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(databaseDisconnectTask, "deserializeAs", null);

    databaseDisconnectTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(databaseDisconnectTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, databaseDisconnectTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(databaseDisconnectTask, "inputVariables", null);

    databaseDisconnectTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(databaseDisconnectTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(databaseDisconnectTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, databaseDisconnectTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(databaseDisconnectTask, "outputVariable", null);

    databaseDisconnectTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(databaseDisconnectTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(databaseDisconnectTask, "asyncBefore", true);

    assertEquals(true, databaseDisconnectTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(databaseDisconnectTask, "asyncBefore", false);

    databaseDisconnectTask.setAsyncBefore(true);
    assertEquals(true, getField(databaseDisconnectTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(databaseDisconnectTask, "asyncAfter", true);

    assertEquals(true, databaseDisconnectTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(databaseDisconnectTask, "asyncAfter", false);

    databaseDisconnectTask.setAsyncAfter(true);
    assertEquals(true, getField(databaseDisconnectTask, "asyncAfter"));
  }

  @Test
  void getDesignationWorksTest() {
    setField(databaseDisconnectTask, "designation", VALUE);

    assertEquals(VALUE, databaseDisconnectTask.getDesignation());
  }

  @Test
  void setDesignationWorksTest() {
    setField(databaseDisconnectTask, "designation", null);

    databaseDisconnectTask.setDesignation(VALUE);
    assertEquals(VALUE, getField(databaseDisconnectTask, "designation"));
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

    return Stream.of(
      Arguments.of(
        helperFieldMap(NULL_STR),
        helperFieldMap("")
      ),
      Arguments.of(
        helperFieldMap(VALUE),
        helperFieldMap(VALUE)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param designation The designation value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String designation) {
    final Map<String, Object> map = new HashMap<>();

    map.put("designation", designation);

    return map;
  }

}
