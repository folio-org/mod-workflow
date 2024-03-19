import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReceiveTaskTest {

    @Test
    void testConstructor() {
        ReceiveTask receiveTask = new ReceiveTask();
        assertNotNull(receiveTask);
    }

    @Test
    void testMessageNotNull() {
        ReceiveTask receiveTask = new ReceiveTask();
        assertNotNull(receiveTask.getMessage());
    }

    @Test
    void testMessageSize() {
        ReceiveTask receiveTask = new ReceiveTask();
        String message = "Test message";
        receiveTask.setMessage(message);
        assertEquals(message, receiveTask.getMessage());
    }

    @Test
    void testAsyncBeforeDefault() {
        ReceiveTask receiveTask = new ReceiveTask();
        assertFalse(receiveTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterDefault() {
        ReceiveTask receiveTask = new ReceiveTask();
        assertFalse(receiveTask.isAsyncAfter());
    }
}
