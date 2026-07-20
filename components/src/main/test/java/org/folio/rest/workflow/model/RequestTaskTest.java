import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RequestTaskTest {

    @Test
    void testConstructor() {
        RequestTask requestTask = new RequestTask();
        assertNotNull(requestTask);
    }

    @Test
    void testAsyncBeforeDefault() {
        RequestTask requestTask = new RequestTask();
        assertFalse(requestTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterDefault() {
        RequestTask requestTask = new RequestTask();
        assertFalse(requestTask.isAsyncAfter());
    }

    @Test
    void testInputVariablesNotNull() {
        RequestTask requestTask = new RequestTask();
        assertNotNull(requestTask.getInputVariables());
    }

    @Test
    void testHeaderOutputVariablesNotNull() {
        RequestTask requestTask = new RequestTask();
        assertNotNull(requestTask.getHeaderOutputVariables());
    }

    @Test
    void testOutputVariableNotNull() {
        RequestTask requestTask = new RequestTask();
        assertNotNull(requestTask.getOutputVariable());
    }

    @Test
    void testRequestNotNull() {
        RequestTask requestTask = new RequestTask();
        assertNotNull(requestTask.getRequest());
    }
}
