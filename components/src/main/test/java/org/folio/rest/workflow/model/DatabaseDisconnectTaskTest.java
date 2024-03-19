import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DatabaseDisconnectTaskTest {

    private DatabaseDisconnectTask databaseDisconnectTask;

    @BeforeEach
    void setUp() {
        databaseDisconnectTask = new DatabaseDisconnectTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable("inputVar1", "value1"));
        inputVariables.add(new EmbeddedVariable("inputVar2", "value2"));
        databaseDisconnectTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, databaseDisconnectTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable("outputVar", "outputValue");
        databaseDisconnectTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, databaseDisconnectTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        databaseDisconnectTask.setAsyncBefore(true);
        assertEquals(true, databaseDisconnectTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        databaseDisconnectTask.setAsyncAfter(true);
        assertEquals(true, databaseDisconnectTask.isAsyncAfter());
    }

    @Test
    void testDesignationGetterSetter() {
        String designation = "designation";
        databaseDisconnectTask.setDesignation(designation);
        assertEquals(designation, databaseDisconnectTask.getDesignation());
    }
}
