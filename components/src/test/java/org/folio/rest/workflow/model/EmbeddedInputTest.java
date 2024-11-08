package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.folio.rest.workflow.enums.InputAttribute;
import org.folio.rest.workflow.enums.InputType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class EmbeddedInputTest {

  private List<InputAttribute> attributes;

  private List<String> options;

  private EmbeddedInput embeddedInput;

  @BeforeEach
  void beforeEach() {
    attributes = new ArrayList<>();
    options = new ArrayList<>();

    embeddedInput = new EmbeddedInput();
  }

  @Test
  void getAttributesWorksTest() {
    setField(embeddedInput, "attributes", attributes);

    assertEquals(attributes, embeddedInput.getAttributes());
  }

  @Test
  void setAttributesWorksTest() {
    setField(embeddedInput, "attributes", null);

    embeddedInput.setAttributes(attributes);
    assertEquals(attributes, getField(embeddedInput, "attributes"));
  }

  @Test
  void getDefaultValueWorksTest() {
    setField(embeddedInput, "defaultValue", VALUE);

    assertEquals(VALUE, embeddedInput.getDefaultValue());
  }

  @Test
  void setDefaultValueWorksTest() {
    setField(embeddedInput, "defaultValue", null);

    embeddedInput.setDefaultValue(VALUE);
    assertEquals(VALUE, getField(embeddedInput, "defaultValue"));
  }

  @Test
  void getFieldIdWorksTest() {
    setField(embeddedInput, "fieldId", VALUE);

    assertEquals(VALUE, embeddedInput.getFieldId());
  }

  @Test
  void setFieldIdWorksTest() {
    setField(embeddedInput, "fieldId", null);

    embeddedInput.setFieldId(VALUE);
    assertEquals(VALUE, getField(embeddedInput, "fieldId"));
  }

  @Test
  void getFieldLabelWorksTest() {
    setField(embeddedInput, "fieldLabel", VALUE);

    assertEquals(VALUE, embeddedInput.getFieldLabel());
  }

  @Test
  void setFieldLabelWorksTest() {
    setField(embeddedInput, "fieldLabel", null);

    embeddedInput.setFieldLabel(VALUE);
    assertEquals(VALUE, getField(embeddedInput, "fieldLabel"));
  }

  @Test
  void getInputTypeWorksTest() {
    setField(embeddedInput, "inputType", InputType.EMAIL);

    assertEquals(InputType.EMAIL, embeddedInput.getInputType());
  }

  @Test
  void setInputTypeWorksTest() {
    setField(embeddedInput, "inputType", null);

    embeddedInput.setInputType(InputType.CHECKBOX);
    assertEquals(InputType.CHECKBOX, getField(embeddedInput, "inputType"));
  }

  @Test
  void getOptionsWorksTest() {
    setField(embeddedInput, "options", options);

    assertEquals(options, embeddedInput.getOptions());
  }

  @Test
  void setOptionsWorksTest() {
    setField(embeddedInput, "options", null);

    embeddedInput.setOptions(options);
    assertEquals(options, getField(embeddedInput, "options"));
  }

  @Test
  void getRequiredWorksTest() {
    setField(embeddedInput, "required", true);

    assertEquals(true, embeddedInput.getRequired());
  }

  @Test
  void setRequiredWorksTest() {
    setField(embeddedInput, "required", false);

    embeddedInput.setRequired(true);
    assertEquals(true, getField(embeddedInput, "required"));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(embeddedInput, attribute, value);
    });

    embeddedInput.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(embeddedInput, attribute));
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
    final List<String> strList = new ArrayList<>();
    strList.add(VALUE);

    final ArrayList<InputAttribute> attrList = new ArrayList<>();
    attrList.add(InputAttribute.MAX);

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,              null,  null,  null,           null,              null),
        helperFieldMap(new ArrayList<>(), "",    "",    InputType.TEXT, new ArrayList<>(), false)
      ),
      Arguments.of(
        helperFieldMap(attrList         , null,  null,  null,           null,              null),
        helperFieldMap(attrList         , "",    "",    InputType.TEXT, new ArrayList<>(), false)
      ),
      Arguments.of(
        helperFieldMap(null,              VALUE, null,  null,           null,              null),
        helperFieldMap(new ArrayList<>(), VALUE, "",    InputType.TEXT, new ArrayList<>(), false)
      ),
      Arguments.of(
        helperFieldMap(null,              null,  VALUE, null,           null,              null),
        helperFieldMap(new ArrayList<>(), "",    VALUE, InputType.TEXT, new ArrayList<>(), false)
      ),
      Arguments.of(
        helperFieldMap(null,              null,  null,  InputType.DATE, null,              null),
        helperFieldMap(new ArrayList<>(), "",    "",    InputType.DATE, new ArrayList<>(), false)
      ),
      Arguments.of(
        helperFieldMap(null,              null,  null,  null,           strList,           null),
        helperFieldMap(new ArrayList<>(), "",    "",    InputType.TEXT, strList,           false)
      ),
      Arguments.of(
        helperFieldMap(null,              null,  null,  null,           null,              true),
        helperFieldMap(new ArrayList<>(), "",    "",    InputType.TEXT, new ArrayList<>(), true)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param attributes The attributes value.
   * @param fieldId The fieldId value.
   * @param fieldLabel The fieldLabel value.
   * @param inputType The inputType value.
   * @param options The options value.
   * @param required The required value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(List<InputAttribute> attributes, String fieldId, String fieldLabel, InputType inputType, List<String> options, Boolean required) {
    final Map<String, Object> map = new HashMap<>();

    map.put("attributes", attributes);
    map.put("fieldId", fieldId);
    map.put("fieldLabel", fieldLabel);
    map.put("inputType", inputType);
    map.put("options", options);
    map.put("required", required);

    return map;
  }

}
