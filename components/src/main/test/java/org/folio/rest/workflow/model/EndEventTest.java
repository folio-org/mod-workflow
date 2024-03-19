import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EndEventTest {

    @Test
    void testEndEventInheritance() {
        EndEvent endEvent = new EndEvent();

        assertTrue(endEvent.getId() == null); 
        assertTrue(endEvent.getName() == null); 

        assertTrue(endEvent.isEndEvent()); 
    }
}
