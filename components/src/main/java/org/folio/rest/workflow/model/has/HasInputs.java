package org.folio.rest.workflow.model.has;

import java.util.Set;
import org.folio.rest.workflow.model.EmbeddedInput;

/**
 * This interface provides the Inputs methods.
 */
public interface HasInputs {

  public Set<EmbeddedInput> getInputs();

  public void setInputs(Set<EmbeddedInput> inputs);
}
