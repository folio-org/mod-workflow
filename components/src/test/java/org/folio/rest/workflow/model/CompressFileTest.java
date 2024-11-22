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
    setField(compressFileTask, "id", VALUE);

    assertEquals(VALUE, compressFileTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(compressFileTask, "id", null);

    compressFileTask.setId(VALUE);
    assertEquals(VALUE, getField(compressFileTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(compressFileTask, "name", VALUE);

    assertEquals(VALUE, compressFileTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(compressFileTask, "name", null);

    compressFileTask.setName(VALUE);
    assertEquals(VALUE, getField(compressFileTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(compressFileTask, "description", VALUE);

    assertEquals(VALUE, compressFileTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(compressFileTask, "description", null);

    compressFileTask.setDescription(VALUE);
    assertEquals(VALUE, getField(compressFileTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(compressFileTask, "deserializeAs", VALUE);

    assertEquals(VALUE, compressFileTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(compressFileTask, "deserializeAs", null);

    compressFileTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(compressFileTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(compressFileTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, compressFileTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(compressFileTask, "inputVariables", null);

    compressFileTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(compressFileTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(compressFileTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, compressFileTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(compressFileTask, "outputVariable", null);

    compressFileTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(compressFileTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(compressFileTask, "asyncBefore", true);

    assertEquals(true, compressFileTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(compressFileTask, "asyncBefore", false);

    compressFileTask.setAsyncBefore(true);
    assertEquals(true, getField(compressFileTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(compressFileTask, "asyncAfter", true);

    assertEquals(true, compressFileTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(compressFileTask, "asyncAfter", false);

    compressFileTask.setAsyncAfter(true);
    assertEquals(true, getField(compressFileTask, "asyncAfter"));
  }

  @Test
  void getSourceWorksTest() {
    setField(compressFileTask, "source", VALUE);

    assertEquals(VALUE, compressFileTask.getSource());
  }

  @Test
  void setSourceWorksTest() {
    setField(compressFileTask, "source", null);

    compressFileTask.setSource(VALUE);
    assertEquals(VALUE, getField(compressFileTask, "source"));
  }

  @Test
  void getDestinationWorksTest() {
    setField(compressFileTask, "destination", VALUE);

    assertEquals(VALUE, compressFileTask.getDestination());
  }

  @Test
  void setDestinationWorksTest() {
    setField(compressFileTask, "destination", null);

    compressFileTask.setDestination(VALUE);
    assertEquals(VALUE, getField(compressFileTask, "destination"));
  }

  @Test
  void getFormatWorksTest() {
    setField(compressFileTask, "format", BZIP2);

    assertEquals(BZIP2, compressFileTask.getFormat());
  }

  @Test
  void setFormatWorksTest() {
    setField(compressFileTask, "format", null);

    compressFileTask.setFormat(BZIP2);
    assertEquals(BZIP2, getField(compressFileTask, "format"));
  }

  @Test
  void getContainerWorksTest() {
    setField(compressFileTask, "container", TAR);

    assertEquals(TAR, compressFileTask.getContainer());
  }

  @Test
  void setContainerWorksTest() {
    setField(compressFileTask, "container", null);

    compressFileTask.setContainer(TAR);
    assertEquals(TAR, getField(compressFileTask, "container"));
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

    return Stream.of(
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
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
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

    map.put("source", source);
    map.put("destination", destination);
    map.put("format", format);
    map.put("container", container);

    return map;
  }

}
