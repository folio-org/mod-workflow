package org.folio.rest.workflow.model;

import static org.folio.spring.test.mock.MockMvcConstant.NULL_STR;
import static org.folio.spring.test.mock.MockMvcConstant.VALUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.ArrayList;
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
class EmailTaskTest {

  private static final String ASYNCAFTER        = "asyncAfter";
  private static final String ASYNCBEFORE       = "asyncBefore";
  private static final String ATTACHMENTPATH    = "attachmentPath";
  private static final String DESCRIPTION       = "description";
  private static final String DESERIALIZEAS     = "deserializeAs";
  private static final String INCLUDEATTACHMENT = "includeAttachment";
  private static final String ID                = "id";
  private static final String INPUTVARIABLES    = "inputVariables";
  private static final String MAILFROM          = "mailFrom";
  private static final String MAILMARKUP        = "mailMarkup";
  private static final String MAILSUBJECT       = "mailSubject";
  private static final String MAILTEXT          = "mailText";
  private static final String MAILTO            = "mailTo";
  private static final String MAILCC            = "mailCc";
  private static final String MAILBCC           = "mailBcc";
  private static final String NAME              = "name";
  private static final String OUTPUTVARIABLE    = "outputVariable";

  @Mock
  private EmbeddedVariable embeddedVariable;

  private Set<EmbeddedVariable> inputVariables;

  private EmailTask emailTask;

  @BeforeEach
  void beforeEach() {

    emailTask = new EmailTask();
    inputVariables = new HashSet<>();
    inputVariables.add(embeddedVariable);
  }

  @Test
  void getIdWorksTest() {
    setField(emailTask, ID, VALUE);

    assertEquals(VALUE, emailTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(emailTask, ID, null);

    emailTask.setId(VALUE);
    assertEquals(VALUE, getField(emailTask, ID));
  }

  @Test
  void getNameWorksTest() {
    setField(emailTask, NAME, VALUE);

    assertEquals(VALUE, emailTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(emailTask, NAME, null);

    emailTask.setName(VALUE);
    assertEquals(VALUE, getField(emailTask, NAME));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(emailTask, DESCRIPTION, VALUE);

    assertEquals(VALUE, emailTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(emailTask, DESCRIPTION, null);

    emailTask.setDescription(VALUE);
    assertEquals(VALUE, getField(emailTask, DESCRIPTION));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(emailTask, DESERIALIZEAS, VALUE);

    assertEquals(VALUE, emailTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(emailTask, DESERIALIZEAS, null);

    emailTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(emailTask, DESERIALIZEAS));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(emailTask, INPUTVARIABLES, inputVariables);

    assertEquals(inputVariables, emailTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(emailTask, INPUTVARIABLES, null);

    emailTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(emailTask, INPUTVARIABLES));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(emailTask, OUTPUTVARIABLE, embeddedVariable);

    assertEquals(embeddedVariable, emailTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(emailTask, OUTPUTVARIABLE, null);

    emailTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(emailTask, OUTPUTVARIABLE));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(emailTask, ASYNCBEFORE, true);

    assertEquals(true, emailTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(emailTask, ASYNCBEFORE, false);

    emailTask.setAsyncBefore(true);
    assertEquals(true, getField(emailTask, ASYNCBEFORE));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(emailTask, ASYNCAFTER, true);

    assertEquals(true, emailTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(emailTask, ASYNCAFTER, false);

    emailTask.setAsyncAfter(true);
    assertEquals(true, getField(emailTask, ASYNCAFTER));
  }

  @Test
  void getMailToWorksTest() {
    setField(emailTask, MAILTO, VALUE);

    assertEquals(VALUE, emailTask.getMailTo());
  }

  @Test
  void setMailToWorksTest() {
    setField(emailTask, MAILTO, null);

    emailTask.setMailTo(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILTO));
  }

  @Test
  void getMailCcWorksTest() {
    setField(emailTask, MAILCC, VALUE);

    assertEquals(VALUE, emailTask.getMailCc());
  }

  @Test
  void setMailCcWorksTest() {
    setField(emailTask, MAILCC, null);

    emailTask.setMailCc(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILCC));
  }

  @Test
  void getMailBccWorksTest() {
    setField(emailTask, MAILBCC, VALUE);

    assertEquals(VALUE, emailTask.getMailBcc());
  }

  @Test
  void setMailBccWorksTest() {
    setField(emailTask, MAILBCC, null);

    emailTask.setMailBcc(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILBCC));
  }

  @Test
  void getMailFromWorksTest() {
    setField(emailTask, MAILFROM, VALUE);

    assertEquals(VALUE, emailTask.getMailFrom());
  }

  @Test
  void setMailFromWorksTest() {
    setField(emailTask, MAILFROM, null);

    emailTask.setMailFrom(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILFROM));
  }

  @Test
  void getMailSubjectWorksTest() {
    setField(emailTask, MAILSUBJECT, VALUE);

    assertEquals(VALUE, emailTask.getMailSubject());
  }

  @Test
  void setMailSubjectWorksTest() {
    setField(emailTask, MAILSUBJECT, null);

    emailTask.setMailSubject(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILSUBJECT));
  }

  @Test
  void getMailTextWorksTest() {
    setField(emailTask, MAILTEXT, VALUE);

    assertEquals(VALUE, emailTask.getMailText());
  }

  @Test
  void setMailTextWorksTest() {
    setField(emailTask, MAILTEXT, null);

    emailTask.setMailText(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILTEXT));
  }

  @Test
  void getMailMarkupWorksTest() {
    setField(emailTask, MAILMARKUP, VALUE);

    assertEquals(VALUE, emailTask.getMailMarkup());
  }

  @Test
  void setMailMarkupWorksTest() {
    setField(emailTask, MAILMARKUP, null);

    emailTask.setMailMarkup(VALUE);
    assertEquals(VALUE, getField(emailTask, MAILMARKUP));
  }

  @Test
  void getAttachmentPathWorksTest() {
    setField(emailTask, ATTACHMENTPATH, VALUE);

    assertEquals(VALUE, emailTask.getAttachmentPath());
  }

  @Test
  void setAttachmentPathWorksTest() {
    setField(emailTask, ATTACHMENTPATH, null);

    emailTask.setAttachmentPath(VALUE);
    assertEquals(VALUE, getField(emailTask, ATTACHMENTPATH));
  }

  @Test
  void getIncludeAttachmentWorksTest() {
    setField(emailTask, INCLUDEATTACHMENT, VALUE);

    assertEquals(VALUE, emailTask.getIncludeAttachment());
  }

  @Test
  void setIncludeAttachmentWorksTest() {
    setField(emailTask, INCLUDEATTACHMENT, null);

    emailTask.setIncludeAttachment(VALUE);
    assertEquals(VALUE, getField(emailTask, INCLUDEATTACHMENT));
  }

  @ParameterizedTest
  @MethodSource("providePrePersistFor")
  void prePersistWorksTest(Map<String, Object> initial, Map<String, Object> expected) {
    initial.forEach((String attribute, Object value) -> {
      setField(emailTask, attribute, value);
    });

    emailTask.prePersist();

    expected.forEach((String attribute, Object value) -> {
      assertEquals(value, getField(emailTask, attribute));
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

    final List<Arguments> arguments = new ArrayList<>();

    final String defaultIA = EmailTask.DEFAULT_INCLUDE_ATTACHMENT;

    arguments.add(Arguments.of(
      helperFieldMap(NULL_STR,  NULL_STR, NULL_STR, NULL_STR, NULL_STR),
      helperFieldMap(defaultIA, "",       "",       "",       "")
    ));

    arguments.add(Arguments.of(
      helperFieldMap(VALUE,     NULL_STR, NULL_STR, NULL_STR, NULL_STR),
      helperFieldMap(VALUE,     "",       "",       "",       "")
    ));

    arguments.add(Arguments.of(
      helperFieldMap(NULL_STR,  VALUE,    NULL_STR, NULL_STR, NULL_STR),
      helperFieldMap(defaultIA, VALUE,    "",       "",       "")
    ));

    arguments.add(Arguments.of(
      helperFieldMap(NULL_STR,  NULL_STR, VALUE,    NULL_STR, NULL_STR),
      helperFieldMap(defaultIA, "",       VALUE,    "",       "")
    ));

    arguments.add(Arguments.of(
      helperFieldMap(NULL_STR,  NULL_STR, NULL_STR, VALUE,    NULL_STR),
      helperFieldMap(defaultIA, "",       "",       VALUE,    "")
    ));

    arguments.add(Arguments.of(
      helperFieldMap(NULL_STR,  NULL_STR, NULL_STR, NULL_STR, VALUE),
      helperFieldMap(defaultIA, "",       "",       "",       VALUE)
    ));

    return arguments.stream();
  }

  /**
   * Helper for reducing in line code repetition for assignments.
   *
   * @param includeAttachment The includeAttachment value.
   * @param mailFrom          The mailFrom value.
   * @param mailText          The mailText value.
   * @param mailTo            The mailTo value.
   * @param mailSubject       The mailSubject value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(
    String includeAttachment, String mailFrom, String mailText, String mailTo, String mailSubject
  ) {

    final Map<String, Object> map = new HashMap<>();

    map.put(INCLUDEATTACHMENT, includeAttachment);
    map.put(MAILFROM, mailFrom);
    map.put(MAILTEXT, mailText);
    map.put(MAILTO, mailTo);
    map.put(MAILSUBJECT, mailSubject);

    return map;
  }

}
