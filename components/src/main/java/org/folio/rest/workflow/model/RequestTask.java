package org.folio.rest.workflow.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import java.util.HashSet;
import java.util.Set;
import org.folio.rest.workflow.model.components.DelegateTask;
import org.folio.rest.workflow.model.has.common.HasRequestTaskCommon;

@Entity
public class RequestTask extends AbstractTask implements DelegateTask, HasRequestTaskCommon {

  @ElementCollection
  private Set<EmbeddedVariable> headerOutputVariables;

  @Embedded
  private EmbeddedRequest request;

  public RequestTask() {
    super();

    headerOutputVariables = new HashSet<>();
  }

  @Override
  public Set<EmbeddedVariable> getHeaderOutputVariables() {
    return headerOutputVariables;
  }

  @Override
  public EmbeddedRequest getRequest() {
    return request;
  }

  @Override
  public void setHeaderOutputVariables(Set<EmbeddedVariable> headerOutputVariables) {
    this.headerOutputVariables = headerOutputVariables;
  }

  @Override
  public void setRequest(EmbeddedRequest request) {
    this.request = request;
  }

}
