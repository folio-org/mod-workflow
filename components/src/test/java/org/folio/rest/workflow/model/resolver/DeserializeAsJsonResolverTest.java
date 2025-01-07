package org.folio.rest.workflow.model.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.folio.rest.workflow.model.AbstractGateway;
import org.folio.rest.workflow.model.AbstractProcess;
import org.folio.rest.workflow.model.AbstractTask;
import org.folio.rest.workflow.model.CompressFileTask;
import org.folio.rest.workflow.model.Condition;
import org.folio.rest.workflow.model.ConnectTo;
import org.folio.rest.workflow.model.DatabaseConnectionTask;
import org.folio.rest.workflow.model.DatabaseDisconnectTask;
import org.folio.rest.workflow.model.DatabaseQueryTask;
import org.folio.rest.workflow.model.DirectoryTask;
import org.folio.rest.workflow.model.EmailTask;
import org.folio.rest.workflow.model.EmbeddedInput;
import org.folio.rest.workflow.model.EmbeddedLoopReference;
import org.folio.rest.workflow.model.EmbeddedProcessor;
import org.folio.rest.workflow.model.EmbeddedRequest;
import org.folio.rest.workflow.model.EmbeddedVariable;
import org.folio.rest.workflow.model.EndEvent;
import org.folio.rest.workflow.model.EventSubprocess;
import org.folio.rest.workflow.model.ExclusiveGateway;
import org.folio.rest.workflow.model.FileTask;
import org.folio.rest.workflow.model.FtpTask;
import org.folio.rest.workflow.model.InclusiveGateway;
import org.folio.rest.workflow.model.InputTask;
import org.folio.rest.workflow.model.MoveToLastGateway;
import org.folio.rest.workflow.model.MoveToNode;
import org.folio.rest.workflow.model.Node;
import org.folio.rest.workflow.model.ParallelGateway;
import org.folio.rest.workflow.model.ProcessorTask;
import org.folio.rest.workflow.model.ReceiveTask;
import org.folio.rest.workflow.model.RequestTask;
import org.folio.rest.workflow.model.ScriptTask;
import org.folio.rest.workflow.model.Setup;
import org.folio.rest.workflow.model.StartEvent;
import org.folio.rest.workflow.model.Subprocess;
import org.folio.rest.workflow.model.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeserializeAsJsonResolverTest {

  private ObjectMapper mapper;

  @Mock
  private DatabindContext databindContext;

  @InjectMocks
  private DeserializeAsJsonResolver deserializeAsJsonResolver;

  @BeforeEach
  void beforeEach() {
    mapper = new ObjectMapper();
  }

  @Test
  void idFromValueWorksTest() {
    String result = deserializeAsJsonResolver.idFromValue(new String());

    assertEquals(String.class.getSimpleName(), result);
  }

  @Test
  void idFromValueAndTypeWorksTest() {
    String result = deserializeAsJsonResolver.idFromValueAndType(new String(), JsonNode.class);

    assertEquals(String.class.getSimpleName(), result);
  }

  @Test
  void getMechanismWorksTest() {
    Id result = deserializeAsJsonResolver.getMechanism();

    assertEquals(Id.SIMPLE_NAME, result);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = { "doesNotExist", "12345" })
  void typeFromIdWorksExpectNullTest(String id) {
    JavaType result = deserializeAsJsonResolver.typeFromId(databindContext, id);

    assertNull(result);
  }

  @ParameterizedTest
  @MethodSource("provideClassNameFor")
  void typeFromIdWorksWithKnownClassesTest(String className, String simpleName) {
    when(databindContext.getTypeFactory()).thenReturn(mapper.getTypeFactory());

    JavaType result = deserializeAsJsonResolver.typeFromId(databindContext, className);

    assertEquals(simpleName, result.getRawClass().getSimpleName());
  }

  /**
   * Helper function for parameterized test providing tests for the testingClasses.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - String className The name of the class.
   *     - String simpleName The simple name returned by the class.
   */
  private static Stream<Arguments> provideClassNameFor() {
    return Stream.of(
      Arguments.of("AbstractGateway",        AbstractGateway.class.getSimpleName()),
      Arguments.of("AbstractProcess",        AbstractProcess.class.getSimpleName()),
      Arguments.of("AbstractTask",           AbstractTask.class.getSimpleName()),
      Arguments.of("CompressFileTask",       CompressFileTask.class.getSimpleName()),
      Arguments.of("Condition",              Condition.class.getSimpleName()),
      Arguments.of("ConnectTo",              ConnectTo.class.getSimpleName()),
      Arguments.of("DatabaseConnectionTask", DatabaseConnectionTask.class.getSimpleName()),
      Arguments.of("DatabaseDisconnectTask", DatabaseDisconnectTask.class.getSimpleName()),
      Arguments.of("DatabaseQueryTask",      DatabaseQueryTask.class.getSimpleName()),
      Arguments.of("DirectoryTask",          DirectoryTask.class.getSimpleName()),
      Arguments.of("EmailTask",              EmailTask.class.getSimpleName()),
      Arguments.of("EmbeddedInput",          EmbeddedInput.class.getSimpleName()),
      Arguments.of("EmbeddedLoopReference",  EmbeddedLoopReference.class.getSimpleName()),
      Arguments.of("EmbeddedProcessor",      EmbeddedProcessor.class.getSimpleName()),
      Arguments.of("EmbeddedRequest",        EmbeddedRequest.class.getSimpleName()),
      Arguments.of("EmbeddedVariable",       EmbeddedVariable.class.getSimpleName()),
      Arguments.of("EndEvent",               EndEvent.class.getSimpleName()),
      Arguments.of("EventSubprocess",        EventSubprocess.class.getSimpleName()),
      Arguments.of("ExclusiveGateway",       ExclusiveGateway.class.getSimpleName()),
      Arguments.of("FileTask",               FileTask.class.getSimpleName()),
      Arguments.of("FtpTask",                FtpTask.class.getSimpleName()),
      Arguments.of("InclusiveGateway",       InclusiveGateway.class.getSimpleName()),
      Arguments.of("InputTask",              InputTask.class.getSimpleName()),
      Arguments.of("MoveToLastGateway",      MoveToLastGateway.class.getSimpleName()),
      Arguments.of("MoveToNode",             MoveToNode.class.getSimpleName()),
      Arguments.of("Node",                   Node.class.getSimpleName()),
      Arguments.of("ParallelGateway",        ParallelGateway.class.getSimpleName()),
      Arguments.of("ProcessorTask",          ProcessorTask.class.getSimpleName()),
      Arguments.of("ReceiveTask",            ReceiveTask.class.getSimpleName()),
      Arguments.of("RequestTask",            RequestTask.class.getSimpleName()),
      Arguments.of("ScriptTask",             ScriptTask.class.getSimpleName()),
      Arguments.of("Setup",                  Setup.class.getSimpleName()),
      Arguments.of("StartEvent",             StartEvent.class.getSimpleName()),
      Arguments.of("Subprocess",             Subprocess.class.getSimpleName()),
      Arguments.of("Workflow",               Workflow.class.getSimpleName())
    );
  }

}
