package org.folio.rest.workflow.controller;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.model.repo.WorkflowRepo;
import org.folio.spring.tenant.annotation.TenantHeader;
import org.folio.spring.web.annotation.TokenHeader;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class WorkflowControllerOverride {

  private static final Log LOG = LogFactory.getLog(WorkflowControllerOverride.class);

  private WorkflowRepo workflowRepo;

  public WorkflowControllerOverride(WorkflowRepo workflowRepo) {
    this.workflowRepo = workflowRepo;
  }

  @PostMapping(value = "/workflows", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<Object> postWorkflow(
    @TenantHeader String tenant,
    @TokenHeader String token,
    @RequestBody Workflow workflow
  ) throws URISyntaxException {
    System.out.print("\n\n\nDEBUG: Aaaaaaaahhhhhhhhhhhhhhhhhhh\n\n\n\nahhhhhhhh!\n\n\n");
    
    LOG.debug(String.format("Creating new workflow %s", workflow == null ? "" : workflow.getId()));

    final Workflow result = workflowRepo.save(workflow);
    System.out.print("\n\n\nDEBUG: result id = " + result.getId() + "\n\n\n");
    URI location = new URI(String.format("/workflows/%s", result.getId()));

    return ResponseEntity.created(location).body(result);
    //return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

}
