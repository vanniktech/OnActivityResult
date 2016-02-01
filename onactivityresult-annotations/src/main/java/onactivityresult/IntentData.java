package onactivityresult;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * parameters of a {@link OnActivityResult} annotated method can be annotated to get the {@link android.content.Intent#getData()} of the intent<br>
 * Example:<br>
 * <p>
 * <code>
 *     &#64;OnActivityResult(requestCode = 1)
 *     void onResult(final int resultCode, @IntentData final Uri uri) {
 *          // Do something
 *     }
 * </code>
 * </p>
 * <br>
 * If any annotation with the name Nullable (e.g. google's support annotation or IntelliJs annotation) is applied on this parameter,<br>
 * it'll check the {@link android.content.Intent} for non null first and then try to get the intent data.<br>
 * If the intent or the data is null, null will be returned.
 * <br>
 * <br>
 * If any annotation with the name NotNull or NonNull (e.g. google's support annotation or IntelliJs annotation) is applied on this parameter,<br>
 * it'll check the {@link android.content.Intent} for non null first and then check that the intent data is also not null.<br>
 * If the intent or the data is null an exception will be thrown.
 *
 * @since 0.2.0
 */
@Retention(CLASS)
@Target(PARAMETER)
public @interface IntentData {}
