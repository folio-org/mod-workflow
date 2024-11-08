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
class EmailTaskTest {

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
    setField(emailTask, "id", VALUE);

    assertEquals(VALUE, emailTask.getId());
  }

  @Test
  void setIdWorksTest() {
    setField(emailTask, "id", null);

    emailTask.setId(VALUE);
    assertEquals(VALUE, getField(emailTask, "id"));
  }

  @Test
  void getNameWorksTest() {
    setField(emailTask, "name", VALUE);

    assertEquals(VALUE, emailTask.getName());
  }

  @Test
  void setNameWorksTest() {
    setField(emailTask, "name", null);

    emailTask.setName(VALUE);
    assertEquals(VALUE, getField(emailTask, "name"));
  }

  @Test
  void getDescriptionWorksTest() {
    setField(emailTask, "description", VALUE);

    assertEquals(VALUE, emailTask.getDescription());
  }

  @Test
  void setDescriptionWorksTest() {
    setField(emailTask, "description", null);

    emailTask.setDescription(VALUE);
    assertEquals(VALUE, getField(emailTask, "description"));
  }

  @Test
  void getDeserializeAsWorksTest() {
    setField(emailTask, "deserializeAs", VALUE);

    assertEquals(VALUE, emailTask.getDeserializeAs());
  }

  @Test
  void setDeserializeAsWorksTest() {
    setField(emailTask, "deserializeAs", null);

    emailTask.setDeserializeAs(VALUE);
    assertEquals(VALUE, getField(emailTask, "deserializeAs"));
  }

  @Test
  void getInputVariablesWorksTest() {
    setField(emailTask, "inputVariables", inputVariables);

    assertEquals(inputVariables, emailTask.getInputVariables());
  }

  @Test
  void setInputVariablesWorksTest() {
    setField(emailTask, "inputVariables", null);

    emailTask.setInputVariables(inputVariables);
    assertEquals(inputVariables, getField(emailTask, "inputVariables"));
  }

  @Test
  void getOutputVariableWorksTest() {
    setField(emailTask, "outputVariable", embeddedVariable);

    assertEquals(embeddedVariable, emailTask.getOutputVariable());
  }

  @Test
  void setOutputVariableWorksTest() {
    setField(emailTask, "outputVariable", null);

    emailTask.setOutputVariable(embeddedVariable);
    assertEquals(embeddedVariable, getField(emailTask, "outputVariable"));
  }

  @Test
  void getAsyncBeforeWorksTest() {
    setField(emailTask, "asyncBefore", true);

    assertEquals(true, emailTask.getAsyncBefore());
  }

  @Test
  void setAsyncBeforeWorksTest() {
    setField(emailTask, "asyncBefore", false);

    emailTask.setAsyncBefore(true);
    assertEquals(true, getField(emailTask, "asyncBefore"));
  }

  @Test
  void getAsyncAfterWorksTest() {
    setField(emailTask, "asyncAfter", true);

    assertEquals(true, emailTask.getAsyncAfter());
  }

  @Test
  void setAsyncAfterWorksTest() {
    setField(emailTask, "asyncAfter", false);

    emailTask.setAsyncAfter(true);
    assertEquals(true, getField(emailTask, "asyncAfter"));
  }

  @Test
  void getMailToWorksTest() {
    setField(emailTask, "mailTo", VALUE);

    assertEquals(VALUE, emailTask.getMailTo());
  }

  @Test
  void setMailToWorksTest() {
    setField(emailTask, "mailTo", null);

    emailTask.setMailTo(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailTo"));
  }

  @Test
  void getMailCcWorksTest() {
    setField(emailTask, "mailCc", VALUE);

    assertEquals(VALUE, emailTask.getMailCc());
  }

  @Test
  void setMailCcWorksTest() {
    setField(emailTask, "mailCc", null);

    emailTask.setMailCc(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailCc"));
  }

  @Test
  void getMailBccWorksTest() {
    setField(emailTask, "mailBcc", VALUE);

    assertEquals(VALUE, emailTask.getMailBcc());
  }

  @Test
  void setMailBccWorksTest() {
    setField(emailTask, "mailBcc", null);

    emailTask.setMailBcc(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailBcc"));
  }

  @Test
  void getMailFromWorksTest() {
    setField(emailTask, "mailFrom", VALUE);

    assertEquals(VALUE, emailTask.getMailFrom());
  }

  @Test
  void setMailFromWorksTest() {
    setField(emailTask, "mailFrom", null);

    emailTask.setMailFrom(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailFrom"));
  }

  @Test
  void getMailSubjectWorksTest() {
    setField(emailTask, "mailSubject", VALUE);

    assertEquals(VALUE, emailTask.getMailSubject());
  }

  @Test
  void setMailSubjectWorksTest() {
    setField(emailTask, "mailSubject", null);

    emailTask.setMailSubject(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailSubject"));
  }

  @Test
  void getMailTextWorksTest() {
    setField(emailTask, "mailText", VALUE);

    assertEquals(VALUE, emailTask.getMailText());
  }

  @Test
  void setMailTextWorksTest() {
    setField(emailTask, "mailText", null);

    emailTask.setMailText(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailText"));
  }

  @Test
  void getMailMarkupWorksTest() {
    setField(emailTask, "mailMarkup", VALUE);

    assertEquals(VALUE, emailTask.getMailMarkup());
  }

  @Test
  void setMailMarkupWorksTest() {
    setField(emailTask, "mailMarkup", null);

    emailTask.setMailMarkup(VALUE);
    assertEquals(VALUE, getField(emailTask, "mailMarkup"));
  }

  @Test
  void getAttachmentPathWorksTest() {
    setField(emailTask, "attachmentPath", VALUE);

    assertEquals(VALUE, emailTask.getAttachmentPath());
  }

  @Test
  void setAttachmentPathWorksTest() {
    setField(emailTask, "attachmentPath", null);

    emailTask.setAttachmentPath(VALUE);
    assertEquals(VALUE, getField(emailTask, "attachmentPath"));
  }

  @Test
  void getIncludeAttachmentWorksTest() {
    setField(emailTask, "includeAttachment", VALUE);

    assertEquals(VALUE, emailTask.getIncludeAttachment());
  }

  @Test
  void setIncludeAttachmentWorksTest() {
    setField(emailTask, "includeAttachment", null);

    emailTask.setIncludeAttachment(VALUE);
    assertEquals(VALUE, getField(emailTask, "includeAttachment"));
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
    final String mailFrom = "mailFromValue";
    final String mailText = "mailTextValue";
    final String mailTo = "mailToValue";
    final String mailSubject = "mailSubjectValue";

    return Stream.of(
      Arguments.of(
        helperFieldMap(null, null, null, null),
        helperFieldMap("", "", "", "")
      ),
      Arguments.of(
        helperFieldMap(null, mailText, mailTo, mailSubject),
        helperFieldMap("", mailText, mailTo, mailSubject)
      ),
      Arguments.of(
        helperFieldMap(mailFrom, null, mailTo, mailSubject),
        helperFieldMap(mailFrom, "", mailTo, mailSubject)
      ),
      Arguments.of(
        helperFieldMap(mailFrom, mailText, null, mailSubject),
        helperFieldMap(mailFrom, mailText, "", mailSubject)
      ),
      Arguments.of(
        helperFieldMap(null, mailText, mailTo, null),
        helperFieldMap("", mailText, mailTo, "")
      ),
      Arguments.of(
        helperFieldMap(mailFrom, null, null, mailSubject),
        helperFieldMap(mailFrom, "", "", mailSubject)
      ),
      Arguments.of(
        helperFieldMap(mailFrom, mailText, mailTo, null),
        helperFieldMap(mailFrom, mailText, mailTo, "")
      ),
      Arguments.of(
        helperFieldMap(mailFrom, mailText, mailTo, mailSubject),
        helperFieldMap(mailFrom, mailText, mailTo, mailSubject)
      )
    );
  }

  /**
   * Helper for reducing inline code repititon for assignments.
   *
   * @param mailFrom The mailFrom value.
   * @param mailText The mailText value.
   * @param mailTo The mailTo value.
   * @param mailSubject The mailSubject value.
   *
   * @return The built arguments map.
   */
  private static Map<String, Object> helperFieldMap(String mailFrom, String mailText, String mailTo, String mailSubject) {
    final Map<String, Object> map = new HashMap<>();

    map.put("mailFrom", mailFrom);
    map.put("mailText", mailText);
    map.put("mailTo", mailTo);
    map.put("mailSubject", mailSubject);

    return map;
  }

}
