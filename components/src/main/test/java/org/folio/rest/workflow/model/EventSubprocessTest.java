import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EventSubprocessTest {

    private EventSubprocess eventSubprocess;

    @BeforeEach
    void setUp() {
        eventSubprocess = new EventSubprocess();
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        eventSubprocess.setAsyncBefore(true);
        assertTrue(eventSubprocess.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        eventSubprocess.setAsyncAfter(true);
        assertTrue(eventSubprocess.isAsyncAfter());
    }

    @Test
    void testNodesGetterSetter() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new StartEvent());
        nodes.add(new EndEvent());
        eventSubprocess.setNodes(nodes);
        assertEquals(nodes, eventSubprocess.getNodes());
    }

    @Test
    void testNodesOrder() {
        StartEvent startEvent = new StartEvent();
        EndEvent endEvent = new EndEvent();
        List<Node> nodes = new ArrayList<>();
        nodes.add(endEvent);
        nodes.add(startEvent);
        eventSubprocess.setNodes(nodes);
        assertEquals(startEvent, eventSubprocess.getNodes().get(1));
        assertEquals(endEvent, eventSubprocess.getNodes().get(0));
    }

    @Test
    void testNodesOrderColumnAnnotation() {
        assertTrue(eventSubprocess.getClass().isAnnotationPresent(OrderColumn.class));
    }
}
