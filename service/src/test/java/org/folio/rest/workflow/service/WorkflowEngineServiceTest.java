package org.folio.rest.workflow.service;

import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_TENANT;
import static org.folio.spring.test.mock.MockMvcConstant.OKAPI_TOKEN;
import static org.folio.spring.test.mock.MockMvcConstant.UUID;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.List;
import org.folio.rest.workflow.dto.WorkflowDto;
import org.folio.rest.workflow.dto.WorkflowOperationalDto;
import org.folio.rest.workflow.dto.WorkflowOperationalNodeDto;
import org.folio.rest.workflow.exception.WorkflowDeploymentNotActivated;
import org.folio.rest.workflow.exception.WorkflowDeploymentNotFound;
import org.folio.rest.workflow.exception.WorkflowEngineServiceException;
import org.folio.rest.workflow.exception.WorkflowNotFoundException;
import org.folio.rest.workflow.model.Node;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.model.repo.WorkflowRepo;
import org.folio.spring.test.helper.MapperHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class WorkflowEngineServiceTest {

  private static final RestClientException RC_EXC = new RestClientException("Trigger Failure");

  private static final String DEACTIVATE = "workflow-engine/workflows/deactivate";
  private static final String HISTORY_INCIDENT = "history/incident";
  private static final String HISTORY_INSTANCE = "history/process-instance";
  private static final String PROCESS_DEFINITION = "process-definition";

  @Mock
  private DeleteService deleteService;

  @Mock
  private WorkflowRepo workflowRepo;

  @Mock
  private RestTemplate restTemplate;

  private WorkflowAsDto workflow;

  private WorkflowAsOperationalDto workflowOperational;

  private WorkflowAsOperationalNodeDto workflowOperationalNode;

  private WorkflowEngineService workflowEngineService;

  private  List<Node> nodes;

  private JsonMapper mapper;

  private List<String> deploymentIds;

  @BeforeEach
  void beforeEach() {
    mapper = MapperHelper.build();
    workflowEngineService = new WorkflowEngineService(deleteService, workflowRepo, mapper, new RestTemplateBuilder());

    nodes = List.of();
    deploymentIds = List.of(UUID);

    workflow = new WorkflowAsDto();
    workflow.setId(UUID);
    workflow.setTenantId(OKAPI_TENANT);
    workflow.setDeploymentIds(deploymentIds);
    workflow.setName(VALUE);

    workflowOperational = new WorkflowAsOperationalDto();
    workflowOperational.setId(UUID);
    workflowOperational.setTenantId(OKAPI_TENANT);
    workflowOperational.setDeploymentIds(deploymentIds);
    workflowOperational.setName(VALUE);
    workflowOperational.setVersionTag(VALUE);

    workflowOperationalNode = new WorkflowAsOperationalNodeDto();
    workflowOperationalNode.setId(UUID);
    workflowOperationalNode.setTenantId(OKAPI_TENANT);
    workflowOperationalNode.setDeploymentIds(deploymentIds);
    workflowOperationalNode.setName(VALUE);
    workflowOperationalNode.setNodes(nodes);
    workflowOperationalNode.setVersionTag(VALUE);

    setField(workflowEngineService, "workflowRepo", workflowRepo);
    setField(workflowEngineService, "restTemplate", restTemplate);
    setField(workflowEngineService, "tenantHeaderName", OKAPI_TENANT);
    setField(workflowEngineService, "tokenHeaderName", OKAPI_TOKEN);
  }

  @Test
  void activateWorksTest() throws WorkflowEngineServiceException, WorkflowNotFoundException {
    WorkflowDto workflowDto = (WorkflowDto) workflow;
    ResponseEntity<String> activateResponseEntity = new ResponseEntity<>(HttpStatus.OK);
    setField(activateResponseEntity, "body", UUID);

    when(workflowRepo.getViewById(anyString(), ArgumentMatchers.<Class<WorkflowDto>>any())).thenReturn(workflowDto);
    when(workflowRepo.getReferenceById(anyString())).thenReturn(workflow);

    when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class), anyMap()))
      .thenReturn(activateResponseEntity);

    when(workflowRepo.save(any())).thenReturn(workflow);

    workflowEngineService.activate(UUID, OKAPI_TENANT, OKAPI_TOKEN);

    verify(workflowRepo).save(any());
    verify(workflowRepo, never()).deleteById(anyString());
  }

  @Test
  void activateWithoutHeadersTest() throws WorkflowEngineServiceException, WorkflowNotFoundException {
    WorkflowDto workflowDto = (WorkflowDto) workflow;
    ResponseEntity<String> activateResponseEntity = new ResponseEntity<>(HttpStatus.OK);
    setField(activateResponseEntity, "body", UUID);

    when(workflowRepo.getViewById(anyString(), ArgumentMatchers.<Class<WorkflowDto>>any())).thenReturn(workflowDto);
    when(workflowRepo.getReferenceById(anyString())).thenReturn(workflow);

    when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class), anyMap()))
      .thenReturn(activateResponseEntity);

    when(workflowRepo.save(any())).thenReturn(workflow);

    workflowEngineService.activate(UUID, OKAPI_TENANT, null);

    verify(workflowRepo).save(any());
    verify(workflowRepo, never()).deleteById(anyString());
  }

  @Test
  void deactivateWorksTest() throws WorkflowEngineServiceException, WorkflowNotFoundException {
    WorkflowDto workflowDto = (WorkflowDto) workflow;
    ResponseEntity<Object> deactivateResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

    when(workflowRepo.getViewById(anyString(), ArgumentMatchers.<Class<WorkflowDto>>any())).thenReturn(workflowDto);
    when(workflowRepo.getReferenceById(anyString())).thenReturn(workflow);

    when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class), anyMap()))
      .thenReturn(deactivateResponseEntity);

    when(workflowRepo.save(any())).thenReturn(workflow);

    workflowEngineService.deactivateDeployments(UUID, OKAPI_TENANT, OKAPI_TOKEN);

    verify(workflowRepo).save(any());
    verify(workflowRepo, never()).deleteById(anyString());
  }

  @Test
  void deleteWorksTest() throws WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    ResponseEntity<ArrayNode> responseArray = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    ResponseEntity<Object> deactivateResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

    final WorkflowDto workflowDto = (WorkflowDto) workflow;

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalNodeDto.class))).thenReturn(workflowOperationalNode);
    when(workflowRepo.getViewById(anyString(), eq(WorkflowDto.class))).thenReturn(workflowDto);
    when(workflowRepo.getReferenceById(anyString())).thenReturn(workflow);

    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(responseArray);

    lenient().when(restTemplate.exchange(contains(DEACTIVATE), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class), anyMap()))
      .thenReturn(deactivateResponseEntity);

    when(workflowRepo.save(any())).thenReturn(workflow);

    doNothing().when(deleteService).deleteNodes(any(WorkflowOperationalNodeDto.class));
    doNothing().when(workflowRepo).deleteById(anyString());

    workflowEngineService.delete(UUID, OKAPI_TENANT, OKAPI_TOKEN);

    verify(workflowRepo).save(any());
    verify(workflowRepo).deleteById(anyString());
  }

  @Test
  void deleteNotActiveWorksTest() throws WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    ArrayNode arrayNodeSingle = mapper.createArrayNode();

    ResponseEntity<ArrayNode> responseArray = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalNodeDto.class))).thenReturn(workflowOperationalNode);

    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(responseArray);

    doNothing().when(deleteService).deleteNodes(any(WorkflowOperationalNodeDto.class));
    doNothing().when(workflowRepo).deleteById(anyString());

    workflowEngineService.delete(UUID, OKAPI_TENANT, OKAPI_TOKEN);

    verify(workflowRepo).deleteById(anyString());
  }

  @Test
  void deleteThrowsExceptionUnableGetUpdatedTest() {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    ResponseEntity<ArrayNode> responseArray = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);

    ResponseEntity<Object> deactivateResponseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);

    final WorkflowDto workflowDto = (WorkflowDto) workflow;

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalNodeDto.class))).thenReturn(workflowOperationalNode);
    when(workflowRepo.getViewById(anyString(), eq(WorkflowDto.class))).thenReturn(workflowDto);

    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(responseArray);

    when(restTemplate.exchange(contains(DEACTIVATE), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class), anyMap()))
      .thenReturn(deactivateResponseEntity);

    assertThrows(WorkflowEngineServiceException.class, () ->
      workflowEngineService.delete(UUID, OKAPI_TENANT, OKAPI_TOKEN)
    );

    verify(workflowRepo, never()).save(any());
    verify(workflowRepo, never()).deleteById(anyString());
  }

  @Test
  void deleteThrowsExceptionFailedToSaveTest() {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    ResponseEntity<Object> deactivateResponseEntity = new ResponseEntity<>(HttpStatus.OK);

    final WorkflowDto workflowDto = (WorkflowDto) workflow;

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalNodeDto.class))).thenReturn(workflowOperationalNode);
    when(workflowRepo.getViewById(anyString(), eq(WorkflowDto.class))).thenReturn(workflowDto);
    when(workflowRepo.getReferenceById(anyString())).thenReturn(workflow);

    when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(DEACTIVATE), any(HttpMethod.class), any(HttpEntity.class), eq(Object.class), anyMap()))
      .thenReturn(deactivateResponseEntity);

    when(workflowRepo.save(any())).thenThrow(RC_EXC);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.delete(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
    // java.lang.ClassCastException

    verify(workflowRepo).save(any());
    verify(workflowRepo, never()).deleteById(anyString());
  }

  @Test
  void existsWorksTest() {
    when(workflowRepo.existsById(anyString())).thenReturn(true);
    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    assertDoesNotThrow(() -> workflowEngineService.exists(UUID, OKAPI_TENANT));
  }

  @Test
  void existsThrowsExceptionWorkflowNotFoundTest() {
    when(workflowRepo.existsById(anyString())).thenReturn(false);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.exists(UUID, OKAPI_TENANT);
    });
  }

  @Test
  void startWorksTest()
    throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowNotFoundException, WorkflowDeploymentNotActivated {

    ArrayNode startArrayNode = mapper.createArrayNode();
    ObjectNode deploymentStartWrapper = mapper.createObjectNode();
    deploymentStartWrapper.put("deploymentId", UUID);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    ResponseEntity<JsonNode> workflowEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    JsonNode context = mapper.createObjectNode();

    ArrayNode arrayNode = mapper.createArrayNode();
    arrayNode.add(objectNode);
    setField(workflowEntity, "body", arrayNode);

    deploymentStartWrapper.set("start", arrayNode);
    startArrayNode.add(deploymentStartWrapper);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.POST), any(HttpEntity.class), eq(JsonNode.class), anyMap()))
      .thenReturn(workflowEntity);

    final JsonNode response = workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    assertEquals(startArrayNode, response);
  }

  @Test
  void startThrowsExceptionHttpNotOkTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);

    JsonNode context = mapper.createObjectNode();
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class)))
      .thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startThrowsExceptionOnBadExchangeTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);

    JsonNode context = mapper.createObjectNode();
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class)))
      .thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.POST), any(HttpEntity.class), eq(JsonNode.class), anyMap()))
      .thenThrow(RC_EXC);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startThrowsExceptionDeploymentIdMissingTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    workflowOperationalDto.setDeploymentIds(null);

    ResponseEntity<ArrayNode> responseEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    JsonNode context = mapper.createObjectNode();
    ObjectNode objectNode = mapper.createObjectNode();

    ArrayNode arrayNode = mapper.createArrayNode();
    arrayNode.add(objectNode);
    setField(responseEntity, "body", arrayNode);

    when(workflowRepo.getViewById(anyString(), ArgumentMatchers.<Class<WorkflowOperationalDto>>any())).thenReturn(workflowOperationalDto);

    Exception exception = assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });

    assertTrue(exception.getMessage().contains(UUID));
  }

  @Test
  void startThrowsExceptionWorkflowNullTest() {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(null);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startThrowsExceptionWorkflowInvalidTenant() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    workflowOperationalDto.setTenantId(null);

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startThrowsExceptionDefinitionNoIdTest() {

    ObjectNode objectNode = mapper.createObjectNode();

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    Exception exception = assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });

    assertTrue(exception.getMessage().contains("has no definition ID"));
  }

  @Test
  void startThrowsExceptionWorkflowNotFoundTest() {

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    final ResponseEntity<JsonNode> workflowEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.POST), any(HttpEntity.class), eq(JsonNode.class), anyMap()))
      .thenReturn(workflowEntity);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startThrowsExceptionWorkflowNotFoundViaViewTest() {

    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(null);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startThrowsExceptionWorkflowDeploymentNotFoundViaViewTest() {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    final ArrayNode arrayNodeSingle = mapper.createArrayNode();

    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    assertThrows(WorkflowDeploymentNotFound.class, () -> {
      workflowEngineService.start(UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentWorksTest()
    throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowNotFoundException, WorkflowDeploymentNotActivated {

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    ResponseEntity<JsonNode> workflowEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    JsonNode context = mapper.createObjectNode();

    ArrayNode arrayNode = mapper.createArrayNode();
    arrayNode.add(objectNode);
    setField(workflowEntity, "body", arrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.POST), any(HttpEntity.class), eq(JsonNode.class), anyMap()))
      .thenReturn(workflowEntity);

    final JsonNode response = workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    assertEquals(arrayNode, response);
  }

  @Test
  void startForDeploymentThrowsExceptionHttpNotOkTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);

    JsonNode context = mapper.createObjectNode();
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class)))
      .thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionOnBadExchangeTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);

    JsonNode context = mapper.createObjectNode();
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class)))
      .thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.POST), any(HttpEntity.class), eq(JsonNode.class), anyMap()))
      .thenThrow(RC_EXC);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionNullDeploymentId() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;

    ResponseEntity<ArrayNode> responseEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    JsonNode context = mapper.createObjectNode();
    ObjectNode objectNode = mapper.createObjectNode();

    ArrayNode arrayNode = mapper.createArrayNode();
    arrayNode.add(objectNode);
    setField(responseEntity, "body", arrayNode);

    when(workflowRepo.getViewById(anyString(), ArgumentMatchers.<Class<WorkflowOperationalDto>>any())).thenReturn(workflowOperationalDto);

    Exception exception = assertThrows(WorkflowDeploymentNotActivated.class, () -> {
      workflowEngineService.startForDeployment(UUID, null, OKAPI_TENANT, OKAPI_TOKEN, context);
    });

    assertTrue(exception.getMessage().contains(UUID));
  }

  @Test
  void startForDeploymentThrowsExceptionDeploymentIdMissingTest() {
    final WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    workflowOperationalDto.setDeploymentIds(null);

    final ArrayNode arrayNodeSingle = mapper.createArrayNode();
    final ObjectNode objectNode = mapper.createObjectNode();
    arrayNodeSingle.add(objectNode);

    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionWorkflowNullTest() {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(null);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionWorkflowInvalidTenant() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    workflowOperationalDto.setTenantId(null);

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionDefinitionNoIdTest() {

    ObjectNode objectNode = mapper.createObjectNode();

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    Exception exception = assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });

    assertTrue(exception.getMessage().contains("has no definition ID"));
  }

  @Test
  void startForDeploymentThrowsExceptionWorkflowNotFoundTest() {

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode arrayNodeSingle = mapper.createArrayNode();
    arrayNodeSingle.add(objectNode);

    final ResponseEntity<JsonNode> workflowEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.POST), any(HttpEntity.class), eq(JsonNode.class), anyMap()))
      .thenReturn(workflowEntity);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionWorkflowNotFoundViaViewTest() {

    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(null);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void startForDeploymentThrowsExceptionWorkflowDeploymentNotFoundViaViewTest() {

    final ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    final ArrayNode arrayNodeSingle = mapper.createArrayNode();

    final ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(arrayNodeSingle, HttpStatus.OK);
    final JsonNode context = mapper.createObjectNode();

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperational);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    assertThrows(WorkflowDeploymentNotFound.class, () -> {
      workflowEngineService.startForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN, context);
    });
  }

  @Test
  void historyWorksTest()
    throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> incidentEntity = new ResponseEntity<>(HttpStatus.OK);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    ObjectNode deploymentHistoryWrapper = mapper.createObjectNode();
    deploymentHistoryWrapper.put("deploymentId", UUID);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ObjectNode incidentNode = mapper.createObjectNode();
    incidentNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyInternalArrayNode = mapper.createArrayNode();
    historyInternalArrayNode.add(historyNode);
    setField(historyEntity, "body", historyInternalArrayNode);
    deploymentHistoryWrapper.set("history", historyInternalArrayNode);
    historyArrayNode.add(deploymentHistoryWrapper);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    when(restTemplate.exchange(contains(HISTORY_INCIDENT), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(incidentEntity);

    JsonNode response = workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    assertEquals(historyArrayNode, response);
  }

  @Test
  void historyWorksWithNotFoundWorkflowTest() {

    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    ObjectNode deploymentHistoryWrapper = mapper.createObjectNode();
    deploymentHistoryWrapper.put("deploymentId", UUID);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ObjectNode incidentNode = mapper.createObjectNode();
    incidentNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyInternalArrayNode = mapper.createArrayNode();
    historyInternalArrayNode.add(historyNode);
    setField(historyEntity, "body", historyInternalArrayNode);
    deploymentHistoryWrapper.set("history", historyInternalArrayNode);
    historyArrayNode.add(deploymentHistoryWrapper);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyWorksWithoutOkFetchingIncidentsHistoryTest()
    throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;

    ResponseEntity<ArrayNode> deploymentEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> incidentsEntity = new ResponseEntity<>(HttpStatus.FOUND);
    
    ArrayNode historyArrayNode = mapper.createArrayNode();
    ObjectNode processWrapper = mapper.createObjectNode();
    processWrapper.put("deploymentId", UUID);

    ObjectNode deploymentNode = mapper.createObjectNode();
    deploymentNode.put("id", UUID);

    ObjectNode processNode = mapper.createObjectNode();
    processNode.put("id", UUID);

    ArrayNode deploymentArrayNode = mapper.createArrayNode();
    deploymentArrayNode.add(deploymentNode);
    setField(deploymentEntity, "body", deploymentArrayNode);

    ArrayNode processArrayNode = mapper.createArrayNode();
    processArrayNode.add(processNode);
    setField(processEntity, "body", processArrayNode);

    ArrayNode incidentsArrayNode = mapper.createArrayNode();
    setField(incidentsEntity, "body", incidentsArrayNode);

    processWrapper.set("history", processArrayNode);
    historyArrayNode.add(processWrapper);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity, processEntity, incidentsEntity);

    JsonNode response = workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    assertEquals(historyArrayNode, response);
  }

  @Test
  void historyThrowsExceptionWithNotFoundTenantTest() {

    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    workflowOperationalDto.setTenantId(null);

    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    ObjectNode deploymentHistoryWrapper = mapper.createObjectNode();
    deploymentHistoryWrapper.put("deploymentId", UUID);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ObjectNode incidentNode = mapper.createObjectNode();
    incidentNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyInternalArrayNode = mapper.createArrayNode();
    historyInternalArrayNode.add(historyNode);
    setField(historyEntity, "body", historyInternalArrayNode);
    deploymentHistoryWrapper.set("history", historyInternalArrayNode);
    historyArrayNode.add(deploymentHistoryWrapper);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyThrowsExceptionWithNotOkHttpStatusForProcessTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    setField(historyEntity, "body", null);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyThrowsExceptionWithNullIncidentsForProcessTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    setField(historyEntity, "body", null);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyWorksThrowsExceptionForFetchingIncidentsHistoryTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyWorksThrowsExceptionForNotOkHttpOnIncidentsHistoryTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    ResponseEntity<ArrayNode> thirdEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity, thirdEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyWorksThrowsExceptionForNullIncidentsOnIncidentsHistoryTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    setField(historyEntity, "body", null);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.history(UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentWorksTest()
    throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> incidentEntity = new ResponseEntity<>(HttpStatus.OK);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ObjectNode incidentNode = mapper.createObjectNode();
    incidentNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    when(restTemplate.exchange(contains(HISTORY_INCIDENT), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(incidentEntity);

    JsonNode response = workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    assertEquals(historyArrayNode, response);
  }

  @Test
  void historyForDeploymentWorksWithNotFoundWorkflowTest() {

    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ObjectNode incidentNode = mapper.createObjectNode();
    incidentNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentWorksWithoutOkFetchingIncidentsHistoryTest()
    throws WorkflowDeploymentNotFound, WorkflowEngineServiceException, WorkflowDeploymentNotActivated, WorkflowNotFoundException {

    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;

    ResponseEntity<ArrayNode> deploymentEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> incidentsEntity = new ResponseEntity<>(HttpStatus.FOUND);

    ObjectNode deploymentNode = mapper.createObjectNode();
    deploymentNode.put("id", UUID);

    ObjectNode processNode = mapper.createObjectNode();
    processNode.put("id", UUID);

    ArrayNode deploymentArrayNode = mapper.createArrayNode();
    deploymentArrayNode.add(deploymentNode);
    setField(deploymentEntity, "body", deploymentArrayNode);

    ArrayNode processArrayNode = mapper.createArrayNode();
    processArrayNode.add(processNode);
    setField(processEntity, "body", processArrayNode);

    ArrayNode incidentsArrayNode = mapper.createArrayNode();
    setField(incidentsEntity, "body", incidentsArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity, processEntity, incidentsEntity);

    JsonNode response = workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    assertEquals(processArrayNode, response);
  }

  @Test
  void historyForDeploymentThrowsExceptionWithNotFoundTenantTest() {

    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    workflowOperationalDto.setTenantId(null);

    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ObjectNode incidentNode = mapper.createObjectNode();
    incidentNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    assertThrows(WorkflowNotFoundException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentThrowsExceptionWithNotOkHttpStatusForProcessTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    setField(historyEntity, "body", null);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentThrowsExceptionWithNullIncidentsForProcessTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    setField(historyEntity, "body", null);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentWorksThrowsExceptionForFetchingIncidentsHistoryTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentWorksThrowsExceptionForNotOkHttpOnIncidentsHistoryTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    ResponseEntity<ArrayNode> thirdEntity = new ResponseEntity<>(HttpStatus.RESET_CONTENT);

    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    ArrayNode historyArrayNode = mapper.createArrayNode();
    historyArrayNode.add(historyNode);
    setField(historyEntity, "body", historyArrayNode);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity, thirdEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  @Test
  void historyForDeploymentWorksThrowsExceptionForNullIncidentsOnIncidentsHistoryTest() {
    WorkflowOperationalDto workflowOperationalDto = (WorkflowOperationalDto) workflowOperational;
    ResponseEntity<ArrayNode> processEntity = new ResponseEntity<>(HttpStatus.OK);
    ResponseEntity<ArrayNode> historyEntity = new ResponseEntity<>(HttpStatus.OK);
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.put("id", UUID);

    ObjectNode historyNode = mapper.createObjectNode();
    historyNode.put("id", UUID);
    historyNode.put("history", VALUE);

    ArrayNode processNode = mapper.createArrayNode();
    processNode.add(objectNode);
    setField(processEntity, "body", processNode);

    setField(historyEntity, "body", null);

    when(workflowRepo.getViewById(anyString(), eq(WorkflowOperationalDto.class))).thenReturn(workflowOperationalDto);

    when(restTemplate.exchange(contains(PROCESS_DEFINITION), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(processEntity);

    when(restTemplate.exchange(contains(HISTORY_INSTANCE), eq(HttpMethod.GET), any(HttpEntity.class), eq(ArrayNode.class), anyMap()))
      .thenReturn(historyEntity);

    assertThrows(WorkflowEngineServiceException.class, () -> {
      workflowEngineService.historyForDeployment(UUID, UUID, OKAPI_TENANT, OKAPI_TOKEN);
    });
  }

  private class WorkflowAsDto extends Workflow implements WorkflowDto {}

  private class WorkflowAsOperationalDto extends Workflow implements WorkflowOperationalDto {}

  private class WorkflowAsOperationalNodeDto extends Workflow implements WorkflowOperationalNodeDto {}

}
