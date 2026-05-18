package org.folio.rest.workflow.model.has;

import org.folio.rest.workflow.enums.SubprocessType;

/**
 * This interface provides methods associated with {@link org.folio.rest.workflow.enums.SubprocessType SubprocessType}.
 */
public interface HasSubProcessType {

  public SubprocessType getType();

  public void setType(SubprocessType type);
}
