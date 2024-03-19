import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class FileTaskTest {

    private FileTask fileTask;

    @BeforeEach
    void setUp() {
        fileTask = new FileTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable());
        fileTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, fileTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable();
        fileTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, fileTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        fileTask.setAsyncBefore(true);
        assertTrue(fileTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        fileTask.setAsyncAfter(true);
        assertTrue(fileTask.isAsyncAfter());
    }

    @Test
    void testOpGetterSetter() {
        FileOp op = FileOp.CREATE;
        fileTask.setOp(op);
        assertEquals(op, fileTask.getOp());
    }

    @Test
    void testPathGetterSetter() {
        String path = "/path/to/file";
        fileTask.setPath(path);
        assertEquals(path, fileTask.getPath());
    }

    @Test
    void testTargetGetterSetter() {
        String target = "/path/to/target";
        fileTask.setTarget(target);
        assertEquals(target, fileTask.getTarget());
    }

    @Test
    void testLineGetterSetter() {
        String line = "Some line";
        fileTask.setLine(line);
        assertEquals(line, fileTask.getLine());
    }
}
