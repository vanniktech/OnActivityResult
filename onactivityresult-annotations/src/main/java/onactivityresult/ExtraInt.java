package onactivityresult;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * parameters of a {@link OnActivityResult} annotated method can be annotated to get an int extra of the Intent<br>
 * Example:<br>
 * <p>
 * <code>
 *     &#64;OnActivityResult(requestCode = 1)
 *     void onResult(&#64;ExtraInt final int extraInt) {
 *          // Do something
 *     }
 * </code>
 * </p>
 * <br>
 * extra name: same as parameter <br>
 * NOTE: In this case it would be extraInt<br>
 *
 * @since 0.3.0
 */
@Retention(CLASS)
@Target(PARAMETER)
public @interface ExtraInt {
    /**
     * @return the set default value if the extra is not set on the intent
     * @since 0.3.0
     */
    int defaultValue() default 0;
}
