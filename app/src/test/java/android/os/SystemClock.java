package android.os;

/**
 * Class contains missing functions needed in EspressoIdlingResources.
 */
public class SystemClock {

    public static long uptimeMillis() {
        return System.currentTimeMillis();
    }
}
