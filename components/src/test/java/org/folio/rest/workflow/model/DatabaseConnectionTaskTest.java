package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.HashSet;
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
    setField(databaseConnectionTask, "id", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(databaseConnectionTask, "id", null);

    databaseConnectionTask.setId(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(databaseConnectionTask, "name", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(databaseConnectionTask, "name", null);

    databaseConnectionTask.setName(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(databaseConnectionTask, "description", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(databaseConnectionTask, "description", null);

    databaseConnectionTask.setDescription(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(databaseConnectionTask, "deserializeAs", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(databaseConnectionTask, "deserializeAs", null);

    databaseConnectionTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(databaseConnectionTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, databaseConnectionTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(databaseConnectionTask, "inputVariables", null);

    databaseConnectionTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(databaseConnectionTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(databaseConnectionTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, databaseConnectionTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(databaseConnectionTask, "outputVariable", null);

    databaseConnectionTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(databaseConnectionTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(databaseConnectionTask, "asyncBefore", true);

    assertEquals(true, databaseConnectionTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(databaseConnectionTask, "asyncBefore", false);

    databaseConnectionTask.setAsyncBefore(true);
    assertEquals(true, getField(databaseConnectionTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(databaseConnectionTask, "asyncAfter", true);

    assertEquals(true, databaseConnectionTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(databaseConnectionTask, "asyncAfter", false);

    databaseConnectionTask.setAsyncAfter(true);
    assertEquals(true, getField(databaseConnectionTask, "asyncAfter"));
  }

  @Test
  void getDesignationWorksTest() {
    setField(databaseConnectionTask, "designation", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getDesignation());
  }

  @Test
  void setDesignationWorksTest() {
    setField(databaseConnectionTask, "designation", null);

    databaseConnectionTask.setDesignation(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "designation"));
  }

  @Test
  void getUrlWorksTest() {
    setField(databaseConnectionTask, "url", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getUrl());
  }

  @Test
  void setUrlWorksTest() {
    setField(databaseConnectionTask, "url", null);

    databaseConnectionTask.setUrl(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "url"));
  }

  @Test
  void getUsernameWorksTest() {
    setField(databaseConnectionTask, "username", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getUsername());
  }

  @Test
  void setUsernameWorksTest() {
    setField(databaseConnectionTask, "username", null);

    databaseConnectionTask.setUsername(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "username"));
  }

  @Test
  void getPasswordWorksTest() {
    setField(databaseConnectionTask, "password", VALUE);

    assertEquals(VALUE, databaseConnectionTask.getPassword());
  }

  @Test
  void setPasswordWorksTest() {
    setField(databaseConnectionTask, "password", null);

    databaseConnectionTask.setPassword(VALUE);
    assertEquals(VALUE, getField(databaseConnectionTask, "password"));
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
    final String designation = "designation";
    final String url = "url";

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,  null),
        helperFieldMap("", "")
      ),
      Arguments.of(
        helperFieldMap("",  ""),
        helperFieldMap("", "")
      ),
      Arguments.of(
        helperFieldMap(designation,url),
        helperFieldMap(designation, url)
      ),
      Arguments.of(
        helperFieldMap(designation,  null),
        helperFieldMap(designation, "")
      ),
      Arguments.of(
        helperFieldMap(null,  url),
        helperFieldMap("", url)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param designation The designation value.
   * @param url The url value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String designation, String url ) {
    final Map<String, Object> map = new HashMap<>();

    map.put("designation", designation);
    map.put("url", url);

    return map;
  }

}
