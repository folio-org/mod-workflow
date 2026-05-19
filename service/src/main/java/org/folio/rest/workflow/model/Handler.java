package org.folio.rest.workflow.model;

import java.util.ArrayList;
import java.util.List;
import org.folio.rest.workflow.enums.HttpMethod;

public class Handler {

  private List<String> methods;

  private String pathPattern;

  private List<String> permissionsRequired;

  private List<String> permissionsDesired;

  public Handler() {
    super();
  }

  public List<Action> getActionByInterface(String interfaceName) {
    List<Action> actions = new ArrayList<>();
    methods.forEach(method -> actions.add(new Action(interfaceName, pathPattern, HttpMethod.valueOf(method))));
    return actions;
  }

  /**
   * @return the methods
   */
  public List<String> getMethods() {
    return methods;
  }

  /**
   * @return the pathPattern
   */
  public String getPathPattern() {
    return pathPattern;
  }

  /**
   * @return the permissionsRequired
   */
  public List<String> getPermissionsRequired() {
    return permissionsRequired;
  }

  /**
   * @return the permissionsDesired
   */
  public List<String> getPermissionsDesired() {
    return permissionsDesired;
  }

  /**
   * @param methods the methods to set
   */
  public void setMethods(List<String> methods) {
    this.methods = methods;
  }

  /**
   * @param pathPattern the pathPattern to set
   */
  public void setPathPattern(String pathPattern) {
    this.pathPattern = pathPattern;
  }

  /**
   * @param permissionsRequired the permissionsRequired to set
   */
  public void setPermissionsRequired(List<String> permissionsRequired) {
    this.permissionsRequired = permissionsRequired;
  }

  /**
   * @param permissionsDesired the permissionsDesired to set
   */
  public void setPermissionsDesired(List<String> permissionsDesired) {
    this.permissionsDesired = permissionsDesired;
  }

}
