package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

@ExtendWith(MockitoExtension.class)
class AbstractTaskTest {

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  @Mock
  private Node node;

  private List<Node> nodes;

  private AbstractTask abstractTask;

  @BeforeEach
  void beforeEach() {
    abstractTask = new Impl();
    nodes = new ArrayList<>();
    nodes.add(node);
  }

  @Test
  void getIdWorksTest() {
    setField(abstractTask, "id", VALUE);

    assertEquals(VALUE, abstractTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractTask, "id", null);

    abstractTask.setId(VALUE);
    assertEquals(VALUE, getField(abstractTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractTask, "name", VALUE);

    assertEquals(VALUE, abstractTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractTask, "name", null);

    abstractTask.setName(VALUE);
    assertEquals(VALUE, getField(abstractTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractTask, "description", VALUE);

    assertEquals(VALUE, abstractTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractTask, "description", null);

    abstractTask.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractTask, "deserializeAs", VALUE);

    assertEquals(VALUE, abstractTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractTask, "deserializeAs", null);

    abstractTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(abstractTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, abstractTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(abstractTask, "inputVariables", null);

    abstractTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(abstractTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(abstractTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, abstractTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(abstractTask, "outputVariable", null);

    abstractTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(abstractTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(abstractTask, "asyncBefore", true);

    assertEquals(true, abstractTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(abstractTask, "asyncBefore", false);

    abstractTask.setAsyncBefore(true);
    assertEquals(true, getField(abstractTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(abstractTask, "asyncAfter", true);

    assertEquals(true, abstractTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(abstractTask, "asyncAfter", false);

    abstractTask.setAsyncAfter(true);
    assertEquals(true, getField(abstractTask, "asyncAfter"));
  }

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected, Map<String, Boolean> persist) {

    initial.forEach((String attribute, Object value) -> {
      setField(abstractTask, attribute, value);
    });

    abstractTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      if (attribute == "inputVariables") {
        final Set<EmbeddedVariable> eps = (Set<EmbeddedVariable>) value;

        assertNotNull(getField(abstractTask, attribute));
        assertEquals(eps.size(), ((Set<EmbeddedVariable>) getField(abstractTask, attribute)).size());

        eps.forEach(ep -> {
          if (ep != null) {
            if (Boolean.TRUE.equals(persist.get(attribute))) {
              verify(ep).prePersist();
            } else if (Boolean.FALSE.equals(persist.get(attribute))) {
              verify(ep, never()).prePersist();
            }
          }
        });
      } else if (attribute == "outputVariable") {
        if (Boolean.TRUE.equals(persist.get(attribute))) {
          verify((EmbeddedVariable) value).prePersist();
        } else if (Boolean.FALSE.equals(persist.get(attribute))) {
          verify((EmbeddedVariable) value, never()).prePersist();
        }
      } else {
        assertEquals(value, getField(abstractTask, attribute));
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

    final EmbeddedVariable iv = Mockito.spy(new EmbeddedVariable());
    final EmbeddedVariable ov = Mockito.spy(new EmbeddedVariable());
    final EmbeddedVariable nullValue = null;
    final Set<EmbeddedVariable> evs = Set.of(iv);
    final Set<EmbeddedVariable> evsEmpty = Set.of();
    final Set<EmbeddedVariable> evsNull = new HashSet<>();

    evsNull.add(nullValue);

    return List.of(
      Arguments.of(
        helperFieldMap(null,  null,  null,     nullValue),
        helperFieldMap(false, false, evsEmpty, nullValue),
        helperPersistMap(            false,    null)
      ),
      Arguments.of(
        helperFieldMap(true,  null,  null,     nullValue),
        helperFieldMap(true,  false, evsEmpty, nullValue),
        helperPersistMap(            false,    null)
      ),
      Arguments.of(
        helperFieldMap(null,  true,  null,     nullValue),
        helperFieldMap(false, true,  evsEmpty, nullValue),
        helperPersistMap(            false,    null)
      ),
      Arguments.of(
        helperFieldMap(null,  null,  evsNull,  nullValue),
        helperFieldMap(false, false, evsNull,  nullValue),
        helperPersistMap(            false,    null)
      ),
      Arguments.of(
        helperFieldMap(null,  null,  evs,      nullValue),
        helperFieldMap(false, false, evs,      nullValue),
        helperPersistMap(            true,     null)
      ),
      Arguments.of(
        helperFieldMap(null,  true,  null,     ov),
        helperFieldMap(false, true,  evsEmpty, ov),
        helperPersistMap(            false,    true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param asyncBefore    The asyncBefore value.
   * @param asyncAfter     The asyncAfter value.
   * @param inputVariables The inputVariables value.
   * @param outputVariable The outputVariable value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Boolean asyncBefore, Boolean asyncAfter, Set<EmbeddedVariable> inputVariables, EmbeddedVariable outputVariable) {
    final Map<String, Object> map = new HashMap<>();

    map.put("asyncBefore", asyncBefore);
    map.put("asyncAfter", asyncAfter);
    map.put("inputVariables", inputVariables);
    map.put("outputVariable", outputVariable);

    return map;
  }

  /**
   * Helper for reducing in line code repetition for assignments for persist setting.
   *
   * @param inputVariables The inputVariables persist value.
   * @param outputVariable The outputVariable persist value.
   *
   * @return The built persist map.
   */
  private static Map<String, Object> helperPersistMap(Boolean inputVariables, Boolean outputVariable) {

    final Map<String, Object> map = new HashMap<>();

    map.put("inputVariables", inputVariables);
    map.put("outputVariable", outputVariable);

    return map;
  }

  private static class Impl extends AbstractTask { }

}
