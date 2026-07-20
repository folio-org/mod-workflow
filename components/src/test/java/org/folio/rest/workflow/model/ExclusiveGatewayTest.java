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
class ExclusiveGatewayTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String DIRECTION     = "direction";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

  @Mock
  private Node node;

  private List<Node> nodes;

  private ExclusiveGateway exclusiveGateway;

  @BeforeEach
  void beforeEach() {
    exclusiveGateway = new ExclusiveGateway();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(exclusiveGateway, ID, VALUE);

    assertEquals(VALUE, exclusiveGateway.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(exclusiveGateway, ID, null);

    exclusiveGateway.setId(VALUE);
    assertEquals(VALUE, getField(exclusiveGateway, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(exclusiveGateway, NAME, VALUE);

    assertEquals(VALUE, exclusiveGateway.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(exclusiveGateway, NAME, null);

    exclusiveGateway.setName(VALUE);
    assertEquals(VALUE, getField(exclusiveGateway, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(exclusiveGateway, DESCRIPTION, VALUE);

    assertEquals(VALUE, exclusiveGateway.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(exclusiveGateway, DESCRIPTION, null);

    exclusiveGateway.setDescription(VALUE);
    assertEquals(VALUE, getField(exclusiveGateway, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(exclusiveGateway, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, exclusiveGateway.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(exclusiveGateway, DESERIALIZEAS, null);

    exclusiveGateway.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(exclusiveGateway, DESERIALIZEAS));
  }

  @Test
  void getDirectionWorksTest() {
    setField(exclusiveGateway, DIRECTION, Direction.CONVERGING);

    assertEquals(Direction.CONVERGING, exclusiveGateway.getDirection());
  }

  @Test
  void setDirectionWorksTest() {
    setField(exclusiveGateway, DIRECTION, null);

    exclusiveGateway.setDirection(Direction.CONVERGING);
    assertEquals(Direction.CONVERGING, getField(exclusiveGateway, DIRECTION));
  }

  @Test
  void getNodesWorksTest() {
    setField(exclusiveGateway, NODES, nodes);

    assertEquals(nodes, exclusiveGateway.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(exclusiveGateway, NODES, null);

    exclusiveGateway.setNodes(nodes);
    assertEquals(nodes, getField(exclusiveGateway, NODES));
  }

}
