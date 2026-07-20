import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProcessorTaskTest {

    @Test
    void testConstructor() {
        ProcessorTask processorTask = new ProcessorTask();
        assertNotNull(processorTask);
    }

    @Test
    void testInputVariablesNotNull() {
        ProcessorTask processorTask = new ProcessorTask();
        assertNotNull(processorTask.getInputVariables());
    }

    @Test
    void testOutputVariableNotNull() {
        ProcessorTask processorTask = new ProcessorTask();
        assertNotNull(processorTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeDefault() {
        ProcessorTask processorTask = new ProcessorTask();
        assertFalse(processorTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterDefault() {
        ProcessorTask processorTask = new ProcessorTask();
        assertFalse(processorTask.isAsyncAfter());
    }

    @Test
    void testProcessorNotNull() {
        ProcessorTask processorTask = new ProcessorTask();
        assertNotNull(processorTask.getProcessor());
    }
}
