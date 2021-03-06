package org.folio.rest.workflow.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class EventSubprocess extends Node {

  @OneToMany
  @OrderColumn
  private List<Node> nodes;

  @Column(nullable = false)
  private boolean asyncBefore;

  @Column(nullable = false)
  private boolean asyncAfter;

  public EventSubprocess() {
    super();
    nodes = new ArrayList<Node>();
  }

  public List<Node> getNodes() {
    return nodes;
  }

  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

}
