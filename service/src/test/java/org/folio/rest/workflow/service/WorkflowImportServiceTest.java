package org.folio.rest.workflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import org.folio.rest.workflow.config.JunitHelperWebMvcConfig;
import org.folio.rest.workflow.exception.WorkflowImportAlreadyImported;
import org.folio.rest.workflow.exception.WorkflowImportException;
import org.folio.rest.workflow.exception.WorkflowImportInvalidOrMissingProperty;
import org.folio.rest.workflow.exception.WorkflowImportRequiredFileMissing;
import org.folio.rest.workflow.model.Workflow;
import org.folio.rest.workflow.model.repo.NodeRepo;
import org.folio.rest.workflow.model.repo.TriggerRepo;
import org.folio.rest.workflow.model.repo.WorkflowRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import tools.jackson.databind.json.JsonMapper;

@ActiveProfiles("test")
@SpringJUnitWebConfig({
  JunitHelperWebMvcConfig.class,
  WorkflowImportService.class
})
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class WorkflowImportServiceTest {

  private static final String WORKFLOW_UUID = "7dcd302f-a438-4ca5-a7eb-21653610d46f";

  @InjectMocks
  private WorkflowImportService workflowImportService;

  @MockitoBean
  private NodeRepo nodeRepo;

  @MockitoBean
  private TriggerRepo triggerRepo;

  @MockitoBean
  private WorkflowRepo workflowRepo;

  @MockitoSpyBean
  private JsonMapper mapper;

  @Mock
  private Page<Workflow> page;

  private Resource fwzFakeResource;

  private Resource fwzBzip2AsBz2Resource;

  private Resource fwzBzip2Resource;

  private Resource fwzGzipAsGzResource;

  private Resource fwzGzipBadCodeResource;

  private Resource fwzGzipBadDeserializeasResource;

  private Resource fwzGzipBadIdResource;

  private Resource fwzGzipBadNodesResource;

  private Resource fwzGzipBadScriptformatResource;

  private Resource fwzGzipBadVersionResource;

  private Resource fwzGzipJavaResource;

  private Resource fwzGzipMisCodeResource;

  private Resource fwzGzipMisDeserializeasResource;

  private Resource fwzGzipMisIdResource;

  private Resource fwzGzipMisIdWorkflowJsonResource;

  private Resource fwzGzipMisScriptResource;

  private Resource fwzGzipMisSetupResource;

  private Resource fwzGzipMisVersionResource;

  private Resource fwzGzipMisWorkflowResource;

  private Resource fwzGzipOddFilesResource;

  private Resource fwzGzipPythonResource;

  private Resource fwzGzipResource;

  private Resource fwzGzipRubyResource;

  private Resource fwzGzipUnVerResource;

  private Resource fwzZipAsZipResource;

  private Resource fwzZipResource;

  private Workflow workflow;

  @BeforeEach
  void beforeEach() {
    workflow = new Workflow();
    workflow.setId(WORKFLOW_UUID);

    when(workflowRepo.save(any())).thenReturn(workflow);

    fwzFakeResource = new ClassPathResource("fwz/unit_test_fake.fwz");
    fwzBzip2AsBz2Resource = new ClassPathResource("fwz/unit_test_bzip2.tar.bz2");
    fwzBzip2Resource = new ClassPathResource("fwz/unit_test_bzip2.fwz");
    fwzGzipAsGzResource = new ClassPathResource("fwz/unit_test_gzip.tar.gz");
    fwzGzipBadCodeResource = new ClassPathResource("fwz/unit_test_gzip-bad_code.fwz");
    fwzGzipBadDeserializeasResource = new ClassPathResource("fwz/unit_test_gzip-bad_deserializeas.fwz");
    fwzGzipBadIdResource = new ClassPathResource("fwz/unit_test_gzip-bad_id.fwz");
    fwzGzipBadNodesResource = new ClassPathResource("fwz/unit_test_gzip-bad_nodes.fwz");
    fwzGzipBadScriptformatResource = new ClassPathResource("fwz/unit_test_gzip-bad_scriptformat.fwz");
    fwzGzipBadVersionResource = new ClassPathResource("fwz/unit_test_gzip-bad_version.fwz");
    fwzGzipJavaResource = new ClassPathResource("fwz/unit_test_gzip-java.fwz");
    fwzGzipMisCodeResource = new ClassPathResource("fwz/unit_test_gzip-missing_code.fwz");
    fwzGzipMisDeserializeasResource = new ClassPathResource("fwz/unit_test_gzip-missing_deserializeas.fwz");
    fwzGzipMisIdResource = new ClassPathResource("fwz/unit_test_gzip-missing_id.fwz");
    fwzGzipMisIdWorkflowJsonResource = new ClassPathResource("fwz/unit_test_gzip-missing_id_workflow_json.fwz");
    fwzGzipMisScriptResource = new ClassPathResource("fwz/unit_test_gzip-missing_script.fwz");
    fwzGzipMisSetupResource = new ClassPathResource("fwz/unit_test_gzip-missing_setup.fwz");
    fwzGzipMisVersionResource = new ClassPathResource("fwz/unit_test_gzip-missing_version.fwz");
    fwzGzipMisWorkflowResource = new ClassPathResource("fwz/unit_test_gzip-missing_workflow.fwz");
    fwzGzipOddFilesResource = new ClassPathResource("fwz/unit_test_gzip-odd_files.fwz");
    fwzGzipPythonResource = new ClassPathResource("fwz/unit_test_gzip-python.fwz");
    fwzGzipResource = new ClassPathResource("fwz/unit_test_gzip.fwz");
    fwzGzipRubyResource = new ClassPathResource("fwz/unit_test_gzip-ruby.fwz");
    fwzGzipUnVerResource = new ClassPathResource("fwz/unit_test_gzip-unknown_version.fwz");
    fwzZipAsZipResource = new ClassPathResource("fwz/unit_test_zip.zip");
    fwzZipResource = new ClassPathResource("fwz/unit_test_zip.fwz");
  }

  @Test
  void importFileThrowsExceptionForFakeTest() {
    assertThrows(WorkflowImportException.class, () ->
      workflowImportService.importFile(fwzFakeResource)
    );
  }

  @Test
  void importFileThrowsExceptionForExistingIdTest() {
    when(workflowRepo.existsById(anyString())).thenReturn(true);

    assertThrows(WorkflowImportAlreadyImported.class, () ->
      workflowImportService.importFile(fwzGzipResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithBadCodeTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipBadCodeResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithBadDeserializeasTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipBadDeserializeasResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithBadIdTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipBadIdResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithBadScriptformatTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipBadScriptformatResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithBadNodesTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipBadNodesResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithMisCodeTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipMisCodeResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithMisDeserializeasTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipMisDeserializeasResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithMisIdTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipMisIdResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithMisIdWorkflowJsonTest() {
    assertThrows(WorkflowImportInvalidOrMissingProperty.class, () ->
      workflowImportService.importFile(fwzGzipMisIdWorkflowJsonResource)
    );
  }


  @Test
  void importFileThrowsExceptionWithMisScriptTest() {
    assertThrows(WorkflowImportRequiredFileMissing.class, () ->
      workflowImportService.importFile(fwzGzipMisScriptResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithMisSetupTest() {
    assertThrows(WorkflowImportRequiredFileMissing.class, () ->
      workflowImportService.importFile(fwzGzipMisSetupResource)
    );
  }

  @Test
  void importFileThrowsExceptionWithMisWorkflowTest() {
    assertThrows(WorkflowImportRequiredFileMissing.class, () ->
      workflowImportService.importFile(fwzGzipMisWorkflowResource)
    );
  }

  @Test
  void importFileWorksForBzip2Test() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzBzip2Resource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForBzip2AsBz2Test() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzBzip2AsBz2Resource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipAsGzTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipAsGzResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithBadVersionTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipBadVersionResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithJavaTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipJavaResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithOddFilesTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipOddFilesResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithPythonTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipPythonResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithRubyTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipRubyResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithMissingVersionTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipMisVersionResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForGzipWithUnknownVersionTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzGzipUnVerResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForZipTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzZipResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

  @Test
  void importFileWorksForZipAsZipTest() throws IOException, WorkflowImportException {
    Workflow imported = workflowImportService.importFile(fwzZipAsZipResource);
    assertNotNull(imported);
    assertEquals(workflow.getId(), imported.getId());
  }

}
