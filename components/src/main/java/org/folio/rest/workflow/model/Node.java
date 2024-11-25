package org.folio.rest.workflow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
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
import org.folio.rest.workflow.model.resolver.DeserializeAsJsonResolver;
import org.folio.spring.domain.model.AbstractBaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/*
@JsonTypeIdResolver(DeserializeAsJsonResolver.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "deserializeAs")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CompressFileTask.class, name = "CompressFileTask"),
  @JsonSubTypes.Type(value = Condition.class, name = "Condition"),
  @JsonSubTypes.Type(value = ConnectTo.class, name = "ConnectTo"),
  @JsonSubTypes.Type(value = DatabaseConnectionTask.class, name = "DatabaseConnectionTask"),
  @JsonSubTypes.Type(value = DatabaseDisconnectTask.class, name = "DatabaseDisconnectTask"),
  @JsonSubTypes.Type(value = DatabaseQueryTask.class, name = "DatabaseQueryTask"),
  @JsonSubTypes.Type(value = DirectoryTask.class, name = "DirectoryTask"),
  @JsonSubTypes.Type(value = EmailTask.class, name = "EmailTask"),
  @JsonSubTypes.Type(value = EndEvent.class, name = "EndEvent"),
  @JsonSubTypes.Type(value = EventSubprocess.class, name = "EventSubprocess"),
  @JsonSubTypes.Type(value = ExclusiveGateway.class, name = "ExclusiveGateway"),
  @JsonSubTypes.Type(value = FileTask.class, name = "FileTask"),
  @JsonSubTypes.Type(value = FtpTask.class, name = "FtpTask"),
  @JsonSubTypes.Type(value = InclusiveGateway.class, name = "InclusiveGateway"),
  @JsonSubTypes.Type(value = InputTask.class, name = "InputTask"),
  @JsonSubTypes.Type(value = MoveToLastGateway.class, name = "MoveToLastGateway"),
  @JsonSubTypes.Type(value = MoveToNode.class, name = "MoveToNode"),
  @JsonSubTypes.Type(value = ParallelGateway.class, name = "ParallelGateway"),
  @JsonSubTypes.Type(value = ProcessorTask.class, name = "ProcessorTask"),
  @JsonSubTypes.Type(value = ReceiveTask.class, name = "ReceiveTask"),
  @JsonSubTypes.Type(value = RequestTask.class, name = "RequestTask"),
  @JsonSubTypes.Type(value = ScriptTask.class, name = "ScriptTask"),
  @JsonSubTypes.Type(value = StartEvent.class, name = "StartEvent"),
  @JsonSubTypes.Type(value = Subprocess.class, name = "Subprocess")
})*/
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
    System.out.print("\n\n\nDEBUG: getIdentifier called, response = " + String.format("%s_%s", name.replaceAll(regex, replacement).toLowerCase(), getId().replace("-", "_")) + "\n\n\n");
    return String.format("%s_%s", name.replaceAll(regex, replacement).toLowerCase(), getId().replace("-", "_"));
  }

}
