package onactivityresult;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * parameters of a {@link OnActivityResult} annotated method can be annotated to get a double extra of the Intent<br>
 * Example:<br>
 * <p>
 * <code>
 *     &#64;OnActivityResult(requestCode = 1)
 *     void onResult(&#64;ExtraDouble final double extraDouble) {
 *          // Do something
 *     }
 * </code>
 * </p>
 * <br>
 * extra name: same as parameter <br>
 * NOTE: In this case it would be extraDouble<br>
 * <br>
 * NOTE: If you don't care about the {@link ExtraDouble#defaultValue()} you can also use the {@link Extra} annotation<br>
 * <br>
 *
 * @since 0.3.0
 */
@Retention(CLASS)
@Target(PARAMETER)
public @interface ExtraDouble {
    /**
     * @return the set default value if the extra is not set on the intent
     * @since 0.3.0
     */
    double defaultValue() default 0;

    /**
     * @return the name of the extra parameter which is contained in the Intent
     * @since 0.6.0
     */
    String name() default "";
}
