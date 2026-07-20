import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryTaskTest {

    private DirectoryTask directoryTask;

    @BeforeEach
    void setUp() {
        directoryTask = new DirectoryTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable("inputVar1", "value1"));
        inputVariables.add(new EmbeddedVariable("inputVar2", "value2"));
        directoryTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, directoryTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable("outputVar", "outputValue");
        directoryTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, directoryTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        directoryTask.setAsyncBefore(true);
        assertTrue(directoryTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        directoryTask.setAsyncAfter(true);
        assertTrue(directoryTask.isAsyncAfter());
    }

    @Test
    void testPathGetterSetter() {
        String path = "/path/to/directory";
        directoryTask.setPath(path);
        assertEquals(path, directoryTask.getPath());
    }

    @Test
    void testWorkflowGetterSetter() {
        String workflow = "workflow";
        directoryTask.setWorkflow(workflow);
        assertEquals(workflow, directoryTask.getWorkflow());
    }

    @Test
    void testActionGetterSetter() {
        DirectoryAction action = DirectoryAction.CREATE;
        directoryTask.setAction(action);
        assertEquals(action, directoryTask.getAction());
    }
}
