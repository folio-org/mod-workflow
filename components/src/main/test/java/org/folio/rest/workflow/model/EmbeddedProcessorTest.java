import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmbeddedProcessorTest {

    private EmbeddedProcessor embeddedProcessor;
    private Validator validator;

    @BeforeEach
    void setUp() {
        embeddedProcessor = new EmbeddedProcessor();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testScriptTypeGetterSetter() {
        ScriptType scriptType = ScriptType.JAVASCRIPT;
        embeddedProcessor.setScriptType(scriptType);
        assertEquals(scriptType, embeddedProcessor.getScriptType());
    }

    @Test
    void testFunctionNameGetterSetter() {
        String functionName = "function";
        embeddedProcessor.setFunctionName(functionName);
        assertEquals(functionName, embeddedProcessor.getFunctionName());
    }

    @Test
    void testCodeGetterSetter() {
        String code = "console.log('Hello, World!');";
        embeddedProcessor.setCode(code);
        assertEquals(code, embeddedProcessor.getCode());
    }

    @Test
    void testBufferGetterSetter() {
        int buffer = 100;
        embeddedProcessor.setBuffer(buffer);
        assertEquals(buffer, embeddedProcessor.getBuffer());
    }

    @Test
    void testDelayGetterSetter() {
        int delay = 500;
        embeddedProcessor.setDelay(delay);
        assertEquals(delay, embeddedProcessor.getDelay());
    }

    @Test
    void testFunctionNameSizeValidation() {
        assertThrows(Exception.class, () -> {
            embeddedProcessor.setFunctionName(""); // smaller than min size
            validator.validateProperty(embeddedProcessor, "functionName");
        });

        assertThrows(Exception.class, () -> {
            embeddedProcessor.setFunctionName("A".repeat(129)); // larger than max size
            validator.validateProperty(embeddedProcessor, "functionName");
        });

        // valid size
        embeddedProcessor.setFunctionName("A".repeat(4));
        assertEquals(0, validator.validateProperty(embeddedProcessor, "functionName").size());
    }

    @Test
    void testCodeSizeValidation() {
        assertThrows(Exception.class, () -> {
            embeddedProcessor.setCode(""); // smaller than min size
            validator.validateProperty(embeddedProcessor, "code");
        });

        // valid size
        embeddedProcessor.setCode("A".repeat(4));
        assertEquals(0, validator.validateProperty(embeddedProcessor, "code").size());
    }
}
