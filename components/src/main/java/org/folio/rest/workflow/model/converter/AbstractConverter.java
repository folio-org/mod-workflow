package org.folio.rest.workflow.model.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.AttributeConverter;
import org.folio.rest.workflow.exception.AbstractConverterFailure;
import tools.jackson.core.JacksonException;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * This converts the value into a JSON representation stored as a single string in the database.
 *
 * Implementations need only provide the T.
 * Each implementation is expected to be used as-is as a JSON string value without needing the type details.
 */
public abstract class AbstractConverter<T> implements AttributeConverter<T, String> {

  private JsonMapper mapper = JsonMapper
    .builderWithJackson2Defaults()
    .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES, true)
    .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    .configure(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION, true)
    .changeDefaultPropertyInclusion(incl -> incl
      .withValueInclusion(JsonInclude.Include.NON_NULL)
      .withContentInclusion(JsonInclude.Include.NON_NULL)
    )
    .findAndAddModules()
    .build();

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (attribute == null) return null;

    try {
      return mapper.writeValueAsString(attribute);
    } catch (JacksonException e) {
      throw new AbstractConverterFailure(e.getMessage(), e);
    }
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;

    try {
      return mapper.readValue(dbData, getTypeReference());
    } catch (JacksonException e) {
      throw new AbstractConverterFailure(e.getMessage(), e);
    }
  }

  public TypeReference<T> getTypeReference() {
    return new TypeReference<T>() {};
  }

}
