import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmbeddedVariableTest {

    private EmbeddedVariable embeddedVariable;
    private Validator validator;

    @BeforeEach
    void setUp() {
        embeddedVariable = new EmbeddedVariable();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testKeyGetterSetter() {
        String key = "variableKey";
        embeddedVariable.setKey(key);
        assertEquals(key, embeddedVariable.getKey());
    }

    @Test
    void testTypeGetterSetter() {
        VariableType type = VariableType.PROCESS;
        embeddedVariable.setType(type);
        assertEquals(type, embeddedVariable.getType());
    }

    @Test
    void testSpinGetterSetter() {
        embeddedVariable.setSpin(true);
        assertEquals(true, embeddedVariable.isSpin());
    }

    @Test
    void testAsJsonGetterSetter() {
        embeddedVariable.setAsJson(true);
        assertEquals(true, embeddedVariable.isAsJson());
    }

    @Test
    void testAsTransientGetterSetter() {
        embeddedVariable.setAsTransient(true);
        assertEquals(true, embeddedVariable.isAsTransient());
    }

    @Test
    void testKeySizeValidation() {
        assertThrows(Exception.class, () -> {
            embeddedVariable.setKey(""); // smaller than min size
            validator.validateProperty(embeddedVariable, "key");
        });

        assertThrows(Exception.class, () -> {
            embeddedVariable.setKey("A".repeat(65)); // larger than max size
            validator.validateProperty(embeddedVariable, "key");
        });

        // valid size
        embeddedVariable.setKey("A".repeat(4));
        assertEquals(0, validator.validateProperty(embeddedVariable, "key").size());
    }
}
