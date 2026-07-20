import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class FtpTaskTest {

    private FtpTask ftpTask;

    @BeforeEach
    void setUp() {
        ftpTask = new FtpTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable());
        ftpTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, ftpTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable();
        ftpTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, ftpTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        ftpTask.setAsyncBefore(true);
        assertTrue(ftpTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        ftpTask.setAsyncAfter(true);
        assertTrue(ftpTask.isAsyncAfter());
    }

    @Test
    void testOriginPathGetterSetter() {
        String originPath = "/origin/path";
        ftpTask.setOriginPath(originPath);
        assertEquals(originPath, ftpTask.getOriginPath());
    }

    @Test
    void testDestinationPathGetterSetter() {
        String destinationPath = "/destination/path";
        ftpTask.setDestinationPath(destinationPath);
        assertEquals(destinationPath, ftpTask.getDestinationPath());
    }

    @Test
    void testOpGetterSetter() {
        SftpOp op = SftpOp.DOWNLOAD;
        ftpTask.setOp(op);
        assertEquals(op, ftpTask.getOp());
    }

    @Test
    void testSchemeGetterSetter() {
        String scheme = "ftp";
        ftpTask.setScheme(scheme);
        assertEquals(scheme, ftpTask.getScheme());
    }

    @Test
    void testHostGetterSetter() {
        String host = "example.com";
        ftpTask.setHost(host);
        assertEquals(host, ftpTask.getHost());
    }

    @Test
    void testPortGetterSetter() {
        int port = 22;
        ftpTask.setPort(port);
        assertEquals(port, ftpTask.getPort());
    }

    @Test
    void testUsernameGetterSetter() {
        String username = "user";
        ftpTask.setUsername(username);
        assertEquals(username, ftpTask.getUsername());
    }

    @Test
    void testPasswordGetterSetter() {
        String password = "password";
        ftpTask.setPassword(password);
        assertEquals(password, ftpTask.getPassword());
    }
}
