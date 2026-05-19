package org.folio.rest.workflow.model.has;

import org.folio.rest.workflow.enums.StartEventType;

/**
 * This interface provides methods associated with {@link org.folio.rest.workflow.enums.StartEventType StartEventType}.
 */
public interface HasStartEventType {

  public StartEventType getType();

  public void setType(StartEventType type);
}
