import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseQueryTaskTest {

    private DatabaseQueryTask databaseQueryTask;

    @BeforeEach
    void setUp() {
        databaseQueryTask = new DatabaseQueryTask();
    }

    @Test
    void testInputVariablesGetterSetter() {
        Set<EmbeddedVariable> inputVariables = new HashSet<>();
        inputVariables.add(new EmbeddedVariable("inputVar1", "value1"));
        inputVariables.add(new EmbeddedVariable("inputVar2", "value2"));
        databaseQueryTask.setInputVariables(inputVariables);
        assertEquals(inputVariables, databaseQueryTask.getInputVariables());
    }

    @Test
    void testOutputVariableGetterSetter() {
        EmbeddedVariable outputVariable = new EmbeddedVariable("outputVar", "outputValue");
        databaseQueryTask.setOutputVariable(outputVariable);
        assertEquals(outputVariable, databaseQueryTask.getOutputVariable());
    }

    @Test
    void testAsyncBeforeGetterSetter() {
        databaseQueryTask.setAsyncBefore(true);
        assertEquals(true, databaseQueryTask.isAsyncBefore());
    }

    @Test
    void testAsyncAfterGetterSetter() {
        databaseQueryTask.setAsyncAfter(true);
        assertEquals(true, databaseQueryTask.isAsyncAfter());
    }

    @Test
    void testDesignationGetterSetter() {
        String designation = "designation";
        databaseQueryTask.setDesignation(designation);
        assertEquals(designation, databaseQueryTask.getDesignation());
    }

    @Test
    void testQueryGetterSetter() {
        String query = "SELECT * FROM table";
        databaseQueryTask.setQuery(query);
        assertEquals(query, databaseQueryTask.getQuery());
    }

    @Test
    void testOutputPathGetterSetter() {
        String outputPath = "/path/to/output";
        databaseQueryTask.setOutputPath(outputPath);
        assertEquals(outputPath, databaseQueryTask.getOutputPath());
    }

    @Test
    void testResultTypeGetterSetter() {
        DatabaseResultType resultType = DatabaseResultType.JSON;
        databaseQueryTask.setResultType(resultType);
        assertEquals(resultType, databaseQueryTask.getResultType());
    }

    @Test
    void testIncludeHeaderGetterSetter() {
        boolean includeHeader = true;
        databaseQueryTask.setIncludeHeader(includeHeader);
        assertEquals(includeHeader, databaseQueryTask.isIncludeHeader());
    }
}
