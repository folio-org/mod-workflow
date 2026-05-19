package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import java.util.ArrayList;
import java.util.List;
import org.folio.rest.workflow.model.has.HasAsync;
import org.folio.rest.workflow.model.has.HasNodes;
import org.hibernate.annotations.ColumnDefault;

/**
 * Provides a superclass for any Node implementing a process, such as Subprocess.
 *
 * This is intended to reduce repitition of getters and setters needed by the DelegateTask.
 */
@MappedSuperclass
public abstract class AbstractProcess extends Node implements HasAsync, HasNodes {

  @Column(nullable = false)
  @ColumnDefault("false")
  private Boolean asyncAfter;

  @Column(nullable = false)
  @ColumnDefault("false")
  private Boolean asyncBefore;

  @OneToMany
  @OrderColumn
  private List<Node> nodes;

  AbstractProcess() {
    super();

    asyncAfter = false;
    asyncBefore = false;
    nodes = new ArrayList<>();
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

    if (nodes == null) {
      nodes = new ArrayList<>();
    }
  }

  @Override
  public List<Node> getNodes() {
    return nodes;
  }

  @Override
  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
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

}
