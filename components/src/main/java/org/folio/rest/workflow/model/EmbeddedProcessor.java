package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.folio.rest.workflow.enums.ScriptType;
import org.folio.rest.workflow.model.has.common.HasEmbeddedProcessorCommon;

@Embeddable
public class EmbeddedProcessor implements HasEmbeddedProcessorCommon {

  @Getter
  @Setter
  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ScriptType scriptType;

  @Getter
  @Setter
  @NotNull
  @Size(min = 4, max = 128)
  @Column(nullable = false)
  private String functionName;

  @Getter
  @Setter
  @NotNull
  @Column(columnDefinition = "TEXT", nullable = false)
  private String code;

  @Getter
  @Setter
  @Column(nullable = false)
  private int buffer;

  @Getter
  @Setter
  @Column(nullable = false)
  private int delay;

  public EmbeddedProcessor() {
    super();

    buffer = 0;
    delay = 0;
  }

}
