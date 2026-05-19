package org.folio.rest.workflow.dto;

import jakarta.validation.constraints.NotNull;

public class Comparison {

  @NotNull
  private String sourceProperty;

  @NotNull
  private String targetProperty;

  public Comparison() {
    super();
  }

  /**
   * @return the sourceProperty
   */
  public String getSourceProperty() {
    return sourceProperty;
  }

  /**
   * @return the targetProperty
   */
  public String getTargetProperty() {
    return targetProperty;
  }

  /**
   * @param sourceProperty the sourceProperty to set
   */
  public void setSourceProperty(String sourceProperty) {
    this.sourceProperty = sourceProperty;
  }

  /**
   * @param targetProperty the targetProperty to set
   */
  public void setTargetProperty(String targetProperty) {
    this.targetProperty = targetProperty;
  }

}
