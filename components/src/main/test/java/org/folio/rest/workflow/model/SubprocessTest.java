import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SubprocessTest {

    @Test
    void testConstructor() {
        Subprocess subprocess = new Subprocess();
        assertNotNull(subprocess);
    }

    @Test
    void testTypeGetterSetter() {
        Subprocess subprocess = new Subprocess();
        subprocess.setType(SubprocessType.EVENT);
        assertEquals(SubprocessType.EVENT, subprocess.getType());
    }

    @Test
    void testNodesGetterSetter() {
        Subprocess subprocess = new Subprocess();
        Node node = new Node();
        subprocess.getNodes().add(node);
        assertEquals(1, subprocess.getNodes().size());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        Subprocess subprocess = new Subprocess();
        subprocess.setAsyncBefore(true);
        assertTrue(subprocess.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        Subprocess subprocess = new Subprocess();
        subprocess.setAsyncAfter(true);
        assertTrue(subprocess.isAsyncAfter());
    }

    @Test
    void testLoopRefGetterSetter() {
        Subprocess subprocess = new Subprocess();
        EmbeddedLoopReference loopRef = new EmbeddedLoopReference();
        subprocess.setLoopRef(loopRef);
        assertNotNull(subprocess.getLoopRef());
    }
}
