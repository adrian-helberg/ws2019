import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateTest_Template {

    // ------- DO NOT TOUCH THIS LINE -------
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        assertTrue(Statemachine.evalCoverage(), "There are states or transitions left to cover!");
    }

    @Test
    void test() {
        Statemachine.start(); // (re-) starting the statemachine
        Statemachine.getStateName(); // returns the name of the current state
        Statemachine.getStateEvents(); // returns a Set of all permitted events of the current state
        Statemachine.transition("a"); // transition the current state with the given event
        String stateName = Statemachine.getStateName();
        assertEquals("S4", stateName);
    }

}
