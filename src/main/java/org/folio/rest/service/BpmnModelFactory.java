package org.folio.rest.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BoundaryEvent;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.Definitions;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.Message;
import org.camunda.bpm.model.bpmn.instance.MessageEventDefinition;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.SubProcess;
import org.camunda.bpm.model.bpmn.instance.TerminateEventDefinition;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnDiagram;
import org.camunda.bpm.model.bpmn.instance.bpmndi.BpmnPlane;
import org.folio.rest.model.Task;
import org.folio.rest.model.Workflow;
import org.springframework.stereotype.Service;

@Service
public class BpmnModelFactory {

  private static final String END_EVENT_ID = "ee_0";
  private static final String START_EVENT_ID = "se_0";
  private static final String TARGET_NAMESPACE = "http://bpmn.io/schema/bpmn";

  public BpmnModelInstance makeBPMNFromWorkflow(Workflow workflow) {

    BpmnModelInstance modelInstance = Bpmn.createEmptyModel();

    // Setup Definitions
    Definitions definitions = modelInstance.newInstance(Definitions.class);
    definitions.setTargetNamespace(TARGET_NAMESPACE);
    modelInstance.setDefinitions(definitions);

    // Setup Process
    Process process = createElement(modelInstance, definitions, workflow.getName().replaceAll(" ", "_").toLowerCase(), Process.class);
    process.setExecutable(true);
    process.setName(workflow.getName());

    // Setup Nodes

    StartEvent processStartEvent = createElement(modelInstance, process, START_EVENT_ID, StartEvent.class);
    processStartEvent.setName("StartProcess");

    AtomicInteger taskIndex = new AtomicInteger();
    List<ServiceTask> serviceTasks = workflow.getTasks().stream().map(task -> {
      int index = taskIndex.getAndIncrement();
      ServiceTask serviceTask = createElement(modelInstance, process, String.format("t_%s", index), ServiceTask.class);
      // String delegate = index == 0 ? "${testStreamDelegate}" : index < workflow.getTasks().size() - 1 ? "${testProcessDelegate}" : "${testAccumulatorDelegate}";
      return enhanceServiceTask(serviceTask, task);
    }).collect(Collectors.toList());

    EndEvent processEndEvent = createElement(modelInstance, process, END_EVENT_ID, EndEvent.class);
    processEndEvent.setName("EndProcess");
    createElement(modelInstance, processEndEvent, null, TerminateEventDefinition.class);

    SequenceFlow firstSf = createElement(modelInstance, process, String.format("%s-%s", START_EVENT_ID, "t_1"), SequenceFlow.class);
    firstSf.setSource(processStartEvent);
    firstSf.setTarget(serviceTasks.get(0));

    // Setup Connections
    AtomicInteger sfIndex = new AtomicInteger();
    serviceTasks.forEach(st->{
      int currentIndex = sfIndex.getAndIncrement();
      if (currentIndex != serviceTasks.size() - 1) {
        SequenceFlow currentSf = createElement(modelInstance, process, String.format("t_%s-t_%s", currentIndex, currentIndex+1), SequenceFlow.class);
        currentSf.setTarget(serviceTasks.get(currentIndex + 1));
        currentSf.setSource(st);
      }
    });

    SequenceFlow lastSf = createElement(modelInstance, process, String.format("t_%s-%s", serviceTasks.size(), END_EVENT_ID), SequenceFlow.class);
    lastSf.setSource(serviceTasks.get(serviceTasks.size() - 1));
    lastSf.setTarget(processEndEvent);

    Message processStartMessage = createElement(modelInstance, definitions, "process-start-message", Message.class);
    processStartMessage.setName(workflow.getStartTrigger().getPathPattern());

    MessageEventDefinition processStartEventMessageDefinition = createElement(modelInstance, processStartEvent, "process-start-event-message-definition", MessageEventDefinition.class);
    processStartEventMessageDefinition.setMessage(processStartMessage);

    // Stub Empty Diagram
    BpmnDiagram diagramElement = createElement(modelInstance, definitions, String.format("%s-diagram", workflow.getName().replaceAll(" ", "_").toLowerCase()), BpmnDiagram.class);

    BpmnPlane plane = createElement(modelInstance, diagramElement, String.format("%s-plane", workflow.getName().replaceAll(" ", "_").toLowerCase()), BpmnPlane.class);
    plane.setBpmnElement(process);

    return modelInstance;
  }

  // @formatter:off
  protected <T extends BpmnModelElementInstance> T createElement(
    BpmnModelInstance modelInstance,
    BpmnModelElementInstance parentElement,
    String id,
    Class<T> elementClass
  ) {
  // @formatter:on
    T element = modelInstance.newInstance(elementClass);
    if(id != null) element.setAttributeValue("id", id, true);
    parentElement.addChildElement(element);
    return element;
  }

  public Message createMessage(BpmnModelInstance modelInstance, Process process, String id, String textContent) {
    Message message = createElement(modelInstance, process, id, Message.class);
    message.setTextContent(textContent);
    return message;
  }

  public BoundaryEvent createBoundaryEvent(BpmnModelInstance modelInstance, Process process, String id, String textContent) {
    BoundaryEvent boundaryEvent = createElement(modelInstance, process, id, BoundaryEvent.class);
    return boundaryEvent;
  }

  public SequenceFlow createSequenceFlow(BpmnModelInstance modelInstance, SubProcess subProcess, FlowNode from, FlowNode to) {
    String id = from.getId() + "-" + to.getId();
    SequenceFlow sequenceFlow = createElement(modelInstance, subProcess, id, SequenceFlow.class);
    subProcess.addChildElement(sequenceFlow);
    sequenceFlow.setSource(from);
    from.getOutgoing().add(sequenceFlow);
    sequenceFlow.setTarget(to);
    to.getIncoming().add(sequenceFlow);
    return sequenceFlow;
  }

  private ServiceTask enhanceServiceTask(ServiceTask serviceTask, Task task) {
    serviceTask.setName(task.getName());
    serviceTask.setCamundaDelegateExpression(task.getDelegate());
    return serviceTask;
  }

}
