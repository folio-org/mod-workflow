package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractTaskTest {

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";

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
    setField(abstractTask, ID, VALUE);

    assertEquals(VALUE, abstractTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(abstractTask, ID, null);

    abstractTask.setId(VALUE);
    assertEquals(VALUE, getField(abstractTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(abstractTask, NAME, VALUE);

    assertEquals(VALUE, abstractTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(abstractTask, NAME, null);

    abstractTask.setName(VALUE);
    assertEquals(VALUE, getField(abstractTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(abstractTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, abstractTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(abstractTask, DESCRIPTION, null);

    abstractTask.setDescription(VALUE);
    assertEquals(VALUE, getField(abstractTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(abstractTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, abstractTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(abstractTask, DESERIALIZEAS, null);

    abstractTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(abstractTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(abstractTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, abstractTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(abstractTask, INPUTVARIABLES, null);

    abstractTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(abstractTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(abstractTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, abstractTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(abstractTask, OUTPUTVARIABLE, null);

    abstractTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(abstractTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(abstractTask, ASYNCBEFORE, true);

    assertEquals(true, abstractTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(abstractTask, ASYNCBEFORE, false);

    abstractTask.setAsyncBefore(true);
    assertEquals(true, getField(abstractTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(abstractTask, ASYNCAFTER, true);

    assertEquals(true, abstractTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(abstractTask, ASYNCAFTER, false);

    abstractTask.setAsyncAfter(true);
    assertEquals(true, getField(abstractTask, ASYNCAFTER));
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
      if (attribute == INPUTVARIABLES) {
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
      } else if (attribute == OUTPUTVARIABLE) {
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

    final EmbeddedVariable iv = spy(new EmbeddedVariable());
    final EmbeddedVariable ov = spy(new EmbeddedVariable());
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

    map.put(ASYNCBEFORE, asyncBefore);
    map.put(ASYNCAFTER, asyncAfter);
    map.put(INPUTVARIABLES, inputVariables);
    map.put(OUTPUTVARIABLE, outputVariable);

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

    map.put(INPUTVARIABLES, inputVariables);
    map.put(OUTPUTVARIABLE, outputVariable);

    return map;
  }

  private static class Impl extends AbstractTask { }

}
