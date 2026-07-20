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
class InclusiveGatewayTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String DIRECTION     = "direction";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

  @Mock
  private Node node;

  private List<Node> nodes;

  private InclusiveGateway abstractGateway;

  @BeforeEach
  void beforeEach() {
    abstractGateway = new InclusiveGateway();
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
    setField(abstractGateway, DIRECTION, Direction.CONVERGING);

    assertEquals(Direction.CONVERGING, abstractGateway.getDirection());
  }

  @Test
  void setDirectionWorksTest() {
    setField(abstractGateway, DIRECTION, null);

    abstractGateway.setDirection(Direction.CONVERGING);
    assertEquals(Direction.CONVERGING, getField(abstractGateway, DIRECTION));
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

}
