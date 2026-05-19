package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import org.folio.rest.workflow.model.components.DelegateTask;
import org.folio.rest.workflow.model.has.HasDesignation;

@Entity
public class DatabaseDisconnectTask extends AbstractTask implements DelegateTask, HasDesignation {

  @Column(nullable = false)
  private String designation;

  public DatabaseDisconnectTask() {
    super();

    designation = "";
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (designation == null) {
      designation = "";
    }
  }

  @Override
  public String getDesignation() {
    return designation;
  }

  @Override
  public void setDesignation(String designation) {
    this.designation = designation;
  }

}
