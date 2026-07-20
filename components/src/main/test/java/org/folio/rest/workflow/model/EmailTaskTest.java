import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailTaskTest {

    private EmailTask emailTask;

    @BeforeEach
    void setUp() {
        emailTask = new EmailTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable("inputVar1", "value1"));
        inputVariables.add(new EmbeddedVariable("inputVar2", "value2"));
        emailTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, emailTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable("outputVar", "outputValue");
        emailTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, emailTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        emailTask.setAsyncBefore(true);
        assertTrue(emailTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        emailTask.setAsyncAfter(true);
        assertTrue(emailTask.isAsyncAfter());
    }

    @Test
    void testMailToGetterSetter() {
        String mailTo = "recipient@example.com";
        emailTask.setMailTo(mailTo);
        assertEquals(mailTo, emailTask.getMailTo());
    }

    @Test
    void testMailFromGetterSetter() {
        String mailFrom = "sender@example.com";
        emailTask.setMailFrom(mailFrom);
        assertEquals(mailFrom, emailTask.getMailFrom());
    }

    @Test
    void testMailSubjectGetterSetter() {
        String mailSubject = "Test Email Subject";
        emailTask.setMailSubject(mailSubject);
        assertEquals(mailSubject, emailTask.getMailSubject());
    }

    @Test
    void testMailTextGetterSetter() {
        String mailText = "This is a test email.";
        emailTask.setMailText(mailText);
        assertEquals(mailText, emailTask.getMailText());
    }
}
