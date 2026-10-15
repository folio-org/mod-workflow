package org.folio.rest.workflow.dto;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;
import org.folio.rest.workflow.model.Node;
import org.folio.rest.workflow.model.StartEvent;
import org.folio.rest.workflow.model.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkflowOperationalNodeDtoTest {

  private static final StartEvent NODE = new StartEvent();

  private static final List<Node> NODES = List.of(NODE);

  private WorkflowOperationalNodeDto dto;

  private String[] deploymentids;

  @BeforeEach
  void beforeEach() {
    dto = new Impl();
    deploymentids = new String[] { VALUE };
  }

  @Test
  void getDeploymentIdWorksTest() {

    setField(dto, "deploymentIds", deploymentids);

    assertEquals(deploymentids, dto.getDeploymentIds());
  }

  @Test
  void setDeploymentIdWorksTest() {

    setField(dto, "deploymentIds", null);

    dto.setDeploymentIds(deploymentids);
    assertEquals(deploymentids, getField(dto, "deploymentIds"));
  }

  @Test
  void getIdWorksTest() {

    setField(dto, "id", VALUE);

    assertEquals(VALUE, dto.getId());
  }

  @Test
  void setIdWorksTest() {

    setField(dto, "id", null);

    dto.setId(VALUE);
    assertEquals(VALUE, getField(dto, "id"));
  }

  @Test
  void getNodesWorksTest() {

    setField(dto, "nodes", NODES);

    assertEquals(NODES, dto.getNodes());
  }

  @Test
  void setNodesWorksTest() {

    setField(dto, "nodes", null);

    dto.setNodes(NODES);
    assertEquals(NODES, getField(dto, "nodes"));
  }

  @Test
  void getVersionTagWorksTest() {

    setField(dto, "versionTag", VALUE);

    assertEquals(VALUE, dto.getVersionTag());
  }

  @Test
  void setVersionTagWorksTest() {

    setField(dto, "versionTag", null);

    dto.setVersionTag(VALUE);
    assertEquals(VALUE, getField(dto, "versionTag"));
  }

  private static class Impl extends Workflow implements WorkflowOperationalNodeDto { }

}
