import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InclusiveGatewayTest {

    @Test
    void testConstructor() {
        InclusiveGateway inclusiveGateway = new InclusiveGateway();
        assertNotNull(inclusiveGateway);
    }

    @Test
    void testDirectionInheritedFromAbstractGateway() {
        InclusiveGateway inclusiveGateway = new InclusiveGateway();
        assertEquals(Direction.UNSPECIFIED, inclusiveGateway.getDirection());
    }

    @Test
    void testNodesInheritedFromAbstractGateway() {
        InclusiveGateway inclusiveGateway = new InclusiveGateway();
        assertNotNull(inclusiveGateway.getNodes());
    }

    @Test
    void testNodesOrderColumnAnnotation() {
        assertTrue(InclusiveGateway.class.isAnnotationPresent(OrderColumn.class));
    }
}
