import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParallelGatewayTest {

    @Test
    void testConstructor() {
        ParallelGateway parallelGateway = new ParallelGateway();
        assertNotNull(parallelGateway);
    }

    @Test
    void testDirectionInheritedFromAbstractGateway() {
        ParallelGateway parallelGateway = new ParallelGateway();
        assertEquals(Direction.UNSPECIFIED, parallelGateway.getDirection());
    }

    @Test
    void testNodesInheritedFromAbstractGateway() {
        ParallelGateway parallelGateway = new ParallelGateway();
        assertNotNull(parallelGateway.getNodes());
    }

    @Test
    void testNodesOrderColumnAnnotation() {
        assertTrue(ParallelGateway.class.isAnnotationPresent(OrderColumn.class));
    }
}
