package org.folio.rest.workflow.model.resolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import java.util.HashMap;
import java.util.Map;
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
import org.folio.rest.workflow.model.StartEvent;
import org.folio.rest.workflow.model.Subprocess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resolve the type from the `deserializeAs` field from a `Node`.
 */
public class DeserializeAsNodeJsonResolver extends TypeIdResolverBase {

  /**
   * A map of classes that are allowed to be deserialized.
   */
  public static final Map<String, Class<? extends Node>> CLASSES = new HashMap<>();
  {{
    CLASSES.put("AbstractGateway", AbstractGateway.class);
    CLASSES.put("AbstractProcess", AbstractProcess.class);
    CLASSES.put("AbstractTask", AbstractTask.class);
    CLASSES.put("CompressFileTask", CompressFileTask.class);
    CLASSES.put("Condition", Condition.class);
    CLASSES.put("ConnectTo", ConnectTo.class);
    CLASSES.put("DatabaseConnectionTask", DatabaseConnectionTask.class);
    CLASSES.put("DatabaseDisconnectTask", DatabaseDisconnectTask.class);
    CLASSES.put("DatabaseQueryTask", DatabaseQueryTask.class);
    CLASSES.put("DirectoryTask", DirectoryTask.class);
    CLASSES.put("EmailTask", EmailTask.class);
    CLASSES.put("EndEvent", EndEvent.class);
    CLASSES.put("EventSubprocess", EventSubprocess.class);
    CLASSES.put("ExclusiveGateway", ExclusiveGateway.class);
    CLASSES.put("FileTask", FileTask.class);
    CLASSES.put("FtpTask", FtpTask.class);
    CLASSES.put("InclusiveGateway", InclusiveGateway.class);
    CLASSES.put("InputTask", InputTask.class);
    CLASSES.put("MoveToLastGateway", MoveToLastGateway.class);
    CLASSES.put("MoveToNode", MoveToNode.class);
    CLASSES.put("Node", Node.class);
    CLASSES.put("ParallelGateway", ParallelGateway.class);
    CLASSES.put("ProcessorTask", ProcessorTask.class);
    CLASSES.put("ReceiveTask", ReceiveTask.class);
    CLASSES.put("RequestTask", RequestTask.class);
    CLASSES.put("ScriptTask", ScriptTask.class);
    CLASSES.put("StartEvent", StartEvent.class);
    CLASSES.put("Subprocess", Subprocess.class);
  }}

  private static final Logger logger = LoggerFactory.getLogger(DeserializeAsNodeJsonResolver.class);

  @Override
  public String idFromValue(Object value) {
    return value.getClass().getSimpleName();
  }

  @Override
  public String idFromValueAndType(Object value, Class<?> suggestedType) {
    return idFromValue(value);
  }

  @Override
  public Id getMechanism() {
    return Id.SIMPLE_NAME;
  }

  @Override
  public JavaType typeFromId(DatabindContext context, String id) {
    if (CLASSES.containsKey(id)) {
      return context.getTypeFactory().constructType(CLASSES.get(id));
    }

    logger.debug("Unknown or Invalid Workflow Model class " + id + ".");
    return null;
  }

}
