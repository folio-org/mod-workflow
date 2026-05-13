package org.folio.rest.workflow.model.converter;

import jakarta.persistence.Converter;
import tools.jackson.databind.JsonNode;

/**
 * JSON Node converted.
 */
@Converter
public class JsonNodeConverter extends AbstractConverter<JsonNode> {

}
