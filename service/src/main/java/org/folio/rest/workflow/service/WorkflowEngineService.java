package org.folio.rest.workflow.service;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.folio.rest.workflow.dto.WorkflowDto;
import org.folio.rest.workflow.dto.WorkflowOperationalDto;
import org.folio.rest.workflow.dto.WorkflowOperationalNodeDto;
import org.folio.rest.workflow.exception.WorkflowDeploymentNotActivated;
import org.folio.rest.workflow.exception.WorkflowDeploymentNotFound;
import org.folio.rest.workflow.exception.WorkflowEngineServiceException;
import org.folio.rest.workflow.exception.WorkflowNotFoundException;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.model.repo.WorkflowRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

@Service
public class WorkflowEngineService {

  private static final Log LOG = LogFactory.getLog(WorkflowEngineService.class);

  private static final String WORKFLOW_ENGINE_ACTIVATE_URL_TEMPLATE = "%s%s/workflow-engine/workflows/activate";
  private static final String WORKFLOW_ENGINE_DEACTIVATE_URL_TEMPLATE = "%s%s/workflow-engine/workflows/deactivate";

  private static final String PROCESS_DEFINITION_START_URL_TEMPLATE = "%s%s/process-definition/{arg1}/start";
  private static final String PROCESS_DEFINITION_GET_URL_TEMPLATE = "%s%s/process-definition?deploymentId={arg1}&versionTag={arg2}&maxResults=1";

  private static final String HISTORY_PROCESS_INSTANCE_URL_TEMPLATE = "%s%s/history/process-instance?processDefinitionId={arg1}&sortBy=startTime&sortOrder=asc";
  private static final String HISTORY_INCIDENT_URL_TEMPLATE = "%s%s/history/incident?processInstanceId={arg1}&sortBy=createTime&sortOrder=asc";

  @Value("${tenant.headerName:X-Okapi-Tenant}")
  private String tenantHeaderName;

  @Value("${okapi.auth.tokenHeaderName:X-Okapi-Token}")
  private String tokenHeaderName;

  @Value("${okapi.url}")
  private String okapiUrl;

  @Value("${okapi.camunda.base-path:/}")
  private String basePath;

  @Value("${okapi.camunda.rest-path:/camunda}")
  private String restPath;

  private DeleteService deleteService;

  private WorkflowRepo workflowRepo;

  private JsonMapper mapper;

  private RestTemplate restTemplate;

  public WorkflowEngineService(DeleteService deleteService, WorkflowRepo workflowRepo, JsonMapper mapper, RestTemplateBuilder restTemplateBuilder) {
    this.deleteService = deleteService;
    this.workflowRepo = workflowRepo;
    this.mapper = mapper;
    this.restTemplate = restTemplateBuilder.build();
  }

  public Workflow activate(String workflowId, String tenant, String token)
      throws WorkflowEngineServiceException, WorkflowNotFoundException {

    WorkflowDto workflow = workflowRepo.getViewById(workflowId, WorkflowDto.class);
    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    return sendWorkflowRequest(workflow, WORKFLOW_ENGINE_ACTIVATE_URL_TEMPLATE, tenant, token);
  }

  public Workflow deactivate(String workflowId, String tenant, String token)
      throws WorkflowEngineServiceException, WorkflowNotFoundException {

    WorkflowDto workflow = workflowRepo.getViewById(workflowId, WorkflowDto.class);
    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    return sendWorkflowRequest(workflow, WORKFLOW_ENGINE_DEACTIVATE_URL_TEMPLATE, tenant, token);
  }

  /**
   * Delete the Workflow from the given Workflow ID.
   *
   * @param workflowId ID of the Workflow to delete.
   * @param tenant The tenant to use.
   * @param token The token to use.
   *
   * @throws WorkflowDeploymentNotActivated If workflow is not activated.
   * @throws WorkflowEngineServiceException When the request fails in some way preventing the return of an HttpEntity.
   */
  @Transactional(rollbackFor = Exception.class)
  public void delete(String workflowId, String tenant, String token)
      throws WorkflowDeploymentNotActivated, WorkflowEngineServiceException, WorkflowNotFoundException {

    final WorkflowOperationalNodeDto workflow = workflowRepo.getViewById(workflowId, WorkflowOperationalNodeDto.class);

    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    // Deployment ID will not exist if it has not been activated.
    if (workflow.getDeploymentIds() != null && workflow.getDeploymentIds().isEmpty()) {
      deleteDeployedWorkflow(workflow, tenant, token);
    }

    deleteService.deleteNodes(workflow);

    workflowRepo.deleteById(workflowId);
  }

  /**
   * Check that the Workflow exists or throw an exception.
   *
   * @param workflowId The Workflow to check.
   * @param tenant The tenant of the workflow.
   *
   * @throws WorkflowNotFoundException The exception when the Workflow is not found.
   */
  public void exists(String workflowId, String tenant) throws WorkflowNotFoundException {
    if (!workflowRepo.existsById(workflowId)) {
      throw new WorkflowNotFoundException(workflowId);
    }

    final WorkflowOperationalDto workflow = workflowRepo.getViewById(workflowId, WorkflowOperationalDto.class);
    if (workflow == null /* ||tenant == null || !tenant.equals(workflow.getTenantId())*/) {
      throw new WorkflowNotFoundException(workflowId);
    }
    System.out.print("\n\n\nDEBUG: tenant = " + workflow.getTenantId() + "\n\n\n");
  }

  public JsonNode start(String workflowId, String tenant, String token, JsonNode context)
      throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowNotFoundException, WorkflowDeploymentNotActivated {

    WorkflowOperationalDto workflow = workflowRepo.getViewById(workflowId, WorkflowOperationalDto.class);

    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    List<String> deploymentIds = workflow.getDeploymentIds();
    if (deploymentIds == null || deploymentIds.isEmpty()) {
      throw new WorkflowEngineServiceException(String.format("Workflow ID '%s' has no associated Deployment ID!", workflowId));
    }
    
    ArrayNode workflowStartNode = mapper.createArrayNode();

    for (String deploymentId : deploymentIds) {
      JsonNode deploymentStart = startForDeployment(workflowId, deploymentId, tenant, token, context);
      
      ObjectNode deploymentStartWrapper = mapper.createObjectNode();
      deploymentStartWrapper.put("deploymentId", deploymentId)
        .set("start", deploymentStart);
      
      workflowStartNode.add(deploymentStartWrapper);
    }

    return workflowStartNode;
  }

  public JsonNode startForDeployment(String workflowId, String deploymentId, String tenant, String token, JsonNode context)
      throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowNotFoundException, WorkflowDeploymentNotActivated {
    
    WorkflowOperationalDto workflow = workflowRepo.getViewById(workflowId, WorkflowOperationalDto.class);

    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    String version = workflow.getVersionTag();

    JsonNode definition = fetchFirstDeploymentDefinition(workflowId, deploymentId, version, tenant, token);

    if (!definition.has("id") ) {
      throw new WorkflowEngineServiceException(String.format("Workflow ID '%s' with Deployment ID '%s' has no definition ID!", workflowId, deploymentId));
    }

    String definitionId = definition.get("id").asString();

    HttpEntity<JsonNode> contextHttpEntity = new HttpEntity<>(context, headers(tenant, token));

    String url = String.format(PROCESS_DEFINITION_START_URL_TEMPLATE, okapiUrl, restPath);
    Map<String, Object> params = Map.of("arg1", definitionId);

    try {
      final ResponseEntity<JsonNode> response = exchange(url, HttpMethod.POST, contextHttpEntity, JsonNode.class, params);

      if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new WorkflowNotFoundException(workflowId);
      }

      return response.getBody();
    } catch (RestClientException e) {
      throw new WorkflowEngineServiceException(String.format("Failed to start workflow: %s!", e.getMessage()), e);
    }
  }

  public JsonNode history(String workflowId, String tenant, String token)
      throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    WorkflowOperationalDto workflow = workflowRepo.getViewById(workflowId, WorkflowOperationalDto.class);

    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    List<String> deploymentIds = workflow.getDeploymentIds();
    ArrayNode workflowHistoryNode = mapper.createArrayNode();

    for (String deploymentId : deploymentIds) {
      JsonNode deploymentHistory = historyForDeployment(workflowId, deploymentId, tenant, token);

      ObjectNode deploymentHistoryWrapper = mapper.createObjectNode();
      deploymentHistoryWrapper.put("deploymentId", deploymentId)
        .set("history", deploymentHistory);
      
        workflowHistoryNode.add(deploymentHistoryWrapper);
    }

    return workflowHistoryNode;
  }

  /**
   * Fetch the history of a workflow for a given deployment ID.
   * 
   * @param workflowId The workflow ID.
   * @param deploymentId The activated workflow deployment ID.
   * @param tenant The tenant.
   * @param token The token.
   */
  public JsonNode historyForDeployment(String workflowId, String deploymentId, String tenant, String token)
      throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {
    
    WorkflowOperationalDto workflow = workflowRepo.getViewById(workflowId, WorkflowOperationalDto.class);

    if (workflow == null || tenant == null || !tenant.equals(workflow.getTenantId())) {
      throw new WorkflowNotFoundException(workflowId);
    }

    String version = workflow.getVersionTag();

    JsonNode processDefinition = fetchFirstDeploymentDefinition(workflowId, deploymentId, version, tenant, token);
    String processDefinitionId = processDefinition.get("id").asString();

    ArrayNode instances = fetchProcessInstanceHistory(processDefinitionId, tenant, token);

    Iterator<JsonNode> iter = instances.iterator();

    while (iter.hasNext()) {
      JsonNode instance = iter.next();

      if (instance.has("id")) {
        String processInstanceId = instance.get("id").asString();

        ((ObjectNode) instance).withArray("incidents")
          .addAll(fetchIncidentsHistory(processInstanceId, tenant, token));
      }
    }

    return instances;
  }

  /**
   * Delete the deployed workflow.
   *
   * @param workflow The workflow to delete the deployment of.
   * @param tenant   The tenant to use.
   * @param token    The token to use.
   *
   * @throws WorkflowDeploymentNotActivated If workflow is not activated.
   * @throws WorkflowEngineServiceException When the request fails in some way preventing the return of an HttpEntity.
   */
  private void deleteDeployedWorkflow(WorkflowOperationalNodeDto workflow, String tenant, String token)
    throws WorkflowDeploymentNotActivated, WorkflowEngineServiceException, WorkflowNotFoundException {

    final List<String> deploymentIds = workflow.getDeploymentIds();
    final String version = workflow.getVersionTag();

    for (String deploymentId : deploymentIds) {
      deleteDeployedWorkflowForDeployment(workflow.getId(), deploymentId, version, tenant, token);
    }
  }

  /**
   * Delete a deployment of a workflow from a given deployment ID.
   * 
   * @param workflowId The workflow ID.
   * @param deploymentId The deployment ID.
   * @param version The Workflow version number.
   * @param tenant The tenant to use.
   * @param token the token to use.
   * 
   * @throws WorkflowDeploymentNotActivated If workflow is not activated.
   * @throws WorkflowEngineServiceException When the request fails in some way preventing the return of an HttpEntity.
   * @throws WorkflowNotFoundException If the workflow could not be found during deactivation.
   */
  private void deleteDeployedWorkflowForDeployment(String workflowId, String deploymentId, String version, String tenant, String token)
    throws WorkflowDeploymentNotActivated, WorkflowEngineServiceException, WorkflowNotFoundException {
    
    final ResponseEntity<ArrayNode> response = fetchDeploymentDefinitions(workflowId, deploymentId, version, tenant, token);

      if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NOT_FOUND) {

        final ArrayNode definitions = response.hasBody()
          ? response.getBody()
          : null;

        if (definitions != null && response.getStatusCode() != HttpStatus.NOT_FOUND && !definitions.isEmpty()) {
          try {
            deactivate(workflowId, tenant, token);
          } catch (WorkflowEngineServiceException e) {
            final String message = getDeleteDeactivationFailureMessage(workflowId, deploymentId, version, e.getMessage());
            throw new WorkflowEngineServiceException(message, e);
          } catch (WorkflowNotFoundException e) {
            final String message = getDeleteDeactivationFailureMessage(workflowId, deploymentId, version, e.getMessage());
            throw new WorkflowNotFoundException(message, e);
          }
        }
      }
  }

  /**
   * Provide the common formatted string for deactivation failure during deletion.
   * 
   * @param workflowId The workflow ID.
   * @param deploymentId The deployment ID.
   * @param version The Workflow version number.
   * @param exceptionMessage The original exception message.
   * 
   * @return A formatted exception string.
   */
  private String getDeleteDeactivationFailureMessage(String workflowId, String deploymentId, String version, String exceptionMessage) {
    return String.format(
      "Failed to deleted Workflow ID '%s' for Deployment Id '%s' and Version Tag '%s' due to deactivation failure: %s!",
      workflowId,
      deploymentId,
      version,
      exceptionMessage
    );
  }

  /**
   * Fetch the first matching deployment for the given workflow.
   *
   * @param workflowId   The Workflow ID, for use in exception logs.
   * @param deploymentId The Deployment ID to find.
   * @param version      The version tag to use.
   * @param tenant       The FOLIO tenant.
   * @param token        The session token.
   *
   * @return The first matching response.
   *
   * @throws WorkflowDeploymentNotActivated If workflow is not activated.
   * @throws WorkflowDeploymentNotFound     On not found.
   * @throws WorkflowEngineServiceException On error.
   */
  private JsonNode fetchFirstDeploymentDefinition(String workflowId, String deploymentId, String version, String tenant, String token)
      throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated {

    final ResponseEntity<ArrayNode> response = fetchDeploymentDefinitions(workflowId, deploymentId, version, tenant, token);

    if (response != null && (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NOT_FOUND)) {
      final ArrayNode definitions = response.hasBody()
        ? response.getBody()
        : null;

      if (definitions == null || response.getStatusCode() == HttpStatus.NOT_FOUND || definitions.isEmpty() || definitions.get(0) == null) {
        throw new WorkflowDeploymentNotFound(String.format("Workflow with Deployment ID '%s' for Workflow ID '%s' is not found.", deploymentId, workflowId));
      }

      return definitions.get(0);
    }

    throw new WorkflowEngineServiceException("Unable to get workflow process definition from workflow engine!");
  }

  /**
   * Fetch all deployments, but return the response entity to allow caller to handle.
   *
   * @param workflowId   The Workflow ID for when there is an error.
   * @param deploymentId The activated Workflow deployment ID.
   * @param version      The Workflow version number.
   * @param tenant       The tenant.
   * @param token        The token.
   *
   * @return The response entity.
   *
   * @throws WorkflowDeploymentNotActivated If workflow is not activated.
   * @throws WorkflowEngineServiceException On error.
   */
  private ResponseEntity<ArrayNode> fetchDeploymentDefinitions(String workflowId, String deploymentId, String version, String tenant, String token)
      throws WorkflowDeploymentNotActivated, WorkflowEngineServiceException {

    if (deploymentId == null) {
      throw new WorkflowDeploymentNotActivated(String.format("Workflow with ID of '%s' is not activated.", workflowId));
    }

    final HttpEntity<Void> httpEntity = new HttpEntity<>(headers(tenant, token));
    final String url = String.format(PROCESS_DEFINITION_GET_URL_TEMPLATE, okapiUrl, restPath);
    final Map<String, Object> params = Map.of("arg1", deploymentId, "arg2", version);

    try {
      return exchange(url, HttpMethod.GET, httpEntity, ArrayNode.class, params);
    } catch (RestClientException e) {
      throw new WorkflowEngineServiceException(String.format("Failed to deployment definition: %s!", e.getMessage()), e);
    }
  }

  private ArrayNode fetchProcessInstanceHistory(String processDefinitionId, String tenant, String token)
      throws WorkflowEngineServiceException {

    HttpEntity<Void> httpEntity = new HttpEntity<>(headers(tenant, token));

    String url = String.format(HISTORY_PROCESS_INSTANCE_URL_TEMPLATE, okapiUrl, restPath);
    Map<String, Object> params = Map.of("arg1", processDefinitionId);

    try {
      ResponseEntity<ArrayNode> response = exchange(url, HttpMethod.GET, httpEntity, ArrayNode.class, params);

      final ArrayNode definitions = response.hasBody()
        ? response.getBody()
        : null;

      if (definitions != null && response.getStatusCode() == HttpStatus.OK) {
        return definitions;
      }

      throw new WorkflowEngineServiceException("Unable to get workflow process instance history from workflow engine!");
    } catch (RestClientException e) {
      throw new WorkflowEngineServiceException(String.format("Failed to fetch process instance history: %s!", e.getMessage()), e);
    }
  }

  private ArrayNode fetchIncidentsHistory(String processInstanceId, String tenant, String token) throws WorkflowEngineServiceException {

    HttpEntity<Void> httpEntity = new HttpEntity<>(headers(tenant, token));

    String url = String.format(HISTORY_INCIDENT_URL_TEMPLATE, okapiUrl, restPath);
    Map<String, Object> params = Map.of("arg1", processInstanceId);

    try {
      ResponseEntity<ArrayNode> response = exchange(url, HttpMethod.GET, httpEntity, ArrayNode.class, params);

      ArrayNode incidents = response.hasBody()
        ? response.getBody()
        : null;

      if (incidents == null || response.getStatusCode() != HttpStatus.OK) {
        LOG.debug("Unable to get workflow incidents history from workflow engine!");

        incidents = mapper.createArrayNode();
      }

      return incidents;
    } catch (RestClientException e) {
      throw new WorkflowEngineServiceException(String.format("Failed to fetch incident history: %s!", e.getMessage()), e);
    }
  }

  /**
   * Send a HTTP request to perform an action to the Workflow Engine end point and update the workflow locally.
   *
   * @param workflow The workflow associated with the action.
   * @param requestPath The end point being used.
   * @param tenant The tenant to use.
   * @param token The token to use.
   *
   * @return The updated Workflow.
   *
   * @throws WorkflowEngineServiceException On certain request failures or when failed to update the Workflow.
   */
  private Workflow sendWorkflowRequest(WorkflowDto workflow, String requestPath, String tenant, String token)
      throws WorkflowEngineServiceException {

    HttpEntity<WorkflowDto> entity = new HttpEntity<>(workflow, headers(tenant, token));
    String url = String.format(requestPath, okapiUrl, basePath);

    LOG.debug(String.format("Send Okapi workflow engine request %s %s", HttpMethod.POST, url));

    try {
      ResponseEntity<Workflow> response = exchange(url, HttpMethod.POST, entity, Workflow.class, Map.of());

      if (response.getStatusCode() == HttpStatus.OK) {
        Workflow responseWorkflow = response.getBody();

        if (responseWorkflow != null && tenant != null && tenant.equals(responseWorkflow.getTenantId())) {
          List<String> deploymentIds = responseWorkflow.getDeploymentIds();
          Boolean active = responseWorkflow.getActive();

          responseWorkflow.setChecksum(workflow.getChecksum());
          responseWorkflow.setCreatedOn(workflow.getCreatedOn());
          responseWorkflow.setUpdatedOn(Instant.now());

          LOG.info(String.format("Workflow is active = %s, deploymentIDs = %s", Boolean.TRUE.equals(active), deploymentIds.toString()));
          return workflowRepo.save(responseWorkflow);
        }
      }
    } catch (RestClientException e) {
      throw new WorkflowEngineServiceException(String.format("Failed to send workflow request: %s!", e.getMessage()), e);
    }

    throw new WorkflowEngineServiceException("Unable to get updated workflow from workflow engine!");
  }

  private HttpHeaders headers(String tenant, String token) {
    HttpHeaders requestHeaders = new HttpHeaders();
    LOG.debug(String.format("Request Headers: tenant '%s' and token '%s'.", tenant, token));

    if (tenant != null) {
      requestHeaders.add(tenantHeaderName, tenant);
    }

    if (token != null) {
      requestHeaders.add(tokenHeaderName, token);
    }

    requestHeaders.add("Content-Type", "application/json");
    return requestHeaders;
  }

  private <T> ResponseEntity<T> exchange(String url, HttpMethod method, HttpEntity<?> request, Class<T> responseType, Map<String, ? extends Object> params) {
    LOG.debug(String.format("Exchange for %s %s %s %s", responseType.getSimpleName(), method, url, params));
    return this.restTemplate.exchange(url, method, request, responseType, params);
  }

}
