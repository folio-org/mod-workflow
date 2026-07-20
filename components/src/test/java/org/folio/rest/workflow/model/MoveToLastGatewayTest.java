package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.List;
import org.folio.rest.workflow.enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MoveToLastGatewayTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

  @Mock
  private Node node;

  private List<Node> nodes;

  private MoveToLastGateway moveToLastGateway;

  @BeforeEach
  void beforeEach() {
    moveToLastGateway = new MoveToLastGateway();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(moveToLastGateway, ID, VALUE);

    assertEquals(VALUE, moveToLastGateway.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(moveToLastGateway, ID, null);

    moveToLastGateway.setId(VALUE);
    assertEquals(VALUE, getField(moveToLastGateway, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(moveToLastGateway, NAME, VALUE);

    assertEquals(VALUE, moveToLastGateway.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(moveToLastGateway, NAME, null);

    moveToLastGateway.setName(VALUE);
    assertEquals(VALUE, getField(moveToLastGateway, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(moveToLastGateway, DESCRIPTION, VALUE);

    assertEquals(VALUE, moveToLastGateway.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(moveToLastGateway, DESCRIPTION, null);

    moveToLastGateway.setDescription(VALUE);
    assertEquals(VALUE, getField(moveToLastGateway, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(moveToLastGateway, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, moveToLastGateway.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(moveToLastGateway, DESERIALIZEAS, null);

    moveToLastGateway.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(moveToLastGateway, DESERIALIZEAS));
  }

  @Test
  void getDirectionWorksTest() {
    setField(moveToLastGateway, "direction", Direction.CONVERGING);

    assertEquals(Direction.CONVERGING, moveToLastGateway.getDirection());
  }

  @Test
  void setDirectionWorksTest() {
    setField(moveToLastGateway, "direction", null);

    moveToLastGateway.setDirection(Direction.CONVERGING);
    assertEquals(Direction.CONVERGING, getField(moveToLastGateway, "direction"));
  }

  @Test
  void getNodesWorksTest() {
    setField(moveToLastGateway, NODES, nodes);

    assertEquals(nodes, moveToLastGateway.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(moveToLastGateway, NODES, null);

    moveToLastGateway.setNodes(nodes);
    assertEquals(nodes, getField(moveToLastGateway, NODES));
  }

}
