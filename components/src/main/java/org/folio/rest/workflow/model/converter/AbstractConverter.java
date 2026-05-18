package org.folio.rest.workflow.model.converter;

import jakarta.persistence.AttributeConverter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * This converts the value into a JSON representation stored as a single string in the database.
 *
 * Implementations need only provide the T.
 * Each implementation is expected to be used as-is as a JSON string value without needing the type details.
 */
public abstract class AbstractConverter<T> implements AttributeConverter<T, String> {

  private ObjectMapper objectMapper = JsonMapper.builder()
    .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
    .disable(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES)
    .disable(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY)
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    .build();

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (attribute == null) return null;

    try {
      return objectMapper.writeValueAsString(attribute);
    } catch (JacksonException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (dbData == null) return null;

    try {
      return objectMapper.readValue(dbData, getTypeReference());
    } catch (JacksonException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  public TypeReference<T> getTypeReference() {
    return new TypeReference<T>() {};
  }

}
