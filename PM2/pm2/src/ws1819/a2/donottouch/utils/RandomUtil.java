package ws1819.a2.donottouch.utils;

import java.util.Random;

public class RandomUtil {
    private static final int seed = 89162;
    private static Random rand = new Random(seed);

    public static Random getRand(){
        return rand;
    }

    public static void reset(){
        rand = new Random(seed);
    }
}
