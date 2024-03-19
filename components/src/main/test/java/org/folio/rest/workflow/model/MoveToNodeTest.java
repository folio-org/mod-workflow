import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MoveToNodeTest {

    @Test
    void testConstructor() {
        MoveToNode moveToNode = new MoveToNode();
        assertNotNull(moveToNode);
    }

    @Test
    void testGatewayIdGetterSetter() {
        String gatewayId = "gateway123";
        MoveToNode moveToNode = new MoveToNode();
        moveToNode.setGatewayId(gatewayId);
        assertEquals(gatewayId, moveToNode.getGatewayId());
    }

    @Test
    void testNodesNotNull() {
        MoveToNode moveToNode = new MoveToNode();
        assertNotNull(moveToNode.getNodes());
    }

    @Test
    void testNodesOrderColumnAnnotation() {
        assertTrue(MoveToNode.class.isAnnotationPresent(OrderColumn.class));
    }
}
