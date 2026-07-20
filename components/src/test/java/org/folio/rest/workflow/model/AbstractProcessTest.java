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

  private static final String ASYNCAFTER    = "asyncAfter";
  private static final String ASYNCBEFORE   = "asyncBefore";
  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

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
    setField(abstractProcess, ID, VALUE);

    assertEquals(VALUE, abstractProcess.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractProcess, ID, null);

    abstractProcess.setId(VALUE);
    assertEquals(VALUE, getField(abstractProcess, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractProcess, NAME, VALUE);

    assertEquals(VALUE, abstractProcess.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractProcess, NAME, null);

    abstractProcess.setName(VALUE);
    assertEquals(VALUE, getField(abstractProcess, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractProcess, DESCRIPTION, VALUE);

    assertEquals(VALUE, abstractProcess.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractProcess, DESCRIPTION, null);

    abstractProcess.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractProcess, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractProcess, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, abstractProcess.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractProcess, DESERIALIZEAS, null);

    abstractProcess.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractProcess, DESERIALIZEAS));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(abstractProcess, ASYNCBEFORE, true);

    assertEquals(true, abstractProcess.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(abstractProcess, ASYNCBEFORE, false);

    abstractProcess.setAsyncBefore(true);
    assertEquals(true, getField(abstractProcess, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(abstractProcess, ASYNCAFTER, true);

    assertEquals(true, abstractProcess.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(abstractProcess, ASYNCAFTER, false);

    abstractProcess.setAsyncAfter(true);
    assertEquals(true, getField(abstractProcess, ASYNCAFTER));
  }

  @Test
  void getNodesWorksTest() {
    setField(abstractProcess, NODES, nodes);

    assertEquals(nodes, abstractProcess.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(abstractProcess, NODES, null);

    abstractProcess.setNodes(nodes);
    assertEquals(nodes, getField(abstractProcess, NODES));
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

    return List.of(
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
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param asyncBefore The asyncBefore value.
   * @param asyncAfter The asyncAfter value.
   * @param nodes The nodes value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean asyncBefore, Boolean asyncAfter, List<Node> nodes) {
    final Map<String, Object> map = new HashMap<>();

    map.put(ASYNCAFTER, asyncAfter);
    map.put(ASYNCBEFORE, asyncBefore);
    map.put(NODES, nodes);

    return map;
  }

  private static class Impl extends AbstractProcess { }

  private static class NodeImpl extends Node { }

}
