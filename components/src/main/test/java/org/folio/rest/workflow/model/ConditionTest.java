import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConditionTest {

    private Condition condition;
    private Validator validator;

    @BeforeEach
    void setUp() {
        condition = new Condition();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testExpressionGetterSetter() {
        String expression = "expression";
        condition.setExpression(expression);
        assertEquals(expression, condition.getExpression());
    }

    @Test
    void testAnswerGetterSetter() {
        String answer = "answer";
        condition.setAnswer(answer);
        assertEquals(answer, condition.getAnswer());
    }

    @Test
    void testExpressionSizeValidation() {
        assertThrows(Exception.class, () -> {
            condition.setExpression("");
            validator.validateProperty(condition, "expression");
        });

        assertThrows(Exception.class, () -> {
            condition.setExpression("A".repeat(129));
            validator.validateProperty(condition, "expression");
        });

        condition.setExpression("A".repeat(4));
        assertEquals(0, validator.validateProperty(condition, "expression").size());
    }

    @Test
    void testAnswerSizeValidation() {
        assertThrows(Exception.class, () -> {
            condition.setAnswer("");
            validator.validateProperty(condition, "answer");
        });

        assertThrows(Exception.class, () -> {
            condition.setAnswer("A".repeat(65));
            validator.validateProperty(condition, "answer");
        });

        condition.setAnswer("A".repeat(2));
        assertEquals(0, validator.validateProperty(condition, "answer").size());
    }
}
