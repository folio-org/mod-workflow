package org.folio.rest.workflow.model;

import static org.folio.rest.workflow.enums.InputAttribute.MAX;
import static org.folio.rest.workflow.enums.InputType.CHECKBOX;
import static org.folio.rest.workflow.enums.InputType.DATE;
import static org.folio.rest.workflow.enums.InputType.EMAIL;
import static org.folio.rest.workflow.enums.InputType.TEXT;
import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
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
    setField(embeddedInput, "inputType", EMAIL);

    assertEquals(EMAIL, embeddedInput.getInputType());
  }

  @Test
  void setInputTypeWorksTest() {
    setField(embeddedInput, "inputType", null);

    embeddedInput.setInputType(CHECKBOX);
    assertEquals(CHECKBOX, getField(embeddedInput, "inputType"));
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

    final List<String> emptyList = new ArrayList<>();

    final ArrayList<InputAttribute> attrList = new ArrayList<>();
    attrList.add(MAX);

    final ArrayList<InputAttribute> emptyAttrList = new ArrayList<>();

    return Stream.of(
      Arguments.of(
        helperFieldMap(null,          NULL_STR, NULL_STR, null, null,      null),
        helperFieldMap(emptyAttrList, "",       "",       TEXT, emptyList, false)
      ),
      Arguments.of(
        helperFieldMap(attrList,      NULL_STR, NULL_STR, null, null,      null),
        helperFieldMap(attrList,      "",       "",       TEXT, emptyList, false)
      ),
      Arguments.of(
        helperFieldMap(null,          VALUE,    NULL_STR, null, null,      null),
        helperFieldMap(emptyAttrList, VALUE,    "",       TEXT, emptyList, false)
      ),
      Arguments.of(
        helperFieldMap(null,          NULL_STR, VALUE,    null, null,      null),
        helperFieldMap(emptyAttrList, "",       VALUE,    TEXT, emptyList, false)
      ),
      Arguments.of(
        helperFieldMap(null,          NULL_STR, NULL_STR, DATE, null,      null),
        helperFieldMap(emptyAttrList, "",       "",       DATE, emptyList, false)
      ),
      Arguments.of(
        helperFieldMap(null,          NULL_STR, NULL_STR, null, strList,   null),
        helperFieldMap(emptyAttrList, "",       "",       TEXT, strList,   false)
      ),
      Arguments.of(
        helperFieldMap(null,          NULL_STR, NULL_STR, null, null,      true),
        helperFieldMap(emptyAttrList, "",       "",       TEXT, emptyList, true)
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
