package rabbit;

import java.util.Random;

public class Utils {
    public static final void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void throwExceptionsPercentOfTime(int p) {
        Random random = new Random();
        if ((random.nextInt(100) + 1) <= p) {
            throw new RuntimeException("Throwing a random exception!");
        }
    }
}
