import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.folio.rest.workflow.model.AbstractGateway;
import org.folio.rest.workflow.enums.Direction;
import org.folio.rest.workflow.model.Node;

public class AbstractGatewayTest {

    private AbstractGateway abstractGateway;

    @BeforeEach
    void setUp() {
        abstractGateway = new AbstractGateway();
    }

    @Test
    void testConstructor() {
        assertEquals(Direction.UNSPECIFIED, abstractGateway.getDirection());
        assertNotNull(abstractGateway.getNodes());
    }

    @Test
    void testDirectionGetterSetter() {
        abstractGateway.setDirection(Direction.IN);
        assertEquals(Direction.IN, abstractGateway.getDirection());
    }

    @Test
    void testNodesGetterSetter() {
        Node node = new Node(); 
        abstractGateway.getNodes().add(node);
        assertEquals(1, abstractGateway.getNodes().size());
    }

}
