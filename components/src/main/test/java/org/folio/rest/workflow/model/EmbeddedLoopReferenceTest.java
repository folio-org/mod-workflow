import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmbeddedLoopReferenceTest {

    private EmbeddedLoopReference embeddedLoopReference;

    @BeforeEach
    void setUp() {
        embeddedLoopReference = new EmbeddedLoopReference();
    }

    @Test
    void testCardinalityExpressionGetterSetter() {
        String cardinalityExpression = "cardinality";
        embeddedLoopReference.setCardinalityExpression(cardinalityExpression);
        assertEquals(cardinalityExpression, embeddedLoopReference.getCardinalityExpression());
    }

    @Test
    void testDataInputRefExpressionGetterSetter() {
        String dataInputRefExpression = "dataInputRef";
        embeddedLoopReference.setDataInputRefExpression(dataInputRefExpression);
        assertEquals(dataInputRefExpression, embeddedLoopReference.getDataInputRefExpression());
    }

    @Test
    void testInputDataNameGetterSetter() {
        String inputDataName = "inputData";
        embeddedLoopReference.setInputDataName(inputDataName);
        assertEquals(inputDataName, embeddedLoopReference.getInputDataName());
    }

    @Test
    void testCompleteConditionExpressionGetterSetter() {
        String completeConditionExpression = "completeCondition";
        embeddedLoopReference.setCompleteConditionExpression(completeConditionExpression);
        assertEquals(completeConditionExpression, embeddedLoopReference.getCompleteConditionExpression());
    }

    @Test
    void testParallelGetterSetter() {
        embeddedLoopReference.setParallel(true);
        assertTrue(embeddedLoopReference.isParallel());

        embeddedLoopReference.setParallel(false);
        assertFalse(embeddedLoopReference.isParallel());
    }

    @Test
    void testHasCardinalityExpression() {
        assertFalse(embeddedLoopReference.hasCardinalityExpression());

        embeddedLoopReference.setCardinalityExpression("cardinality");
        assertTrue(embeddedLoopReference.hasCardinalityExpression());
    }

    @Test
    void testHasCompleteConditionExpression() {
        assertFalse(embeddedLoopReference.hasCompleteConditionExpression());

        embeddedLoopReference.setCompleteConditionExpression("completeCondition");
        assertTrue(embeddedLoopReference.hasCompleteConditionExpression());
    }

    @Test
    void testHasDataInput() {
        assertFalse(embeddedLoopReference.hasDataInput());

        embeddedLoopReference.setDataInputRefExpression("dataInputRef");
        embeddedLoopReference.setInputDataName("inputData");
        assertTrue(embeddedLoopReference.hasDataInput());
    }
}
