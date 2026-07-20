import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScriptTaskTest {

    @Test
    void testConstructor() {
        ScriptTask scriptTask = new ScriptTask();
        assertNotNull(scriptTask);
    }

    @Test
    void testScriptFormatDefault() {
        ScriptTask scriptTask = new ScriptTask();
        assertEquals("javaScript", scriptTask.getScriptFormat());
    }

    @Test
    void testCodeNotNull() {
        ScriptTask scriptTask = new ScriptTask();
        assertNotNull(scriptTask.getCode());
    }

    @Test
    void testAsyncBeforeDefault() {
        ScriptTask scriptTask = new ScriptTask();
        assertFalse(scriptTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterDefault() {
        ScriptTask scriptTask = new ScriptTask();
        assertFalse(scriptTask.isAsyncAfter());
    }

    @Test
    void testResultVariableIsNull() {
        ScriptTask scriptTask = new ScriptTask();
        assertFalse(scriptTask.hasResultVariable());
    }

    @Test
    void testSetResultVariable() {
        ScriptTask scriptTask = new ScriptTask();
        scriptTask.setResultVariable("result");
        assertTrue(scriptTask.hasResultVariable());
    }
}
