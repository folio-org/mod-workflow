package org.folio.rest.workflow.model;

import static org.folio.rest.workflow.enums.SubprocessType.EMBEDDED;
import static org.folio.rest.workflow.enums.SubprocessType.TRANSACTION;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.folio.rest.workflow.enums.SubprocessType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubprocessTest {

  private static final String ASYNCAFTER    = "asyncAfter";
  private static final String ASYNCBEFORE   = "asyncBefore";
  private static final String DESCRIPTION   = "description";
  private static final String DESERIALIZEAS = "deserializeAs";
  private static final String ID            = "id";
  private static final String LOOPREF       = "loopRef";
  private static final String NAME          = "name";
  private static final String NODES         = "nodes";
  private static final String TYPE          = "type";

  @Mock
  private EmbeddedLoopReference embeddedLoopReference;

  @Mock
  private Node node;

  private List<Node> nodes;

  private Subprocess subprocess;

  @BeforeEach
  void beforeEach() {
    subprocess = new Subprocess();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(subprocess, ID, VALUE);

    assertEquals(VALUE, subprocess.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(subprocess, ID, null);

    subprocess.setId(VALUE);
    assertEquals(VALUE, getField(subprocess, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(subprocess, NAME, VALUE);

    assertEquals(VALUE, subprocess.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(subprocess, NAME, null);

    subprocess.setName(VALUE);
    assertEquals(VALUE, getField(subprocess, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(subprocess, DESCRIPTION, VALUE);

    assertEquals(VALUE, subprocess.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(subprocess, DESCRIPTION, null);

    subprocess.setDescription(VALUE);
    assertEquals(VALUE, getField(subprocess, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(subprocess, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, subprocess.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(subprocess, DESERIALIZEAS, null);

    subprocess.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(subprocess, DESERIALIZEAS));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(subprocess, ASYNCBEFORE, true);

    assertEquals(true, subprocess.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(subprocess, ASYNCBEFORE, false);

    subprocess.setAsyncBefore(true);
    assertEquals(true, getField(subprocess, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(subprocess, ASYNCAFTER, true);

    assertEquals(true, subprocess.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(subprocess, ASYNCAFTER, false);

    subprocess.setAsyncAfter(true);
    assertEquals(true, getField(subprocess, ASYNCAFTER));
  }

  @Test
  void getNodesWorksTest() {
    setField(subprocess, NODES, nodes);

    assertEquals(nodes, subprocess.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(subprocess, NODES, null);

    subprocess.setNodes(nodes);
    assertEquals(nodes, getField(subprocess, NODES));
  }

  @Test
  void getTypeWorksTest() {
    setField(subprocess, TYPE, EMBEDDED);

    assertEquals(EMBEDDED, subprocess.getType());
  }

  @Test
  void setTypeWorksTest() {
    setField(subprocess, TYPE, null);

    subprocess.setType(EMBEDDED);
    assertEquals(EMBEDDED, getField(subprocess, TYPE));
  }

  @Test
  void getLoopRefWorksTest() {
    setField(subprocess, LOOPREF, embeddedLoopReference);

    assertEquals(embeddedLoopReference, subprocess.getLoopRef());
  }

  @Test
  void setLoopRefWorksTest() {
    setField(subprocess, LOOPREF, null);

    subprocess.setLoopRef(embeddedLoopReference);
    assertEquals(embeddedLoopReference, getField(subprocess, LOOPREF));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected, Map<String, Boolean> persist) {

    initial.forEach((String attribute, Object value) -> {
      setField(subprocess, attribute, value);
    });

    subprocess.prePersist();

    expected.forEach((String attribute, Object value) -> {
      if (Boolean.TRUE.equals(persist.get(attribute))) {
        if (attribute == LOOPREF) {
          verify((EmbeddedLoopReference) value).prePersist();
        }
      } else if (Boolean.FALSE.equals(persist.get(attribute))) {
        if (attribute == LOOPREF) {
          verify((EmbeddedLoopReference) value, never()).prePersist();
        }
      } else {
        assertEquals(value, getField(subprocess, attribute));
      }
    });
  }

  /**
   * Helper function for parameterized tests for the prePersist function.
   *
   * @return
   *   The arguments array stream with the stream columns as:
   *     - Arguments initial The initial values.
   *     - Arguments expect  The expected values.
   *     - Arguments persist Boolean representing whether or not this will call prePersist() on the object and if so, then true/false depending on verify.
   */
  private static Stream<Arguments> providePrePersistFor() {

    final EmbeddedLoopReference loopRef = Mockito.spy(new EmbeddedLoopReference());
    final EmbeddedLoopReference loopRefNull = null;

    return List.of(
      Arguments.of(
        helperFieldMap(null,        loopRefNull),
        helperFieldMap(EMBEDDED,    loopRefNull),
        helperPersistMap(           null)
      ),
      Arguments.of(
        helperFieldMap(TRANSACTION, loopRefNull),
        helperFieldMap(TRANSACTION, loopRefNull),
        helperPersistMap(           null)
      ),
      Arguments.of(
        helperFieldMap(null,        loopRef),
        helperFieldMap(EMBEDDED,    loopRef),
        helperPersistMap(           true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param type    The type value.
   * @param loopRef The loopRef value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(SubprocessType type, EmbeddedLoopReference loopRef) {

    final Map<String, Object> map = new HashMap<>();

    map.put(TYPE, type);
    map.put(LOOPREF, loopRef);

    return map;
  }

  /**
   * Helper for reducing in line code repetition for assignments for persist setting.
   *
   * @param loopRef The loopRef persist value.
   *
   * @return The built persist map.
   */
  private static Map<String, Object> helperPersistMap(Boolean loopRef) {

    final Map<String, Object> map = new HashMap<>();

    map.put(LOOPREF, loopRef);

    return map;
  }

}
