import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DatabaseConnectionTaskTest {

    private DatabaseConnectionTask databaseConnectionTask;

    @BeforeEach
    void setUp() {
        databaseConnectionTask = new DatabaseConnectionTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable("inputVar1", "value1"));
        inputVariables.add(new EmbeddedVariable("inputVar2", "value2"));
        databaseConnectionTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, databaseConnectionTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable("outputVar", "outputValue");
        databaseConnectionTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, databaseConnectionTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        databaseConnectionTask.setAsyncBefore(true);
        assertEquals(true, databaseConnectionTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        databaseConnectionTask.setAsyncAfter(true);
        assertEquals(true, databaseConnectionTask.isAsyncAfter());
    }

    @Test
    void testDesignationGetterSetter() {
        String designation = "designation";
        databaseConnectionTask.setDesignation(designation);
        assertEquals(designation, databaseConnectionTask.getDesignation());
    }

    @Test
    void testUrlGetterSetter() {
        String url = "url";
        databaseConnectionTask.setUrl(url);
        assertEquals(url, databaseConnectionTask.getUrl());
    }

    @Test
    void testUsernameGetterSetter() {
        String username = "username";
        databaseConnectionTask.setUsername(username);
        assertEquals(username, databaseConnectionTask.getUsername());
    }

    @Test
    void testPasswordGetterSetter() {
        String password = "password";
        databaseConnectionTask.setPassword(password);
        assertEquals(password, databaseConnectionTask.getPassword());
    }
}
