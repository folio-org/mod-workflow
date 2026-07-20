import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SetupTest {

    @Test
    void testConstructor() {
        Setup setup = new Setup();
        assertNotNull(setup);
    }

    @Test
    void testAsyncBeforeDefault() {
        Setup setup = new Setup();
        assertFalse(setup.isAsyncBefore());
    }

    @Test
    void testAsyncAfterDefault() {
        Setup setup = new Setup();
        assertFalse(setup.isAsyncAfter());
    }

    @Test
    void testSetAsyncBefore() {
        Setup setup = new Setup();
        setup.setAsyncBefore(true);
        assertTrue(setup.isAsyncBefore());
    }

    @Test
    void testSetAsyncAfter() {
        Setup setup = new Setup();
        setup.setAsyncAfter(true);
        assertTrue(setup.isAsyncAfter());
    }
}
