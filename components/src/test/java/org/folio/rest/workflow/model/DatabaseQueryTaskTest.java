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

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String DESIGNATION    = "designation";
  private static final String ID             = "id";
  private static final String INCLUDEHEADER  = "includeHeader";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTPATH     = "outputPath";
  private static final String OUTPUTVARIABLE = "outputVariable";
  private static final String QUERY          = "query";
  private static final String RESULTTYPE     = "resultType";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private DatabaseQueryTask databaseQueryTask;

  @BeforeEach
  void beforeEach() {
    databaseQueryTask = new DatabaseQueryTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(databaseQueryTask, ID, VALUE);

    assertEquals(VALUE, databaseQueryTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(databaseQueryTask, ID, null);

    databaseQueryTask.setId(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(databaseQueryTask, NAME, VALUE);

    assertEquals(VALUE, databaseQueryTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(databaseQueryTask, NAME, null);

    databaseQueryTask.setName(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(databaseQueryTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, databaseQueryTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(databaseQueryTask, DESCRIPTION, null);

    databaseQueryTask.setDescription(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(databaseQueryTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, databaseQueryTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(databaseQueryTask, DESERIALIZEAS, null);

    databaseQueryTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(databaseQueryTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, databaseQueryTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(databaseQueryTask, INPUTVARIABLES, null);

    databaseQueryTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(databaseQueryTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(databaseQueryTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, databaseQueryTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(databaseQueryTask, OUTPUTVARIABLE, null);

    databaseQueryTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(databaseQueryTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(databaseQueryTask, ASYNCBEFORE, true);

    assertEquals(true, databaseQueryTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(databaseQueryTask, ASYNCBEFORE, false);

    databaseQueryTask.setAsyncBefore(true);
    assertEquals(true, getField(databaseQueryTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(databaseQueryTask, ASYNCAFTER, true);

    assertEquals(true, databaseQueryTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(databaseQueryTask, ASYNCAFTER, false);

    databaseQueryTask.setAsyncAfter(true);
    assertEquals(true, getField(databaseQueryTask, ASYNCAFTER));
  }

  @Test
  void getDesignationWorksTest() {
    setField(databaseQueryTask, DESIGNATION, VALUE);

    assertEquals(VALUE, databaseQueryTask.getDesignation());
  }

  @Test
  void setDesignationWorksTest() {
    setField(databaseQueryTask, DESIGNATION, null);

    databaseQueryTask.setDesignation(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, DESIGNATION));
  }

  @Test
  void getOutputPathWorksTest() {
    setField(databaseQueryTask, OUTPUTPATH, VALUE);

    assertEquals(VALUE, databaseQueryTask.getOutputPath());
  }

  @Test
  void setOutputPathWorksTest() {
    setField(databaseQueryTask, OUTPUTPATH, null);

    databaseQueryTask.setOutputPath(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, OUTPUTPATH));
  }

  @Test
  void getQueryWorksTest() {
    setField(databaseQueryTask, QUERY, VALUE);

    assertEquals(VALUE, databaseQueryTask.getQuery());
  }

  @Test
  void setQueryWorksTest() {
    setField(databaseQueryTask, QUERY, null);

    databaseQueryTask.setQuery(VALUE);
    assertEquals(VALUE, getField(databaseQueryTask, QUERY));
  }

  @Test
  void getResultTypeWorksTest() {
    setField(databaseQueryTask, RESULTTYPE, DatabaseResultType.CSV);

    assertEquals(DatabaseResultType.CSV, databaseQueryTask.getResultType());
  }

  @Test
  void setResultTypeWorksTest() {
    setField(databaseQueryTask, RESULTTYPE, null);

    databaseQueryTask.setResultType(DatabaseResultType.CSV);
    assertEquals(DatabaseResultType.CSV, getField(databaseQueryTask, RESULTTYPE));
  }

  @Test
  void getIncludeHeaderWorksTest() {
    setField(databaseQueryTask, INCLUDEHEADER, true);

    assertEquals(true, databaseQueryTask.getIncludeHeader());
  }

  @Test
  void setIncludeHeaderWorksTest() {
    setField(databaseQueryTask, INCLUDEHEADER, false);

    databaseQueryTask.setIncludeHeader(true);
    assertEquals(true, getField(databaseQueryTask, INCLUDEHEADER));
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

    return List.of(
      Arguments.of(
        helperFieldMap(NULL_STR, NULL_STR, null),
        helperFieldMap("",       "",       false)
      ),
      Arguments.of(
        helperFieldMap(VALUE,    NULL_STR, null),
        helperFieldMap(VALUE,    "",       false)
      ),
      Arguments.of(
        helperFieldMap(NULL_STR, VALUE,    null),
        helperFieldMap("",       VALUE,    false)
      ),
      Arguments.of(
        helperFieldMap(NULL_STR, NULL_STR, true),
        helperFieldMap("",       "",       true)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param designation The designation value.
   * @param query The query value.
   * @param includeHeader The includeHeader value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String designation, String query, Boolean includeHeader) {
    final Map<String, Object> map = new HashMap<>();

    map.put(DESIGNATION, designation);
    map.put(QUERY, query);
    map.put(INCLUDEHEADER, includeHeader);

    return map;
  }

}
