package org.folio.rest.workflow.model.converter;

import static org.folio.spring.test.mock.MockMvcConstant.STRING_LIST;
import static org.folio.spring.test.mock.MockMvcConstant.STRING_LIST_AS_JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;

class AbstractConverterTest {

  private Impl abstractConverter;

  @BeforeEach
  void beforeEach() {
    abstractConverter = new Impl();
  }

  @Test
  void convertToDatabaseColumnWorksTest() {
    assertEquals(STRING_LIST_AS_JSON, abstractConverter.convertToDatabaseColumn(STRING_LIST));
  }

  @Test
  void convertToEntityAttributeWorksTest() {
    assertEquals(STRING_LIST, abstractConverter.convertToEntityAttribute(STRING_LIST_AS_JSON));
  }

  @Test
  void getTypeReferenceWorksTest() {
    assertNotNull(abstractConverter.getTypeReference());
  }

  private static class Impl extends AbstractConverter<List<String>> {

    @Override
    public TypeReference<List<String>> getTypeReference() {
      return new TypeReference<List<String>>() {};
    }
  };

}
