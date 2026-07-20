package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseConnectionTaskTest {

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String DESIGNATION    = "designation";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";
  private static final String PASSWORD       = "password";
  private static final String URL            = "url";
  private static final String USERNAME       = "username";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private DatabaseConnectionTask databaseConnectionTask;

  @BeforeEach
  void beforeEach() {
    databaseConnectionTask = new DatabaseConnectionTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(databaseConnectionTask, ID, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(databaseConnectionTask, ID, null);

    databaseConnectionTask.setId(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(databaseConnectionTask, NAME, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(databaseConnectionTask, NAME, null);

    databaseConnectionTask.setName(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(databaseConnectionTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(databaseConnectionTask, DESCRIPTION, null);

    databaseConnectionTask.setDescription(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(databaseConnectionTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(databaseConnectionTask, DESERIALIZEAS, null);

    databaseConnectionTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(databaseConnectionTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, databaseConnectionTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(databaseConnectionTask, INPUTVARIABLES, null);

    databaseConnectionTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(databaseConnectionTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(databaseConnectionTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, databaseConnectionTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(databaseConnectionTask, OUTPUTVARIABLE, null);

    databaseConnectionTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(databaseConnectionTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(databaseConnectionTask, ASYNCBEFORE, true);

    assertEquals(true, databaseConnectionTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(databaseConnectionTask, ASYNCBEFORE, false);

    databaseConnectionTask.setAsyncBefore(true);
    assertEquals(true, getField(databaseConnectionTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(databaseConnectionTask, ASYNCAFTER, true);

    assertEquals(true, databaseConnectionTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(databaseConnectionTask, ASYNCAFTER, false);

    databaseConnectionTask.setAsyncAfter(true);
    assertEquals(true, getField(databaseConnectionTask, ASYNCAFTER));
  }

  @Test
  void getDesignationWorksTest() {
    setField(databaseConnectionTask, DESIGNATION, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getDesignation());
  }

  @Test
  void setDesignationWorksTest() {
    setField(databaseConnectionTask, DESIGNATION, null);

    databaseConnectionTask.setDesignation(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, DESIGNATION));
  }

  @Test
  void getUrlWorksTest() {
    setField(databaseConnectionTask, URL, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getUrl());
  }

  @Test
  void setUrlWorksTest() {
    setField(databaseConnectionTask, URL, null);

    databaseConnectionTask.setUrl(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, URL));
  }

  @Test
  void getUsernameWorksTest() {
    setField(databaseConnectionTask, USERNAME, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getUsername());
  }

  @Test
  void setUsernameWorksTest() {
    setField(databaseConnectionTask, USERNAME, null);

    databaseConnectionTask.setUsername(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, USERNAME));
  }

  @Test
  void getPasswordWorksTest() {
    setField(databaseConnectionTask, PASSWORD, VALUE);

    assertEquals(VALUE, databaseConnectionTask.getPassword());
  }

  @Test
  void setPasswordWorksTest() {
    setField(databaseConnectionTask, PASSWORD, null);

    databaseConnectionTask.setPassword(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, PASSWORD));
  }


  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(databaseConnectionTask, attribute, value);
    });

    databaseConnectionTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(databaseConnectionTask, attribute));
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
        helperFieldMap(NULL_STR, NULL_STR),
        helperFieldMap("",       "")
      ),
      Arguments.of(
        helperFieldMap(VALUE,    NULL_STR),
        helperFieldMap(VALUE,    "")
      ),
      Arguments.of(
        helperFieldMap(NULL_STR, VALUE),
        helperFieldMap("",       VALUE)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param designation The designation value.
   * @param url The url value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String designation, String url ) {
    final Map<String, Object> map = new HashMap<>();

    map.put(DESIGNATION, designation);
    map.put(URL, url);

    return map;
  }

}
