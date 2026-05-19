package org.folio.rest.workflow.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import org.folio.rest.workflow.model.components.DelegateTask;
import org.folio.rest.workflow.model.has.common.HasProcessorTaskCommon;

@Entity
public class ProcessorTask extends AbstractTask implements DelegateTask, HasProcessorTaskCommon {

  @Embedded
  private EmbeddedProcessor processor;

  public ProcessorTask() {
    super();
  }

  @Override
  public EmbeddedProcessor getProcessor() {
    return processor;
  }

  @Override
  public void setProcessor(EmbeddedProcessor processor) {
    this.processor = processor;
  }

}
