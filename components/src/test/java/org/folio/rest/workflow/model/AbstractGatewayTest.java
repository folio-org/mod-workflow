package org.folio.rest.workflow.model;

import static org.folio.rest.workflow.enums.Direction.CONVERGING;
import static org.folio.rest.workflow.enums.Direction.UNSPECIFIED;
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

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String DIRECTION     = "direction";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

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
    setField(abstractGateway, ID, VALUE);

    assertEquals(VALUE, abstractGateway.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractGateway, ID, null);

    abstractGateway.setId(VALUE);
    assertEquals(VALUE, getField(abstractGateway, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractGateway, NAME, VALUE);

    assertEquals(VALUE, abstractGateway.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractGateway, NAME, null);

    abstractGateway.setName(VALUE);
    assertEquals(VALUE, getField(abstractGateway, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractGateway, DESCRIPTION, VALUE);

    assertEquals(VALUE, abstractGateway.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractGateway, DESCRIPTION, null);

    abstractGateway.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractGateway, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractGateway, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, abstractGateway.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractGateway, DESERIALIZEAS, null);

    abstractGateway.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractGateway, DESERIALIZEAS));
  }

  @Test
  void getDirectionWorksTest() {
    setField(abstractGateway, DIRECTION, CONVERGING);

    assertEquals(CONVERGING, abstractGateway.getDirection());
  }

  @Test
  void setDirectionWorksTest() {
    setField(abstractGateway, DIRECTION, null);

    abstractGateway.setDirection(CONVERGING);
    assertEquals(CONVERGING, getField(abstractGateway, DIRECTION));
  }

  @Test
  void getNodesWorksTest() {
    setField(abstractGateway, NODES, nodes);

    assertEquals(nodes, abstractGateway.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(abstractGateway, NODES, null);

    abstractGateway.setNodes(nodes);
    assertEquals(nodes, getField(abstractGateway, NODES));
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

    final List<Node> emptyList = new ArrayList<>();

    return List.of(
      Arguments.of(
        helperFieldMap(null,        null),
        helperFieldMap(UNSPECIFIED, emptyList)
      ),
      Arguments.of(
        helperFieldMap(CONVERGING,  null),
        helperFieldMap(CONVERGING,  emptyList)
      ),
      Arguments.of(
        helperFieldMap(null,        nodeList),
        helperFieldMap(UNSPECIFIED, nodeList)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param direction The direction value.
   * @param nodes The nodes value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Direction direction, List<Node> nodes) {
    final Map<String, Object> map = new HashMap<>();

    map.put(DIRECTION, direction);
    map.put(NODES, nodes);

    return map;
  }

  private static class Impl extends AbstractGateway { }

  private static class NodeImpl extends Node { }

}
