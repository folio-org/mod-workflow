import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoveToLastGatewayTest {

    @Test
    void testConstructor() {
        MoveToLastGateway moveToLastGateway = new MoveToLastGateway();
        assertNotNull(moveToLastGateway);
    }

    @Test
    void testDirectionInheritedFromAbstractGateway() {
        MoveToLastGateway moveToLastGateway = new MoveToLastGateway();
        assertEquals(Direction.UNSPECIFIED, moveToLastGateway.getDirection());
    }

    @Test
    void testNodesInheritedFromAbstractGateway() {
        MoveToLastGateway moveToLastGateway = new MoveToLastGateway();
        assertNotNull(moveToLastGateway.getNodes());
    }

    @Test
    void testNodesOrderColumnAnnotation() {
        assertTrue(MoveToLastGateway.class.isAnnotationPresent(OrderColumn.class));
    }
}
