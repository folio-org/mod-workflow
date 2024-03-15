package org.folio.rest.workflow.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.folio.rest.workflow.exception.WorkflowEngineServiceException;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.service.WorkflowEngineService;
import org.folio.spring.tenant.annotation.TenantHeader;
import org.folio.spring.web.annotation.TokenHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/workflows", "/workflows/"})
public class WorkflowController {

  private WorkflowEngineService workflowEngineService;

  @Autowired
  public WorkflowController(WorkflowEngineService workflowEngineService) {
    this.workflowEngineService = workflowEngineService;
  }

  @PutMapping({"/{id}/activate", "/{id}/activate/"})
  public Workflow activateWorkflow(
    @PathVariable String id,
    @TenantHeader String tenant,
    @TokenHeader String token
  ) throws WorkflowEngineServiceException {
    log.info("Activating: {}", id);
    return workflowEngineService.activate(id, tenant, token);
  }

  @PutMapping({"/{id}/deactivate", "/{id}/deactivate/"})
  public Workflow deactivateWorkflow(
    @PathVariable String id,
    @TenantHeader String tenant,
    @TokenHeader String token
  ) throws WorkflowEngineServiceException {
    log.info("Deactivating: {}", id);
    return workflowEngineService.deactivate(id, tenant, token);
  }

  @PostMapping({"/{id}/start", "/{id}/start/"})
  public JsonNode startWorkflow(
    @PathVariable String id,
    @TenantHeader String tenant,
    @TokenHeader String token,
    @RequestBody JsonNode context
  ) throws WorkflowEngineServiceException {
    log.info("Starting: {} with context {}", id, context);
    return workflowEngineService.start(id, tenant, token, context);
  }

  @GetMapping({"/{id}/history", "/{id}/history/"})
  public JsonNode workflowHistory(
    @PathVariable String id,
    @TenantHeader String tenant,
    @TokenHeader String token
  ) throws WorkflowEngineServiceException {
    log.debug("Retrieving History: {}", id);
    return workflowEngineService.history(id, tenant, token);
  }

}
