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
import org.folio.rest.workflow.enums.Direction;
import org.folio.rest.workflow.enums.InputAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractGatewayTest {

  @Mock
  private Node node;

  private List<Node> nodes;

  private AbstractGateway abstractGateway;

  @BeforeEach
  void beforeEach() {
    abstractGateway = new Impl();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(abstractGateway, "id", VALUE);

    assertEquals(VALUE, abstractGateway.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractGateway, "id", null);

    abstractGateway.setId(VALUE);
    assertEquals(VALUE, getField(abstractGateway, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractGateway, "name", VALUE);

    assertEquals(VALUE, abstractGateway.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractGateway, "name", null);

    abstractGateway.setName(VALUE);
    assertEquals(VALUE, getField(abstractGateway, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractGateway, "description", VALUE);

    assertEquals(VALUE, abstractGateway.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractGateway, "description", null);

    abstractGateway.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractGateway, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractGateway, "deserializeAs", VALUE);

    assertEquals(VALUE, abstractGateway.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractGateway, "deserializeAs", null);

    abstractGateway.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractGateway, "deserializeAs"));
  }

  @Test
  void getDirectionWorksTest() {
    setField(abstractGateway, "direction", Direction.CONVERGING);

    assertEquals(Direction.CONVERGING, abstractGateway.getDirection());
  }

  @Test
  void setDirectionWorksTest() {
    setField(abstractGateway, "direction", null);

    abstractGateway.setDirection(Direction.CONVERGING);
    assertEquals(Direction.CONVERGING, getField(abstractGateway, "direction"));
  }

  @Test
  void getNodesWorksTest() {
    setField(abstractGateway, "nodes", nodes);

    assertEquals(nodes, abstractGateway.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(abstractGateway, "nodes", null);

    abstractGateway.setNodes(nodes);
    assertEquals(nodes, getField(abstractGateway, "nodes"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(abstractGateway, attribute, value);
    });

    abstractGateway.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(abstractGateway, attribute));
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

    final ArrayList<InputAttribute> attrList = new ArrayList<>();
    attrList.add(InputAttribute.MAX);

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,                  null),
        helperFieldMap(Direction.UNSPECIFIED, new ArrayList<>())
      ),
      Arguments.of(
        helperFieldMap(Direction.CONVERGING,  null),
        helperFieldMap(Direction.CONVERGING,  new ArrayList<>())
      ),
      Arguments.of(
        helperFieldMap(null,                  nodeList),
        helperFieldMap(Direction.UNSPECIFIED, nodeList)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param direction The direction value.
   * @param nodes The nodes value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Direction direction, List<Node> nodes) {
    final Map<String, Object> map = new HashMap<>();

    map.put("direction", direction);
    map.put("nodes", nodes);

    return map;
  }

  private static class Impl extends AbstractGateway { }

  private static class NodeImpl extends Node { }

}
