package onactivityresult;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * parameters of a {@link OnActivityResult} method can be annotated to get the {@link android.content.Intent#getData()} of the intent<br>
 * Example:<br>
 * <p>
 * <code>
 *     &#64;OnActivityResult(requestCode = 1)
 *     void onResult(final int resultCode, @IntentData final Uri uri) {
 *          // Do something
 *     }
 * </code>
 * </p>
 * 
 * @since 0.2.0
 */
@Retention(CLASS)
@Target(PARAMETER)
public @interface IntentData {}
