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
 * This is intended to reduce repetition of getters and setters needed by the DelegateTask.
 */
@MappedSuperclass
public abstract class AbstractProcess extends Node implements HasAsync, HasNodes {

  /**
   * The default asyncAfter value.
   */
  static final boolean DEFAULT_ASYNC_AFTER = false;

  /**
   * The default asyncBefore value.
   */
  static final boolean DEFAULT_ASYNC_BEFORE = false;

  @Column(nullable = false)
  @ColumnDefault("" + DEFAULT_ASYNC_AFTER)
  private Boolean asyncAfter;

  @Column(nullable = false)
  @ColumnDefault("" + DEFAULT_ASYNC_BEFORE)
  private Boolean asyncBefore;

  @OneToMany
  @OrderColumn
  private List<Node> nodes;

  AbstractProcess() {
    super();

    asyncAfter = DEFAULT_ASYNC_AFTER;
    asyncBefore = DEFAULT_ASYNC_BEFORE;
    nodes = new ArrayList<>();
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (asyncAfter == null) {
      asyncAfter = DEFAULT_ASYNC_AFTER;
    }

    if (asyncBefore == null) {
      asyncBefore = DEFAULT_ASYNC_BEFORE;
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
