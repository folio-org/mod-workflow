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

  private static final String ATTRIBUTES   = "attributes";
  private static final String DEFAULTVALUE = "defaultValue";
  private static final String FIELDID      = "fieldId";
  private static final String FIELDLABEL   = "fieldLabel";
  private static final String INPUTTYPE    = "inputType";
  private static final String OPTIONS      = "options";
  private static final String REQUIRED     = "required";


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
    setField(embeddedInput, ATTRIBUTES, attributes);

    assertEquals(attributes, embeddedInput.getAttributes());
  }

  @Test
  void setAttributesWorksTest() {
    setField(embeddedInput, ATTRIBUTES, null);

    embeddedInput.setAttributes(attributes);
    assertEquals(attributes, getField(embeddedInput, ATTRIBUTES));
  }

  @Test
  void getDefaultValueWorksTest() {
    setField(embeddedInput, DEFAULTVALUE, VALUE);

    assertEquals(VALUE, embeddedInput.getDefaultValue());
  }

  @Test
  void setDefaultValueWorksTest() {
    setField(embeddedInput, DEFAULTVALUE, null);

    embeddedInput.setDefaultValue(VALUE);
    assertEquals(VALUE, getField(embeddedInput, DEFAULTVALUE));
  }

  @Test
  void getFieldIdWorksTest() {
    setField(embeddedInput, FIELDID, VALUE);

    assertEquals(VALUE, embeddedInput.getFieldId());
  }

  @Test
  void setFieldIdWorksTest() {
    setField(embeddedInput, FIELDID, null);

    embeddedInput.setFieldId(VALUE);
    assertEquals(VALUE, getField(embeddedInput, FIELDID));
  }

  @Test
  void getFieldLabelWorksTest() {
    setField(embeddedInput, FIELDLABEL, VALUE);

    assertEquals(VALUE, embeddedInput.getFieldLabel());
  }

  @Test
  void setFieldLabelWorksTest() {
    setField(embeddedInput, FIELDLABEL, null);

    embeddedInput.setFieldLabel(VALUE);
    assertEquals(VALUE, getField(embeddedInput, FIELDLABEL));
  }

  @Test
  void getInputTypeWorksTest() {
    setField(embeddedInput, INPUTTYPE, EMAIL);

    assertEquals(EMAIL, embeddedInput.getInputType());
  }

  @Test
  void setInputTypeWorksTest() {
    setField(embeddedInput, INPUTTYPE, null);

    embeddedInput.setInputType(CHECKBOX);
    assertEquals(CHECKBOX, getField(embeddedInput, INPUTTYPE));
  }

  @Test
  void getOptionsWorksTest() {
    setField(embeddedInput, OPTIONS, options);

    assertEquals(options, embeddedInput.getOptions());
  }

  @Test
  void setOptionsWorksTest() {
    setField(embeddedInput, OPTIONS, null);

    embeddedInput.setOptions(options);
    assertEquals(options, getField(embeddedInput, OPTIONS));
  }

  @Test
  void getRequiredWorksTest() {
    setField(embeddedInput, REQUIRED, true);

    assertEquals(true, embeddedInput.getRequired());
  }

  @Test
  void setRequiredWorksTest() {
    setField(embeddedInput, REQUIRED, false);

    embeddedInput.setRequired(true);
    assertEquals(true, getField(embeddedInput, REQUIRED));
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

    return List.of(
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
    ).stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
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

    map.put(ATTRIBUTES, attributes);
    map.put(FIELDID, fieldId);
    map.put(FIELDLABEL, fieldLabel);
    map.put(INPUTTYPE, inputType);
    map.put(OPTIONS, options);
    map.put(REQUIRED, required);

    return map;
  }

}
