package org.folio.rest.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.folio.rest.workflow.model.converter.JsonNodeConverter;
import org.folio.rest.workflow.model.has.HasId;
import org.folio.rest.workflow.model.has.HasInformational;
import org.folio.rest.workflow.model.has.HasName;
import org.folio.rest.workflow.model.has.common.HasNodeCommon;
import org.folio.spring.domain.model.AbstractBaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Node extends AbstractBaseEntity implements HasId, HasInformational, HasName, HasNodeCommon, NodeInterface {

  @Getter
  @Setter
  @NotNull
  @Size(min = 3, max = 64)
  @Column(nullable = false)
  private String name;

  @Getter
  @Setter
  @Size(min = 0, max = 512)
  @Column(nullable = true)
  private String description;

  @Getter
  @Setter
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @Convert(converter = JsonNodeConverter.class, attributeName = "deserializeAs")
  private String deserializeAs = this.getClass().getSimpleName();

  public Node() {
    super();

    name = "";
  }

  @PrePersist
  public void prePersist() {
    if (name == null) {
      name = "";
    }
  }

  public String getIdentifier() {
    String regex = "([a-z])([A-Z]+)";
    String replacement = "$1_$2";
    String name = getClass().getSimpleName();
    return String.format("%s_%s", name.replaceAll(regex, replacement).toLowerCase(), getId().replace("-", "_"));
  }

}
