package org.folio.rest.workflow.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.folio.rest.workflow.enums.ScriptType;
import org.folio.rest.workflow.model.has.common.HasEmbeddedProcessorCommon;

@Embeddable
public class EmbeddedProcessor implements HasEmbeddedProcessorCommon {

  @Column(nullable = false)
  private Integer buffer;

  @NotNull
  @Column(columnDefinition = "TEXT", nullable = false)
  private String code;

  @Column(nullable = false)
  private Integer delay;

  @NotNull
  @Size(min = 4, max = 128)
  @Column(nullable = false)
  private String functionName;

  @NotNull
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private ScriptType scriptType;

  public EmbeddedProcessor() {
    super();

    buffer = 0;
    code = "";
    delay = 0;
    functionName = "";
  }

  @PrePersist
  public void prePersist() {
    if (buffer == null) {
      buffer = 0;
    }

    if (code == null) {
      code = "";
    }

    if (delay == null) {
      delay = 0;
    }

    if (functionName == null) {
      functionName = "";
    }
  }

  @Override
  public Integer getBuffer() {
    return buffer;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public Integer getDelay() {
    return delay;
  }

  @Override
  public String getFunctionName() {
    return functionName;
  }

  @Override
  public ScriptType getScriptType() {
    return scriptType;
  }

  @Override
  public void setBuffer(Integer buffer) {
    this.buffer = buffer;
  }

  @Override
  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public void setDelay(Integer delay) {
    this.delay = delay;
  }

  @Override
  public void setFunctionName(String functionName) {
    this.functionName = functionName;
  }

  @Override
  public void setScriptType(ScriptType scriptType) {
    this.scriptType = scriptType;
  }

}
