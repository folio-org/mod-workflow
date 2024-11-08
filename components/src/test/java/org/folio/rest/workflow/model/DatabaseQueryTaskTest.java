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

import org.folio.rest.workflow.enums.DatabaseResultType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseQueryTaskTest {

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private DatabaseQueryTask databaseQueryTask;

  @BeforeEach
  void beforeEach() {
    databaseQueryTask = new Impl();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(databaseQueryTask, "id", VALUE);

    assertEquals(VALUE, databaseQueryTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(databaseQueryTask, "id", null);

    databaseQueryTask.setId(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(databaseQueryTask, "name", VALUE);

    assertEquals(VALUE, databaseQueryTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(databaseQueryTask, "name", null);

    databaseQueryTask.setName(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(databaseQueryTask, "description", VALUE);

    assertEquals(VALUE, databaseQueryTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(databaseQueryTask, "description", null);

    databaseQueryTask.setDescription(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(databaseQueryTask, "deserializeAs", VALUE);

    assertEquals(VALUE, databaseQueryTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(databaseQueryTask, "deserializeAs", null);

    databaseQueryTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(databaseQueryTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, databaseQueryTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(databaseQueryTask, "inputVariables", null);

    databaseQueryTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(databaseQueryTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(databaseQueryTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, databaseQueryTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(databaseQueryTask, "outputVariable", null);

    databaseQueryTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(databaseQueryTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(databaseQueryTask, "asyncBefore", true);

    assertEquals(true, databaseQueryTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(databaseQueryTask, "asyncBefore", false);

    databaseQueryTask.setAsyncBefore(true);
    assertEquals(true, getField(databaseQueryTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(databaseQueryTask, "asyncAfter", true);

    assertEquals(true, databaseQueryTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(databaseQueryTask, "asyncAfter", false);

    databaseQueryTask.setAsyncAfter(true);
    assertEquals(true, getField(databaseQueryTask, "asyncAfter"));
  }

  @Test
  void getDesignationWorksTest() {
    setField(databaseQueryTask, "designation", VALUE);

    assertEquals(VALUE, databaseQueryTask.getDesignation());
  }

  @Test
  void setDesignationWorksTest() {
    setField(databaseQueryTask, "designation", null);

    databaseQueryTask.setDesignation(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "designation"));
  }

  @Test
  void getOutputPathWorksTest() {
    setField(databaseQueryTask, "outputPath", VALUE);

    assertEquals(VALUE, databaseQueryTask.getOutputPath());
  }

  @Test
  void setOutputPathWorksTest() {
    setField(databaseQueryTask, "outputPath", null);

    databaseQueryTask.setOutputPath(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "outputPath"));
  }

  @Test
  void getQueryWorksTest() {
    setField(databaseQueryTask, "query", VALUE);

    assertEquals(VALUE, databaseQueryTask.getQuery());
  }

  @Test
  void setQueryWorksTest() {
    setField(databaseQueryTask, "query", null);

    databaseQueryTask.setQuery(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, "query"));
  }

  @Test
  void getResultTypeWorksTest() {
    setField(databaseQueryTask, "resultType", DatabaseResultType.CSV);

    assertEquals(DatabaseResultType.CSV, databaseQueryTask.getResultType());
  }

  @Test
  void setResultTypeWorksTest() {
    setField(databaseQueryTask, "resultType", null);

    databaseQueryTask.setResultType(DatabaseResultType.CSV);
    assertEquals(DatabaseResultType.CSV, getField(databaseQueryTask, "resultType"));
  }

  @Test
  void getIncludeHeaderWorksTest() {
    setField(databaseQueryTask, "includeHeader", true);

    assertEquals(true, databaseQueryTask.getIncludeHeader());
  }

  @Test
  void setIncludeHeaderWorksTest() {
    setField(databaseQueryTask, "includeHeader", false);

    databaseQueryTask.setIncludeHeader(true);
    assertEquals(true, getField(databaseQueryTask, "includeHeader"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(databaseQueryTask, attribute, value);
    });

    databaseQueryTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(databaseQueryTask, attribute));
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
    final String designation = "designationValue";
    final String query = "queryValue";

    return Stream.of(
      Arguments.of(
        helperFieldMap(null, null, null),
        helperFieldMap("", "", false)
      ),
      Arguments.of(
        helperFieldMap("", null, true),
        helperFieldMap("", "", true)
      ),
      Arguments.of(
        helperFieldMap(null, null, true),
        helperFieldMap("", "", true)
      ),
      Arguments.of(
        helperFieldMap(designation, "", null),
        helperFieldMap(designation, "", false)
      ),
      Arguments.of(
        helperFieldMap(null, query, false),
        helperFieldMap("", query, false)
      ),
      Arguments.of(
        helperFieldMap(designation, query, true),
        helperFieldMap(designation, query, true)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param designation The designation value.
   * @param query The query value.
   * @param includeHeader The includeHeader value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String designation, String query, Boolean includeHeader) {
    final Map<String, Object> map = new HashMap<>();

    map.put("designation", designation);
    map.put("query", query);
    map.put("includeHeader", includeHeader);

    return map;
  }

  private static class Impl extends DatabaseQueryTask { }

}
