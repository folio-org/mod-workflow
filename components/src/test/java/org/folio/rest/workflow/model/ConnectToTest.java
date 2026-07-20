package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
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

class ConnectToTest {

  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String NAME          = "name";
  private static final String NODEID        = "nodeId";

  private ConnectTo connectTo;

  @BeforeEach
  void beforeEach() {
    connectTo = new ConnectTo();
  }

  @Test
  void getIdWorksTest() {
    setField(connectTo, ID, VALUE);

    assertEquals(VALUE, connectTo.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(connectTo, ID, null);

    connectTo.setId(VALUE);
    assertEquals(VALUE, getField(connectTo, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(connectTo, NAME, VALUE);

    assertEquals(VALUE, connectTo.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(connectTo, NAME, null);

    connectTo.setName(VALUE);
    assertEquals(VALUE, getField(connectTo, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(connectTo, DESCRIPTION, VALUE);

    assertEquals(VALUE, connectTo.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(connectTo, DESCRIPTION, null);

    connectTo.setDescription(VALUE);
    assertEquals(VALUE, getField(connectTo, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(connectTo, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, connectTo.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(connectTo, DESERIALIZEAS, null);

    connectTo.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(connectTo, DESERIALIZEAS));
  }

  @Test
  void getNodeIdWorksTest() {
    setField(connectTo, NODEID, VALUE);

    assertEquals(VALUE, connectTo.getNodeId());
  }

  @Test
  void setNodeIdWorksTest() {
    setField(connectTo, NODEID, null);

    connectTo.setNodeId(VALUE);
    assertEquals(VALUE, getField(connectTo, NODEID));
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
   * @param nodeId The nodeId value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String nodeId) {
    final Map<String, Object> map = new HashMap<>();

    map.put(NODEID, nodeId);

    return map;
  }

}
