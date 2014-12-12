package rabbit.service;

public class Utils {
    public static final void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
