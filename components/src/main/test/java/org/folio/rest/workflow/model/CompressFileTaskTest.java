import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompressFileTaskTest {

    private CompressFileTask compressFileTask;

    @BeforeEach
    void setUp() {
        compressFileTask = new CompressFileTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable("inputVar1", "value1"));
        inputVariables.add(new EmbeddedVariable("inputVar2", "value2"));
        compressFileTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, compressFileTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable("outputVar", "outputValue");
        compressFileTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, compressFileTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        compressFileTask.setAsyncBefore(true);
        assertTrue(compressFileTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        compressFileTask.setAsyncAfter(true);
        assertTrue(compressFileTask.isAsyncAfter());
    }

    @Test
    void testSourceGetterSetter() {
        compressFileTask.setSource("/path/to/source");
        assertEquals("/path/to/source", compressFileTask.getSource());
    }

    @Test
    void testDestinationGetterSetter() {
        compressFileTask.setDestination("/path/to/destination");
        assertEquals("/path/to/destination", compressFileTask.getDestination());
    }

    @Test
    void testFormatGetterSetter() {
        compressFileTask.setFormat(CompressFileFormat.ZIP);
        assertEquals(CompressFileFormat.ZIP, compressFileTask.getFormat());
    }

    @Test
    void testContainerGetterSetter() {
        compressFileTask.setContainer(CompressFileContainer.TAR);
        assertEquals(CompressFileContainer.TAR, compressFileTask.getContainer());
    }
}
