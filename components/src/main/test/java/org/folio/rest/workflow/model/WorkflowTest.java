import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class WorkflowTest {

    @Test
    void testConstructor() {
        Workflow workflow = new Workflow();
        assertNotNull(workflow);
        assertFalse(workflow.isActive());
        assertEquals(0, workflow.getNodes().size());
        assertEquals(0, workflow.getInitialContext().size());
        assertEquals(0, workflow.getHistoryTimeToLive());
    }

    @Test
    void testNameGetterSetter() {
        Workflow workflow = new Workflow();
        workflow.setName("Test Workflow");
        assertEquals("Test Workflow", workflow.getName());
    }

    @Test
    void testDescriptionGetterSetter() {
        Workflow workflow = new Workflow();
        workflow.setDescription("Test Description");
        assertEquals("Test Description", workflow.getDescription());
    }

    @Test
    void testVersionTagGetterSetter() {
        Workflow workflow = new Workflow();
        workflow.setVersionTag("v1.0");
        assertEquals("v1.0", workflow.getVersionTag());
    }

    @Test
    void testActiveGetterSetter() {
        Workflow workflow = new Workflow();
        workflow.setActive(true);
        assertTrue(workflow.isActive());
    }

    @Test
    void testDeploymentIdGetterSetter() {
        Workflow workflow = new Workflow();
        workflow.setDeploymentId("12345");
        assertEquals("12345", workflow.getDeploymentId());
    }

    @Test
    void testSetupGetterSetter() {
        Workflow workflow = new Workflow();
        Setup setup = new Setup();
        workflow.setSetup(setup);
        assertNotNull(workflow.getSetup());
    }

    @Test
    void testNodesGetterSetter() {
        Workflow workflow = new Workflow();
        Node node = new Node();
        workflow.getNodes().add(node);
        assertEquals(1, workflow.getNodes().size());
    }

    @Test
    void testInitialContextGetterSetter() {
        Workflow workflow = new Workflow();
        Map<String, JsonNode> initialContext = new HashMap<>();
        initialContext.put("key", null); // Example entry
        workflow.setInitialContext(initialContext);
        assertEquals(1, workflow.getInitialContext().size());
    }

    @Test
    void testHistoryTimeToLiveGetterSetter() {
        Workflow workflow = new Workflow();
        workflow.setHistoryTimeToLive(10);
        assertEquals(10, workflow.getHistoryTimeToLive());
    }
}
