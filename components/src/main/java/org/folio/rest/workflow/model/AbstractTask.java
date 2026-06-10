package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.util.HashSet;
import java.util.Set;
import org.folio.rest.workflow.model.components.Task;
import org.folio.rest.workflow.model.has.HasInputOutput;
import org.hibernate.annotations.ColumnDefault;

/**
 * Provides a superclass for any Node implementing a DelegateTask.
 *
 * This is intended to reduce repetition of getters and setters needed by the Task.
 */
@MappedSuperclass
public abstract class AbstractTask extends Node implements HasInputOutput, Task {

  @Column(nullable = false)
  @ColumnDefault("false")
  private Boolean asyncAfter;

  @Column(nullable = false)
  @ColumnDefault("false")
  private Boolean asyncBefore;

  @ElementCollection
  private Set<EmbeddedVariable> inputVariables;

  @Column(columnDefinition = "TEXT", nullable = true)
  private EmbeddedVariable outputVariable;

  AbstractTask() {
    super();

    asyncAfter = false;
    asyncBefore = false;
    inputVariables = new HashSet<>();
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (asyncAfter == null) {
      asyncAfter = false;
    }

    if (asyncBefore == null) {
      asyncBefore = false;
    }

    if (inputVariables == null) {
      inputVariables = new HashSet<>();
    } else {
      // @Embeddable with @PrePersist do not consistently call PrePersist and so this must be manually triggered.
      inputVariables.forEach((EmbeddedVariable ev) -> {
        if (ev != null) ev.prePersist();
      });
    }

    // @Embeddable with @PrePersist do not consistently call PrePersist and so this must be manually triggered.
    if (outputVariable != null) {
      outputVariable.prePersist();
    }
  }

  @Override
  public Boolean getAsyncAfter() {
    return asyncAfter;
  }

  @Override
  public Boolean getAsyncBefore() {
    return asyncBefore;
  }

  @Override
  public void setAsyncAfter(Boolean asyncAfter) {
    this.asyncAfter = asyncAfter;
  }

  @Override
  public void setAsyncBefore(Boolean asyncBefore) {
    this.asyncBefore = asyncBefore;
  }

  @Override
  public Set<EmbeddedVariable> getInputVariables() {
    return inputVariables;
  }

  @Override
  public EmbeddedVariable getOutputVariable() {
    return outputVariable;
  }

  @Override
  public void setInputVariables(Set<EmbeddedVariable> inputVariables) {
    this.inputVariables = inputVariables;
  }

  @Override
  public void setOutputVariable(EmbeddedVariable outputVariable) {
    this.outputVariable = outputVariable;
  }

}
