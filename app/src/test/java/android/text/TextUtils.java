package android.text;

/**
 * Class contains missing functions needed in EspressoIdlingResources.
 */
public class TextUtils {

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}