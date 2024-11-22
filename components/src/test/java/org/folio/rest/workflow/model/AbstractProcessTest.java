package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
class AbstractProcessTest {

  @Mock
  private Node node;

  private List<Node> nodes;

  private AbstractProcess abstractProcess;

  @BeforeEach
  void beforeEach() {
    abstractProcess = new Impl();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(abstractProcess, "id", VALUE);

    assertEquals(VALUE, abstractProcess.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractProcess, "id", null);

    abstractProcess.setId(VALUE);
    assertEquals(VALUE, getField(abstractProcess, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractProcess, "name", VALUE);

    assertEquals(VALUE, abstractProcess.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractProcess, "name", null);

    abstractProcess.setName(VALUE);
    assertEquals(VALUE, getField(abstractProcess, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractProcess, "description", VALUE);

    assertEquals(VALUE, abstractProcess.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractProcess, "description", null);

    abstractProcess.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractProcess, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractProcess, "deserializeAs", VALUE);

    assertEquals(VALUE, abstractProcess.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractProcess, "deserializeAs", null);

    abstractProcess.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractProcess, "deserializeAs"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(abstractProcess, "asyncBefore", true);

    assertEquals(true, abstractProcess.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(abstractProcess, "asyncBefore", false);

    abstractProcess.setAsyncBefore(true);
    assertEquals(true, getField(abstractProcess, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(abstractProcess, "asyncAfter", true);

    assertEquals(true, abstractProcess.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(abstractProcess, "asyncAfter", false);

    abstractProcess.setAsyncAfter(true);
    assertEquals(true, getField(abstractProcess, "asyncAfter"));
  }

  @Test
  void getNodesWorksTest() {
    setField(abstractProcess, "nodes", nodes);

    assertEquals(nodes, abstractProcess.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(abstractProcess, "nodes", null);

    abstractProcess.setNodes(nodes);
    assertEquals(nodes, getField(abstractProcess, "nodes"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(abstractProcess, attribute, value);
    });

    abstractProcess.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(abstractProcess, attribute));
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
    final List<Node> nodeList = new ArrayList<>();
    nodeList.add(new NodeImpl());

    final List<Node> emptyList = new ArrayList<>();

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
        helperFieldMap(null,  null,  nodeList),
        helperFieldMap(false, false, nodeList)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param asyncBefore The asyncBefore value.
   * @param asyncAfter The asyncAfter value.
   * @param nodes The nodes value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean asyncBefore, Boolean asyncAfter, List<Node> nodes) {
    final Map<String, Object> map = new HashMap<>();

    map.put("asyncBefore", asyncBefore);
    map.put("asyncAfter", asyncAfter);
    map.put("nodes", nodes);

    return map;
  }

  private static class Impl extends AbstractProcess { }

  private static class NodeImpl extends Node { }

}
