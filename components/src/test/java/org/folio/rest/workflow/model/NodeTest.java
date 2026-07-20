package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NodeTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String NAME          = "name";

  private Node node;

  @BeforeEach
  void beforeEach() {
    node = new Impl();
  }

  @Test
  void getIdentifierWorksTest() {
    final String className = node.getClass().getSimpleName().toLowerCase();
    final String expect = "my_identifier";

    setField(node, ID, "my-identifier");

    final String identifier = node.getIdentifier();

    assertThat(identifier, containsString(expect));
    assertThat(identifier, containsString(className));
  }

  @Test
  void getIdWorksTest() {
    setField(node, ID, VALUE);

    assertEquals(VALUE, node.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(node, ID, null);

    node.setId(VALUE);
    assertEquals(VALUE, getField(node, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(node, NAME, VALUE);

    assertEquals(VALUE, node.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(node, NAME, null);

    node.setName(VALUE);
    assertEquals(VALUE, getField(node, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(node, DESCRIPTION, VALUE);

    assertEquals(VALUE, node.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(node, DESCRIPTION, null);

    node.setDescription(VALUE);
    assertEquals(VALUE, getField(node, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(node, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, node.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(node, DESERIALIZEAS, null);

    node.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(node, DESERIALIZEAS));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(node, attribute, value);
    });

    node.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(node, attribute));
    });
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect The expected values.
   */
  private static Stream<Arguments> providePrePersistFor() {

    return List.of(
      Arguments.of(
        helperFieldMap(NULL_STR),
        helperFieldMap("")
      ),
      Arguments.of(
        helperFieldMap(VALUE),
        helperFieldMap(VALUE)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param name The name value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String name) {

    final Map<String, Object> map = new HashMap<>();

    map.put(NAME, name);

    return map;
  }

  private static class Impl extends Node { }

}
