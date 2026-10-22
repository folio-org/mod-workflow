package org.folio.rest.workflow.dto;

import org.folio.rest.workflow.model.has.HasTenantId;
import org.folio.rest.workflow.model.has.HasDeploymentIds;
import org.folio.rest.workflow.model.has.HasVersionTag;

/**
 * This is a DTO designed for operational purposes.
 *
 * Examples are starting a {@link org.folio.rest.workflow.model.Workflow Workflow} or getting the {@link org.folio.rest.workflow.model.Workflow Workflow} history.
 */
public interface WorkflowOperationalDto extends HasTenantId, HasDeploymentIds, HasVersionTag {

}
