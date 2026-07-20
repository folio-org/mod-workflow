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
class ParallelGatewayTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String DIRECTION     = "direction";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";

  @Mock
  private Node node;

  private List<Node> nodes;

  private ParallelGateway parallelGateway;

  @BeforeEach
  void beforeEach() {
    parallelGateway = new ParallelGateway();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(parallelGateway, ID, VALUE);

    assertEquals(VALUE, parallelGateway.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(parallelGateway, ID, null);

    parallelGateway.setId(VALUE);
    assertEquals(VALUE, getField(parallelGateway, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(parallelGateway, NAME, VALUE);

    assertEquals(VALUE, parallelGateway.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(parallelGateway, NAME, null);

    parallelGateway.setName(VALUE);
    assertEquals(VALUE, getField(parallelGateway, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(parallelGateway, DESCRIPTION, VALUE);

    assertEquals(VALUE, parallelGateway.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(parallelGateway, DESCRIPTION, null);

    parallelGateway.setDescription(VALUE);
    assertEquals(VALUE, getField(parallelGateway, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(parallelGateway, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, parallelGateway.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(parallelGateway, DESERIALIZEAS, null);

    parallelGateway.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(parallelGateway, DESERIALIZEAS));
  }

  @Test
  void getDirectionWorksTest() {
    setField(parallelGateway, DIRECTION, Direction.CONVERGING);

    assertEquals(Direction.CONVERGING, parallelGateway.getDirection());
  }

  @Test
  void setDirectionWorksTest() {
    setField(parallelGateway, DIRECTION, null);

    parallelGateway.setDirection(Direction.CONVERGING);
    assertEquals(Direction.CONVERGING, getField(parallelGateway, DIRECTION));
  }

  @Test
  void getNodesWorksTest() {
    setField(parallelGateway, NODES, nodes);

    assertEquals(nodes, parallelGateway.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(parallelGateway, NODES, null);

    parallelGateway.setNodes(nodes);
    assertEquals(nodes, getField(parallelGateway, NODES));
  }

}
