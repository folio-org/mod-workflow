package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
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
class AbstractTaskTest {

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  @Mock
  private Node node;

  private List<Node> nodes;

  private AbstractTask abstractTask;

  @BeforeEach
  void beforeEach() {
    abstractTask = new Impl();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(abstractTask, "id", VALUE);

    assertEquals(VALUE, abstractTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractTask, "id", null);

    abstractTask.setId(VALUE);
    assertEquals(VALUE, getField(abstractTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractTask, "name", VALUE);

    assertEquals(VALUE, abstractTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractTask, "name", null);

    abstractTask.setName(VALUE);
    assertEquals(VALUE, getField(abstractTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractTask, "description", VALUE);

    assertEquals(VALUE, abstractTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractTask, "description", null);

    abstractTask.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractTask, "deserializeAs", VALUE);

    assertEquals(VALUE, abstractTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractTask, "deserializeAs", null);

    abstractTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(abstractTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, abstractTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(abstractTask, "inputVariables", null);

    abstractTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(abstractTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(abstractTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, abstractTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(abstractTask, "outputVariable", null);

    abstractTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(abstractTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(abstractTask, "asyncBefore", true);

    assertEquals(true, abstractTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(abstractTask, "asyncBefore", false);

    abstractTask.setAsyncBefore(true);
    assertEquals(true, getField(abstractTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(abstractTask, "asyncAfter", true);

    assertEquals(true, abstractTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(abstractTask, "asyncAfter", false);

    abstractTask.setAsyncAfter(true);
    assertEquals(true, getField(abstractTask, "asyncAfter"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(abstractTask, attribute, value);
    });

    abstractTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(abstractTask, attribute));
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
    final Set<EmbeddedVariable> evList = new HashSet<>();
    evList.add(new EmbeddedVariable());

    final Set<EmbeddedVariable> emptyList = new HashSet<>();

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,  null,  null),
        helperFieldMap(false, false, emptyList)
      ),
      Arguments.of(
        helperFieldMap(true,  null,  null),
        helperFieldMap(true,  false, emptyList)
      ),
      Arguments.of(
        helperFieldMap(null,  true,  null),
        helperFieldMap(false, true,  emptyList)
      ),
      Arguments.of(
        helperFieldMap(null,  null,  evList),
        helperFieldMap(false, false, evList)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param asyncBefore The asyncBefore value.
   * @param asyncAfter The asyncAfter value.
   * @param inputVariables The inputVariables value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean asyncBefore, Boolean asyncAfter, Set<EmbeddedVariable> inputVariables) {
    final Map<String, Object> map = new HashMap<>();

    map.put("asyncBefore", asyncBefore);
    map.put("asyncAfter", asyncAfter);
    map.put("inputVariables", inputVariables);

    return map;
  }

  private static class Impl extends AbstractTask { }

}
