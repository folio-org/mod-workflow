package org.folio.rest.workflow.model.converter;

import jakarta.persistence.Converter;
import java.util.List;
import org.folio.rest.workflow.dto.Mapping;

/**
 * Mapping list converter.
 */
@Converter
public class MappingListConverter extends AbstractConverter<List<Mapping>> {

}
