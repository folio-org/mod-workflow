package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConnectToTest {

  private ConnectTo connectTo;

  @BeforeEach
  void beforeEach() {
    connectTo = new ConnectTo();
  }

  @Test
  void getIdWorksTest() {
    setField(connectTo, "id", VALUE);

    assertEquals(VALUE, connectTo.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(connectTo, "id", null);

    connectTo.setId(VALUE);
    assertEquals(VALUE, getField(connectTo, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(connectTo, "name", VALUE);

    assertEquals(VALUE, connectTo.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(connectTo, "name", null);

    connectTo.setName(VALUE);
    assertEquals(VALUE, getField(connectTo, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(connectTo, "description", VALUE);

    assertEquals(VALUE, connectTo.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(connectTo, "description", null);

    connectTo.setDescription(VALUE);
    assertEquals(VALUE, getField(connectTo, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(connectTo, "deserializeAs", VALUE);

    assertEquals(VALUE, connectTo.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(connectTo, "deserializeAs", null);

    connectTo.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(connectTo, "deserializeAs"));
  }

  @Test
  void getNodeIdWorksTest() {
    setField(connectTo, "nodeId", VALUE);

    assertEquals(VALUE, connectTo.getNodeId());
  }

  @Test
  void setNodeIdWorksTest() {
    setField(connectTo, "nodeId", null);

    connectTo.setNodeId(VALUE);
    assertEquals(VALUE, getField(connectTo, "nodeId"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(connectTo, attribute, value);
    });

    connectTo.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(connectTo, attribute));
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

    return Stream.of(
      Arguments.of(
        helperFieldMap(NULL_STR),
        helperFieldMap("")
      ),
      Arguments.of(
        helperFieldMap(VALUE),
        helperFieldMap(VALUE)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param nodeId The nodeId value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String nodeId) {
    final Map<String, Object> map = new HashMap<>();

    map.put("nodeId", nodeId);

    return map;
  }

}
