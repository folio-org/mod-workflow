package org.folio.rest.workflow.dto;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;

import org.folio.rest.workflow.model.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkflowOperationalDtoTest {

  private List<String> deploymentIds;

  private WorkflowOperationalDto workflowDto;

  @BeforeEach
  void beforeEach() {
    deploymentIds = List.of(VALUE);
    workflowDto = new Impl();
  }

  @Test
  void getVersionTagWorksTest() {
    setField(workflowDto, "versionTag", VALUE);

    assertEquals(VALUE, workflowDto.getVersionTag());
  }

  @Test
  void setVersionTagWorksTest() {
    setField(workflowDto, "versionTag", null);

    workflowDto.setVersionTag(VALUE);
    assertEquals(VALUE, getField(workflowDto, "versionTag"));
  }

  @Test
  void getDeploymentIdsWorksTest() {
    setField(workflowDto, "deploymentIds", deploymentIds);

    assertEquals(deploymentIds, workflowDto.getDeploymentIds());
  }

  @Test
  void setDeploymentIdsWorksTest() {
    setField(workflowDto, "deploymentIds", null);

    workflowDto.setDeploymentIds(deploymentIds);
    assertEquals(deploymentIds, getField(workflowDto, "deploymentIds"));
  }

  private static class Impl extends Workflow implements WorkflowOperationalDto { }

}
