package org.folio.rest.workflow.model;

import static org.folio.rest.workflow.enums.FileOp.DELETE;
import static org.folio.rest.workflow.enums.FileOp.READ;
import static org.folio.rest.workflow.enums.FileOp.WRITE;
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
import org.folio.rest.workflow.enums.FileOp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileTaskTest {

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";
  private static final String OP             = "op";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private FileTask fileTask;

  @BeforeEach
  void beforeEach() {
    fileTask = new FileTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(fileTask, ID, VALUE);

    assertEquals(VALUE, fileTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(fileTask, ID, null);

    fileTask.setId(VALUE);
    assertEquals(VALUE, getField(fileTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(fileTask, NAME, VALUE);

    assertEquals(VALUE, fileTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(fileTask, NAME, null);

    fileTask.setName(VALUE);
    assertEquals(VALUE, getField(fileTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(fileTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, fileTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(fileTask, DESCRIPTION, null);

    fileTask.setDescription(VALUE);
    assertEquals(VALUE, getField(fileTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(fileTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, fileTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(fileTask, DESERIALIZEAS, null);

    fileTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(fileTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(fileTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, fileTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(fileTask, INPUTVARIABLES, null);

    fileTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(fileTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(fileTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, fileTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(fileTask, OUTPUTVARIABLE, null);

    fileTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(fileTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(fileTask, ASYNCBEFORE, true);

    assertEquals(true, fileTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(fileTask, ASYNCBEFORE, false);

    fileTask.setAsyncBefore(true);
    assertEquals(true, getField(fileTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(fileTask, ASYNCAFTER, true);

    assertEquals(true, fileTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(fileTask, ASYNCAFTER, false);

    fileTask.setAsyncAfter(true);
    assertEquals(true, getField(fileTask, ASYNCAFTER));
  }

  @Test
  void getOpWorksTest() {
    setField(fileTask, OP, DELETE);

    assertEquals(DELETE, fileTask.getOp());
  }

  @Test
  void setOpWorksTest() {
    setField(fileTask, OP, null);

    fileTask.setOp(DELETE);
    assertEquals(DELETE, getField(fileTask, OP));
  }

  @Test
  void getPathWorksTest() {
    setField(fileTask, "path", VALUE);

    assertEquals(VALUE, fileTask.getPath());
  }

  @Test
  void setPathWorksTest() {
    setField(fileTask, "path", null);

    fileTask.setPath(VALUE);
    assertEquals(VALUE, getField(fileTask, "path"));
  }

  @Test
  void getTargetWorksTest() {
    setField(fileTask, "target", VALUE);

    assertEquals(VALUE, fileTask.getTarget());
  }

  @Test
  void setTargetWorksTest() {
    setField(fileTask, "target", null);

    fileTask.setTarget(VALUE);
    assertEquals(VALUE, getField(fileTask, "target"));
  }

  @Test
  void getLineWorksTest() {
    setField(fileTask, "line", VALUE);

    assertEquals(VALUE, fileTask.getLine());
  }

  @Test
  void setLineWorksTest() {
    setField(fileTask, "line", null);

    fileTask.setLine(VALUE);
    assertEquals(VALUE, getField(fileTask, "line"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(fileTask, attribute, value);
    });

    fileTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(fileTask, attribute));
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
        helperFieldMap(null,  NULL_STR),
        helperFieldMap(READ,  "")
      ),
      Arguments.of(
        helperFieldMap(WRITE, NULL_STR),
        helperFieldMap(WRITE, "")
      ),
      Arguments.of(
        helperFieldMap(null,  VALUE),
        helperFieldMap(READ,  VALUE)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param op The op value.
   * @param path The path value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(FileOp op, String path ) {
    final Map<String, Object> map = new HashMap<>();

    map.put(OP, op);
    map.put("path", path);

    return map;
  }

}
