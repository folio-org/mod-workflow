import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StartEventTest {

    @Test
    void testConstructor() {
        StartEvent startEvent = new StartEvent();
        assertNotNull(startEvent);
    }

    @Test
    void testTypeGetterSetter() {
        StartEvent startEvent = new StartEvent();
        startEvent.setType(StartEventType.MESSAGE);
        assertEquals(StartEventType.MESSAGE, startEvent.getType());
    }

    @Test
    void testExpressionGetterSetter() {
        StartEvent startEvent = new StartEvent();
        String expression = "some expression";
        startEvent.setExpression(expression);
        assertEquals(expression, startEvent.getExpression());
    }

    @Test
    void testInterruptingGetterSetter() {
        StartEvent startEvent = new StartEvent();
        startEvent.setInterrupting(true);
        assertTrue(startEvent.isInterrupting());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        StartEvent startEvent = new StartEvent();
        startEvent.setAsyncBefore(true);
        assertTrue(startEvent.isAsyncBefore());
    }
}
