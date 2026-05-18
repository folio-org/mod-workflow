package org.folio.rest.workflow.model.converter;

import jakarta.persistence.Converter;
import java.util.List;

/**
 * Store a list of strings in the database as JSON.
 */
@Converter
public class StringListConverter extends AbstractConverter<List<String>> {

}
