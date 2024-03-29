package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.folio.rest.workflow.enums.HttpMethod;
import org.folio.rest.workflow.model.has.HasId;
import org.folio.rest.workflow.model.has.HasInformational;
import org.folio.rest.workflow.model.has.HasMethod;
import org.folio.rest.workflow.model.has.HasName;
import org.folio.rest.workflow.model.has.HasPathPattern;
import org.folio.spring.domain.model.AbstractBaseEntity;

@Entity
public class Trigger extends AbstractBaseEntity implements HasId, HasInformational, HasMethod, HasName, HasPathPattern {

  @Getter
  @Setter
  @NotNull
  @Size(min = 4, max = 64)
  @Column(unique = true)
  private String name;

  @Getter
  @Setter
  @Size(min = 0, max = 256)
  @Column(nullable = true)
  private String description;

  @Getter
  @Setter
  @NotNull
  @Size(min = 2, max = 256)
  @Column(nullable = false)
  private String pathPattern;

  @Getter
  @Setter
  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private HttpMethod method;

  public Trigger() {
    super();
  }

}
