package org.folio.rest.workflow.model;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;

@Entity
public class EventSubprocess extends AbstractProcess {

  public EventSubprocess() {
    super();
  }

}
