package org.folio.rest.workflow.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.folio.rest.workflow.enums.HttpMethod;

public class Handler {

  @Getter
  @Setter
  private List<String> methods;

  @Getter
  @Setter
  private String pathPattern;

  @Getter
  @Setter
  private List<String> permissionsRequired;

  @Getter
  @Setter
  private List<String> permissionsDesired;

  public Handler() {
    super();
  }

  public List<Action> getActionByInterface(String interfaceName) {
    List<Action> actions = new ArrayList<>();
    methods.forEach(method -> actions.add(new Action(interfaceName, pathPattern, HttpMethod.valueOf(method))));
    return actions;
  }

}
