package org.folio.rest.workflow.model;

import static org.folio.rest.workflow.enums.DirectoryAction.LIST;
import static org.folio.rest.workflow.enums.DirectoryAction.WRITE;
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
import org.folio.rest.workflow.enums.DirectoryAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DirectoryTaskTest {

  private static final String ACTION         = "action";
  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";
  private static final String PATH           = "path";
  private static final String WORKFLOW       = "workflow";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private DirectoryTask directoryTask;

  @BeforeEach
  void beforeEach() {
    directoryTask = new DirectoryTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(directoryTask, ID, VALUE);

    assertEquals(VALUE, directoryTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(directoryTask, ID, null);

    directoryTask.setId(VALUE);
    assertEquals(VALUE, getField(directoryTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(directoryTask, NAME, VALUE);

    assertEquals(VALUE, directoryTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(directoryTask, NAME, null);

    directoryTask.setName(VALUE);
    assertEquals(VALUE, getField(directoryTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(directoryTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, directoryTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(directoryTask, DESCRIPTION, null);

    directoryTask.setDescription(VALUE);
    assertEquals(VALUE, getField(directoryTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(directoryTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, directoryTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(directoryTask, DESERIALIZEAS, null);

    directoryTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(directoryTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(directoryTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, directoryTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(directoryTask, INPUTVARIABLES, null);

    directoryTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(directoryTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(directoryTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, directoryTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(directoryTask, OUTPUTVARIABLE, null);

    directoryTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(directoryTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(directoryTask, ASYNCBEFORE, true);

    assertEquals(true, directoryTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(directoryTask, ASYNCBEFORE, false);

    directoryTask.setAsyncBefore(true);
    assertEquals(true, getField(directoryTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(directoryTask, ASYNCAFTER, true);

    assertEquals(true, directoryTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(directoryTask, ASYNCAFTER, false);

    directoryTask.setAsyncAfter(true);
    assertEquals(true, getField(directoryTask, ASYNCAFTER));
  }

  @Test
  void getPathWorksTest() {
    setField(directoryTask, PATH, VALUE);

    assertEquals(VALUE, directoryTask.getPath());
  }

  @Test
  void setPathWorksTest() {
    setField(directoryTask, PATH, null);

    directoryTask.setPath(VALUE);
    assertEquals(VALUE, getField(directoryTask, PATH));
  }

  @Test
  void setActionWorksTest() {
    setField(directoryTask, ACTION, LIST);

    directoryTask.setAction(LIST);
    assertEquals(LIST, directoryTask.getAction());
  }

  @Test
  void getActionWorksTest() {
    setField(directoryTask, ACTION, LIST);

    assertEquals(LIST, directoryTask.getAction());
  }

  @Test
  void getWorkflowWorksTest() {
    setField(directoryTask, WORKFLOW, VALUE);

    assertEquals(VALUE, directoryTask.getWorkflow());
  }

  @Test
  void setWorkflowWorksTest() {
    setField(directoryTask, WORKFLOW, null);

    directoryTask.setWorkflow(VALUE);
    assertEquals(VALUE, getField(directoryTask, WORKFLOW));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(directoryTask, attribute, value);
    });

    directoryTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(directoryTask, attribute));
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
        helperFieldMap(NULL_STR, NULL_STR, null), 
        helperFieldMap("",       "",       LIST)
      ),
      Arguments.of(
        helperFieldMap(VALUE,    NULL_STR, null), 
        helperFieldMap(VALUE,    "",       LIST)
      ),
      Arguments.of(
        helperFieldMap(NULL_STR, VALUE,    null), 
        helperFieldMap("",       VALUE,    LIST)
      ),
      Arguments.of(
        helperFieldMap(NULL_STR, NULL_STR, WRITE),
        helperFieldMap("",       "",       WRITE)
      )
    ).stream();
  }

  /**
   * Helper method for reducing in line code repetition.
   *
   * @param path      Initial value for 'path' field
   * @param workflow  Initial value for 'workflow' field
   * @param action    Initial value for 'action' field
   * @return          Map of the provided values
   */
  private static Map<String, Object> helperFieldMap(String path, String workflow, DirectoryAction action) {
    Map<String, Object> map = new HashMap<>();

    map.put(PATH, path);
    map.put(WORKFLOW, workflow);
    map.put(ACTION, action);

    return map;
  }

}
