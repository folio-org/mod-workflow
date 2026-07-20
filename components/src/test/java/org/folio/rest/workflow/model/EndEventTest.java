package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndEventTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String NAME          = "name";

  private EndEvent endEvent;

  @BeforeEach
  void beforeEach() {
    endEvent = new Impl();
  }

  @Test
  void getIdWorksTest() {
    setField(endEvent, ID, VALUE);

    assertEquals(VALUE, endEvent.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(endEvent, ID, null);

    endEvent.setId(VALUE);
    assertEquals(VALUE, getField(endEvent, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(endEvent, NAME, VALUE);

    assertEquals(VALUE, endEvent.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(endEvent, NAME, null);

    endEvent.setName(VALUE);
    assertEquals(VALUE, getField(endEvent, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(endEvent, DESCRIPTION, VALUE);

    assertEquals(VALUE, endEvent.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(endEvent, DESCRIPTION, null);

    endEvent.setDescription(VALUE);
    assertEquals(VALUE, getField(endEvent, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(endEvent, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, endEvent.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(endEvent, DESERIALIZEAS, null);

    endEvent.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(endEvent, DESERIALIZEAS));
  }

  private static class Impl extends EndEvent { };

}
