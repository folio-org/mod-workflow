import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExclusiveGatewayTest {

    @Test
    void testConstructor() {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        assertNotNull(exclusiveGateway);
    }

    @Test
    void testDirectionInheritedFromAbstractGateway() {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        assertEquals(Direction.UNSPECIFIED, exclusiveGateway.getDirection());
    }

    @Test
    void testNodesInheritedFromAbstractGateway() {
        ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
        assertNotNull(exclusiveGateway.getNodes());
    }

    @Test
    void testNodesOrderColumnAnnotation() {
        assertTrue(ExclusiveGateway.class.isAnnotationPresent(OrderColumn.class));
    }
}
