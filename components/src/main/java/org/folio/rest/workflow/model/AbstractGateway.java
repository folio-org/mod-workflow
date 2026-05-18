package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import java.util.ArrayList;
import java.util.List;
import org.folio.rest.workflow.enums.Direction;
import org.folio.rest.workflow.model.components.Gateway;
import org.folio.rest.workflow.model.has.HasNodes;

@MappedSuperclass
public abstract class AbstractGateway extends Node implements Gateway, HasNodes {

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Direction direction;

  @OneToMany
  @OrderColumn
  private List<Node> nodes;

  AbstractGateway() {
    super();

    direction = Direction.UNSPECIFIED;
    nodes = new ArrayList<>();
  }

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (direction == null) {
      direction = Direction.UNSPECIFIED;
    }

    if (nodes == null) {
      nodes = new ArrayList<>();
    }
  }

  @Override
  public Direction getDirection() {
    return direction;
  }

  @Override
  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  @Override
  public List<Node> getNodes() {
    return nodes;
  }

  @Override
  public void setNodes(List<Node> nodes) {
    this.nodes = nodes;
  }

}
