package org.folio.rest.workflow.model.converter;

import jakarta.persistence.Converter;
import java.util.List;
import org.folio.rest.workflow.dto.Comparison;

/**
 * Comparison list converter.
 */
@Converter
public class ComparisonListConverter extends AbstractConverter<List<Comparison>> {

}
