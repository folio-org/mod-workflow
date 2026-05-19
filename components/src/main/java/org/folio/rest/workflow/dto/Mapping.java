package org.folio.rest.workflow.dto;

import jakarta.validation.constraints.NotNull;

public class Mapping {

  @NotNull
  private String toProperty;

  @NotNull
  private String fromProperty;

  @NotNull
  private boolean multiple;

  public Mapping() {
    super();

    multiple = false;
  }

  /**
   * @return the toProperty
   */
  public String getToProperty() {
    return toProperty;
  }

  /**
   * @return the fromProperty
   */
  public String getFromProperty() {
    return fromProperty;
  }

  /**
   * @return the multiple
   */
  public boolean isMultiple() {
    return multiple;
  }

  /**
   * @param toProperty the toProperty to set
   */
  public void setToProperty(String toProperty) {
    this.toProperty = toProperty;
  }

  /**
   * @param fromProperty the fromProperty to set
   */
  public void setFromProperty(String fromProperty) {
    this.fromProperty = fromProperty;
  }

  /**
   * @param multiple the multiple to set
   */
  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }

}
