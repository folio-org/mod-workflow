package org.folio.rest.workflow.model.converter;

import jakarta.persistence.Converter;
import org.folio.rest.workflow.model.EmbeddedVariable;

/**
 * Store an EmbeddedVariable in the database as JSON.
 */
@Converter
public class EmbeddedVariableConverter extends AbstractConverter<EmbeddedVariable> {

}
