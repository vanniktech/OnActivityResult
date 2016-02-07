package onactivityresult;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * parameters of a {@link OnActivityResult} annotated method can be annotated to get a short extra of the Intent<br>
 * Example:<br>
 * <p>
 * <code>
 *     &#64;OnActivityResult(requestCode = 1)
 *     void onResult(&#64;ExtraShort final short extraShort) {
 *          // Do something
 *     }
 * </code>
 * </p>
 * <br>
 * extra name: same as parameter <br>
 * NOTE: In this case it would be extraShort<br>
 *
 * @since 0.3.0
 */
@Retention(CLASS)
@Target(PARAMETER)
public @interface ExtraShort {
    /**
     * @return the set default value if the extra is not set on the intent
     * @since 0.3.0
     */
    short defaultValue() default 0;
}
