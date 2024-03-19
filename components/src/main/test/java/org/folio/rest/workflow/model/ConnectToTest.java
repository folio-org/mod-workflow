import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConnectToTest {

    private ConnectTo connectTo;
    private Validator validator;

    @BeforeEach
    void setUp() {
        connectTo = new ConnectTo();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNodeIdGetterSetter() {
        String nodeId = "nodeId";
        connectTo.setNodeId(nodeId);
        assertEquals(nodeId, connectTo.getNodeId());
    }

    @Test
    void testNotNullConstraint() {
        assertThrows(Exception.class, () -> {
            connectTo.setNodeId(null);
            validator.validateProperty(connectTo, "nodeId");
        });
    }
}
