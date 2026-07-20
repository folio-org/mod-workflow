package org.folio.rest.workflow.model;

import static org.folio.rest.workflow.enums.CompressFileContainer.NONE;
import static org.folio.rest.workflow.enums.CompressFileContainer.TAR;
import static org.folio.rest.workflow.enums.CompressFileFormat.BZIP2;
import static org.folio.rest.workflow.enums.CompressFileFormat.GZIP;
import static org.folio.rest.workflow.enums.CompressFileFormat.ZIP;
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
import org.folio.rest.workflow.enums.CompressFileContainer;
import org.folio.rest.workflow.enums.CompressFileFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompressFileTest {

  private static final String ASYNCAFTER     = "asyncAfter";
  private static final String ASYNCBEFORE    = "asyncBefore";
  private static final String CONTAINER      = "container";
  private static final String DESCRIPTION    = "description";
  private static final String DESERIALIZEAS  = "deserializeAs";
  private static final String DESTINATION    = "destination";
  private static final String FORMAT         = "format";
  private static final String ID             = "id";
  private static final String INPUTVARIABLES = "inputVariables";
  private static final String NAME           = "name";
  private static final String OUTPUTVARIABLE = "outputVariable";
  private static final String SOURCE         = "source";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private CompressFileTask compressFileTask;

  @BeforeEach
  void beforeEach() {
    compressFileTask = new CompressFileTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(compressFileTask, ID, VALUE);

    assertEquals(VALUE, compressFileTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(compressFileTask, ID, null);

    compressFileTask.setId(VALUE);
    assertEquals(VALUE, getField(compressFileTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(compressFileTask, NAME, VALUE);

    assertEquals(VALUE, compressFileTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(compressFileTask, NAME, null);

    compressFileTask.setName(VALUE);
    assertEquals(VALUE, getField(compressFileTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(compressFileTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, compressFileTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(compressFileTask, DESCRIPTION, null);

    compressFileTask.setDescription(VALUE);
    assertEquals(VALUE, getField(compressFileTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(compressFileTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, compressFileTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(compressFileTask, DESERIALIZEAS, null);

    compressFileTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(compressFileTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(compressFileTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, compressFileTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(compressFileTask, INPUTVARIABLES, null);

    compressFileTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(compressFileTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(compressFileTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, compressFileTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(compressFileTask, OUTPUTVARIABLE, null);

    compressFileTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(compressFileTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(compressFileTask, ASYNCBEFORE, true);

    assertEquals(true, compressFileTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(compressFileTask, ASYNCBEFORE, false);

    compressFileTask.setAsyncBefore(true);
    assertEquals(true, getField(compressFileTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(compressFileTask, ASYNCAFTER, true);

    assertEquals(true, compressFileTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(compressFileTask, ASYNCAFTER, false);

    compressFileTask.setAsyncAfter(true);
    assertEquals(true, getField(compressFileTask, ASYNCAFTER));
  }

  @Test
  void getSourceWorksTest() {
    setField(compressFileTask, SOURCE, VALUE);

    assertEquals(VALUE, compressFileTask.getSource());
  }

  @Test
  void setSourceWorksTest() {
    setField(compressFileTask, SOURCE, null);

    compressFileTask.setSource(VALUE);
    assertEquals(VALUE, getField(compressFileTask, SOURCE));
  }

  @Test
  void getDestinationWorksTest() {
    setField(compressFileTask, DESTINATION, VALUE);

    assertEquals(VALUE, compressFileTask.getDestination());
  }

  @Test
  void setDestinationWorksTest() {
    setField(compressFileTask, DESTINATION, null);

    compressFileTask.setDestination(VALUE);
    assertEquals(VALUE, getField(compressFileTask, DESTINATION));
  }

  @Test
  void getFormatWorksTest() {
    setField(compressFileTask, FORMAT, BZIP2);

    assertEquals(BZIP2, compressFileTask.getFormat());
  }

  @Test
  void setFormatWorksTest() {
    setField(compressFileTask, FORMAT, null);

    compressFileTask.setFormat(BZIP2);
    assertEquals(BZIP2, getField(compressFileTask, FORMAT));
  }

  @Test
  void getContainerWorksTest() {
    setField(compressFileTask, CONTAINER, TAR);

    assertEquals(TAR, compressFileTask.getContainer());
  }

  @Test
  void setContainerWorksTest() {
    setField(compressFileTask, CONTAINER, null);

    compressFileTask.setContainer(TAR);
    assertEquals(TAR, getField(compressFileTask, CONTAINER));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(compressFileTask, attribute, value);
    });

    compressFileTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(compressFileTask, attribute));
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
        helperFieldMap(null,  null,  null, null),
        helperFieldMap("",    "",    ZIP,  NONE)
      ),
      Arguments.of(
        helperFieldMap(VALUE, null,  null, null),
        helperFieldMap(VALUE, "",    ZIP,  NONE)
      ),
      Arguments.of(
        helperFieldMap(null,  VALUE, null, null),
        helperFieldMap("",    VALUE, ZIP,  NONE)
      ),
      Arguments.of(
        helperFieldMap(null,  null,  GZIP, null),
        helperFieldMap("",    "",    GZIP, NONE)
      ),
      Arguments.of(
        helperFieldMap(null,  null,  null, TAR),
        helperFieldMap("",    "",    ZIP,  TAR)
      )
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param source The asyncBefore value.
   * @param destination The asyncAfter value.
   * @param format The format value.
   * @param container The container value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String source, String destination, CompressFileFormat format, CompressFileContainer container) {
    final Map<String, Object> map = new HashMap<>();

    map.put(CONTAINER, container);
    map.put(DESTINATION, destination);
    map.put(FORMAT, format);
    map.put(SOURCE, source);

    return map;
  }

}
