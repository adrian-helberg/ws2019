import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlackBoxTest_Template {

    // ------- DO NOT TOUCH THIS LINE -------
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        assertTrue(BlackBox.evalEqClasses(), "Not all equivalence classes covert!");
    }

    @Test
    void testG() {
        X[] xes = X.values();
        Y[] ys = Y.values();
        for (int i = 0; i < xes.length; i++) {
            for (int j = 0; j < ys.length; j++) {
                for (int k = 0; k < 2; k++) {
                    int x = xes[i].getValue();
                    int y = ys[j].getValue();
                    boolean b = k == 0;
                    boolean isValid = x > 1 && x < 17 && y >= -2 && y <= 2;
                    Color color = x < 10 && y > 0 && b ? Color.RED : (x >= 10 && !b ? Color.GREEN : Color.YELLOW);
                    // System.out.format("x: %s, y: %s, b: %s, valid: %s\n", x, y, b, isValid);
                    assertEquals(isValid ? color : null, BlackBox.G(xes[i].getValue(), ys[j].getValue(), k == 0));
                }
            }
        }
    }

    enum X {
        VALID_X_LOWER_BOUND(2),
        VALID_X_UPPER_BOUND(16),
        INVALID_X_LOWER_BOUND_1(Integer.MIN_VALUE),
        INVALID_X_UPPER_BOUND_1(1),
        INVALID_X_LOWER_BOUND_2(17),
        INVALID_X_UPPER_BOUND_2(Integer.MAX_VALUE);

        private final int value;

        X(final int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    enum Y {
        VALID_Y_LOWER_BOUND(-2),
        VALID_Y_UPPER_BOUND(2),
        INVALID_Y_LOWER_BOUND_1(Integer.MIN_VALUE),
        INVALID_Y_UPPER_BOUND_1(-3),
        INVALID_Y_LOWER_BOUND_2(3),
        INVALID_Y_UPPER_BOUND_2(Integer.MAX_VALUE);

        private final int value;

        Y(final int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

}
