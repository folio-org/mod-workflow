package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

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
class RequestTaskTest {

  @Mock
  private EmbeddedVariable embeddedVariable;

  @Mock
  private EmbeddedRequest embeddedRequest;

  private Set<EmbeddedVariable> inputVariables;

  private RequestTask requestTask;

  @BeforeEach
  void beforeEach() {
    requestTask = new RequestTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(requestTask, "id", VALUE);

    assertEquals(VALUE, requestTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(requestTask, "id", null);

    requestTask.setId(VALUE);
    assertEquals(VALUE, getField(requestTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(requestTask, "name", VALUE);

    assertEquals(VALUE, requestTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(requestTask, "name", null);

    requestTask.setName(VALUE);
    assertEquals(VALUE, getField(requestTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(requestTask, "description", VALUE);

    assertEquals(VALUE, requestTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(requestTask, "description", null);

    requestTask.setDescription(VALUE);
    assertEquals(VALUE, getField(requestTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(requestTask, "deserializeAs", VALUE);

    assertEquals(VALUE, requestTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(requestTask, "deserializeAs", null);

    requestTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(requestTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(requestTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, requestTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(requestTask, "inputVariables", null);

    requestTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(requestTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(requestTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, requestTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(requestTask, "outputVariable", null);

    requestTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(requestTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(requestTask, "asyncBefore", true);

    assertEquals(true, requestTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(requestTask, "asyncBefore", false);

    requestTask.setAsyncBefore(true);
    assertEquals(true, getField(requestTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(requestTask, "asyncAfter", true);

    assertEquals(true, requestTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(requestTask, "asyncAfter", false);

    requestTask.setAsyncAfter(true);
    assertEquals(true, getField(requestTask, "asyncAfter"));
  }

  @Test
  void getHeaderOutputVariablesWorksTest() {
    setField(requestTask, "headerOutputVariables", inputVariables);

    assertEquals(inputVariables, requestTask.getHeaderOutputVariables());
  }

  @Test
  void setHeaderOutputVariablesWorksTest() {
    setField(requestTask, "headerOutputVariables", null);

    requestTask.setHeaderOutputVariables(inputVariables);
    assertEquals(inputVariables, getField(requestTask, "headerOutputVariables"));
  }

  @Test
  void getRequestWorksTest() {
    setField(requestTask, "request", embeddedRequest);

    assertEquals(embeddedRequest, requestTask.getRequest());
  }

  @Test
  void setRequestWorksTest() {
    setField(requestTask, "request", null);

    requestTask.setRequest(embeddedRequest);
    assertEquals(embeddedRequest, getField(requestTask, "request"));
  }

  @SuppressWarnings("unchecked")
  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected, Map<String, Boolean> persist) {

    initial.forEach((String attribute, Object value) -> {
      setField(requestTask, attribute, value);
    });

    requestTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      if (attribute == "headerOutputVariables") {
        final Set<EmbeddedVariable> eps = (Set<EmbeddedVariable>) value;

        assertNotNull(getField(requestTask, attribute));
        assertEquals(eps.size(), ((Set<EmbeddedVariable>) getField(requestTask, attribute)).size());

        eps.forEach(ep -> {
          if (ep != null) {
            if (Boolean.TRUE.equals(persist.get(attribute))) {
              verify(ep).prePersist();
            } else if (Boolean.FALSE.equals(persist.get(attribute))) {
              verify(ep, never()).prePersist();
            }
          }
        });
      } else {
        assertEquals(value, getField(requestTask, attribute));
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

    final EmbeddedVariable variable = Mockito.spy(new EmbeddedVariable());
    final EmbeddedVariable nullValue = null;
    final Set<EmbeddedVariable> headerOutputVariables = Set.of(variable);
    final Set<EmbeddedVariable> headerOutputVariablesEmpty = Set.of();
    final Set<EmbeddedVariable> headerOutputVariablesNull = new HashSet<>();

    headerOutputVariablesNull.add(nullValue);

    return List.of(
      Arguments.of(
        helperFieldMap(null),
        helperFieldMap(headerOutputVariablesEmpty),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(headerOutputVariablesEmpty),
        helperFieldMap(headerOutputVariablesEmpty),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(headerOutputVariablesNull),
        helperFieldMap(headerOutputVariablesNull),
        helperPersistMap(false)
      ),
      Arguments.of(
        helperFieldMap(headerOutputVariables),
        helperFieldMap(headerOutputVariables),
        helperPersistMap(true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param headerOutputVariables The headerOutputVariables value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(Set<EmbeddedVariable> headerOutputVariables) {

    final Map<String, Object> map = new HashMap<>();

    map.put("headerOutputVariables", headerOutputVariables);

    return map;
  }

  /**
   * Helper for reducing in line code repetition for assignments for persist setting.
   *
   * @param headerOutputVariables The headerOutputVariables persist value.
   *
   * @return The built persist map.
   */
  private static Map<String, Object> helperPersistMap(Boolean headerOutputVariables) {

    final Map<String, Object> map = new HashMap<>();

    map.put("headerOutputVariables", headerOutputVariables);

    return map;
  }

}
