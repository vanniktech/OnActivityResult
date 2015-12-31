package onactivityresult;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * non private and non static methods can be annotated with {@link OnActivityResult}
 * Example:<br>
 * <p>
 * <code>
 *     &#64;OnActivityResult(requestCode = 1)
 *     void onResult(final int resultCode, final Intent intent) {
 *          // Do something
 *     }
 * </code>
 * </p>
 * Various parameters are supported:
 * <ul>
 * <li>none</li>
 * <li>int</li>
 * <li>Intent</li>
 * <li>int, Intent</li>
 * <li>Intent, int</li>
 * </ul>
 * Where int parameters will get the resultCode and Intent parameters will get the Intent.<br>
 * Note: Each annotated method shall only have one int and / or Intent variable.<br>
 * <br>
 * In addition to that other parameter annotations are supported like:<br>
 * <ul>
 * <li>{@link IntentData} {@link android.net.Uri}</li>
 * </ul>
 *
 * @since 0.1.0
 */
@Retention(CLASS)
@Target(METHOD)
public @interface OnActivityResult {
    /**
     * @return requestCode that should be <code>>= 0</code>
     * @since 0.1.0
     */
    int requestCode();
}
