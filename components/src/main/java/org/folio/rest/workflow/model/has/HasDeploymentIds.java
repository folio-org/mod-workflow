package org.folio.rest.workflow.model.has;

import java.util.List;

/**
 * This interface provides the DeploymentId methods.
 */
public interface HasDeploymentIds {

  public List<String> getDeploymentIds();

  public void setDeploymentIds(List<String> deploymentIds);
}
