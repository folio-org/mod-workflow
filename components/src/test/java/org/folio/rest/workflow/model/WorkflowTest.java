package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.INT_VALUE;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.JsonNode;

@ExtendWith(MockitoExtension.class)
class WorkflowTest {

  /**
   * Provide a version string for the default version.
   */
  public static final String VERSION = "1.0";

  @Mock
  private Setup setup;

  @Mock
  private Node node;

  private List<Node> nodes;

  private Map<String, JsonNode> initialContext;

  private Workflow workflow;

  @BeforeEach
  void beforeEach() {
    workflow = new Workflow();
    nodes = new ArrayList<>();
    nodes.add(node);
    initialContext = new HashMap<>();
  }

  @Test
  void getIdWorksTest() {
    setField(workflow, "id", VALUE);

    assertEquals(VALUE, workflow.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(workflow, "id", null);

    workflow.setId(VALUE);
    assertEquals(VALUE, getField(workflow, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(workflow, "name", VALUE);

    assertEquals(VALUE, workflow.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(workflow, "name", null);

    workflow.setName(VALUE);
    assertEquals(VALUE, getField(workflow, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(workflow, "description", VALUE);

    assertEquals(VALUE, workflow.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(workflow, "description", null);

    workflow.setDescription(VALUE);
    assertEquals(VALUE, getField(workflow, "description"));
  }

  @Test
  void getNodesWorksTest() {
    setField(workflow, "nodes", nodes);

    assertEquals(nodes, workflow.getNodes());
  }

  @Test
  void setNodesWorksTest() {
    setField(workflow, "nodes", null);

    workflow.setNodes(nodes);
    assertEquals(nodes, getField(workflow, "nodes"));
  }

  @Test
  void getVersionTagWorksTest() {
    setField(workflow, "versionTag", VALUE);

    assertEquals(VALUE, workflow.getVersionTag());
  }

  @Test
  void setVersionTagWorksTest() {
    setField(workflow, "versionTag", null);

    workflow.setVersionTag(VALUE);
    assertEquals(VALUE, getField(workflow, "versionTag"));
  }

  @Test
  void getHistoryTimeToLiveWorksTest() {
    setField(workflow, "historyTimeToLive", 1);

    assertEquals(1, workflow.getHistoryTimeToLive());
  }

  @Test
  void setHistoryTimeToLiveWorksTest() {
    setField(workflow, "historyTimeToLive", null);

    workflow.setHistoryTimeToLive(1);
    assertEquals(1, getField(workflow, "historyTimeToLive"));
  }

  @Test
  void getActiveWorksTest() {
    setField(workflow, "active", true);

    assertEquals(true, workflow.getActive());
  }

  @Test
  void setActiveWorksTest() {
    setField(workflow, "active", false);

    workflow.setActive(true);
    assertEquals(true, getField(workflow, "active"));
  }

  @Test
  void getDeploymentIdWorksTest() {
    setField(workflow, "deploymentId", VALUE);

    assertEquals(VALUE, workflow.getDeploymentId());
  }

  @Test
  void setDeploymentIdWorksTest() {
    setField(workflow, "deploymentId", null);

    workflow.setDeploymentId(VALUE);
    assertEquals(VALUE, getField(workflow, "deploymentId"));
  }

  @Test
  void getSetupWorksTest() {
    setField(workflow, "setup", setup);

    assertEquals(setup, workflow.getSetup());
  }

  @Test
  void setSetupWorksTest() {
    setField(workflow, "setup", null);

    workflow.setSetup(setup);
    assertEquals(setup, getField(workflow, "setup"));
  }

  @Test
  void getInitialContextWorksTest() {
    setField(workflow, "initialContext", initialContext);

    assertEquals(initialContext, workflow.getInitialContext());
  }

  @Test
  void setInitialContextWorksTest() {
    setField(workflow, "initialContext", null);

    workflow.setInitialContext(initialContext);
    assertEquals(initialContext, getField(workflow, "initialContext"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected, Map<String, Boolean> persist) {

    initial.forEach((String attribute, Object value) -> {
      setField(workflow, attribute, value);
    });

    workflow.prePersist();

    expected.forEach((String attribute, Object value) -> {
      if (Boolean.TRUE.equals(persist.get(attribute))) {
        if (attribute == "setup") {
          verify((Setup) value).prePersist();
        }
      } else if (Boolean.FALSE.equals(persist.get(attribute))) {
        if (attribute == "setup") {
          verify((Setup) value, never()).prePersist();
        }
      } else {
        assertEquals(value, getField(workflow, attribute));
      }
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
    final Map<String, JsonNode> ic = new HashMap<>();
    ic.put(VALUE, null);

    final Map<String, JsonNode> icEmpty = new HashMap<>();

    final List<Node> nodeList = new ArrayList<>();
    nodeList.add(new NodeImpl());

    final List<Node> emptyList = new ArrayList<>();

    final Setup setup = Mockito.spy(new Setup());
    final Setup setupNull = null;

    return List.of(
      Arguments.of(
        helperFieldMap(null,  null,      null,  null,    null,      null,    setupNull),
        helperFieldMap(false, 0,         "",    icEmpty, emptyList, VERSION, setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(true,  null,      null,  null,    null,      null,    setupNull),
        helperFieldMap(true,  0,         "",    icEmpty, emptyList, VERSION, setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(null,  INT_VALUE, null,  null,    null,      null,    setupNull),
        helperFieldMap(false, INT_VALUE, "",    icEmpty, emptyList, VERSION, setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(null,  null,      VALUE, null,    null,      null,    setupNull),
        helperFieldMap(false, 0,         VALUE, icEmpty, emptyList, VERSION, setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(true,  null,      null,  ic,      null,      null,    setupNull),
        helperFieldMap(true,  0,         "",    ic,      emptyList, VERSION, setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(null,  null,      null,  null,    nodeList,  null,    setupNull),
        helperFieldMap(false, 0,         "",    icEmpty, nodeList,  VERSION, setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(null,  null,      null,  null,    null,      VALUE,   setupNull),
        helperFieldMap(false, 0,         "",    icEmpty, emptyList, VALUE,   setupNull),
        helperPersistMap(                                                    null)
      ),
      Arguments.of(
        helperFieldMap(null,  null,      null,  null,    null,      VALUE,   setup),
        helperFieldMap(false, 0,         "",    icEmpty, emptyList, VALUE,   setup),
        helperPersistMap(                                                    true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param active            The active value.
   * @param historyTimeToLive The historyTimeToLive value.
   * @param name              The name value.
   * @param initialContext    The initialContext value.
   * @param nodes             The nodes value.
   * @param versionTag        The versionTag value.
   * @param setup             The setup value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean active, Integer historyTimeToLive, String name, Map<String, JsonNode> initialContext, List<Node> nodes, String versionTag, Setup setup) {

    final Map<String, Object> map = new HashMap<>();

    map.put("active", active);
    map.put("historyTimeToLive", historyTimeToLive);
    map.put("name", name);
    map.put("initialContext", initialContext);
    map.put("nodes", nodes);
    map.put("versionTag", versionTag);
    map.put("setup", setup);

    return map;
  }

  /**
   * Helper for reducing in line code repetition for assignments for persist setting.
   *
   * @param setup The setup persist value.
   *
   * @return The built persist map.
   */
  private static Map<String, Object> helperPersistMap(Boolean setup) {

    final Map<String, Object> map = new HashMap<>();

    map.put("setup", setup);

    return map;
  }

  private static class NodeImpl extends Node { }

}
