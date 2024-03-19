import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    void testConstructor() {
        Node node = new NodeStub();
        assertNotNull(node);
    }

    @Test
    void testNameGetterSetter() {
        String name = "TestNode";
        Node node = new NodeStub();
        node.setName(name);
        assertEquals(name, node.getName());
    }

    @Test
    void testDescriptionGetterSetter() {
        String description = "Test description";
        Node node = new NodeStub();
        node.setDescription(description);
        assertEquals(description, node.getDescription());
    }

    @Test
    void testDeserializeAsDefaultValue() {
        Node node = new NodeStub();
        assertEquals("NodeStub", node.getDeserializeAs());
    }

}

class NodeStub extends Node {
}
