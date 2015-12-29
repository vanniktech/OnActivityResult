package onactivityresult;

import android.content.Intent;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import onactivityresult.internal.IOnActivityResult;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.AvoidDollarSigns", "checkstyle:typename", "checkstyle:innertypelast", "PMD.JUnitTestsShouldIncludeAssert", "PMD.AvoidThrowingRawExceptionTypes" })
public class ActivityResultTest {
    @Rule public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConstructorShouldBePrivate() {
        PrivateConstructorChecker.forClass(ActivityResult.class).expectedTypeOfException(AssertionError.class).expectedExceptionMessage("No instances.").check();
    }

    @Test
    public void testOnActivityResultClassIsNotFound() {
        final ActivityResult.OnResult onResult = ActivityResult.onResult(0, 0, null);
        final LonelyObject lonelyObject = new LonelyObject();

        expectedException.expect(ActivityResult.ActivityResultRuntimeException.class);
        expectedException.expectMessage("Could not find OnActivityResult class for onactivityresult.ActivityResultTest$LonelyObject");
        onResult.into(lonelyObject);
    }

    public static class LonelyObject {}

    @Test
    public void testOnActivityResultClassFound() {
        final ActivityResult.OnResult onResult = ActivityResult.onResult(1, 2, null);
        final OnResult result = new OnResult();

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("OnResult was called: 1, 2, null");
        onResult.into(result);
    }

    public static class OnResult {}

    public static class OnResult$$OnActivityResult implements IOnActivityResult<OnResult> {
        @Override
        public void onResult(final OnResult onResult, final int requestCode, final int resultCode, final Intent intent) {
            throw new RuntimeException("OnResult was called: " + requestCode + ", " + resultCode + ", " + intent);
        }
    }

    @Test
    public void testOnResultShouldThrowRuntimeExceptionWithPrivateConstructorTextWhenOnActivityResultHasPrivateConstructor() {
        final ActivityResult.OnResult onResult = ActivityResult.onResult(0, 0, null);
        final OnResultPrivateObject onResultPrivateObject = new OnResultPrivateObject();

        expectedException.expect(ActivityResult.ActivityResultRuntimeException.class);
        expectedException.expectMessage("Can't create OnActivityResult class for onactivityresult.ActivityResultTest$OnResultPrivateObject");
        onResult.into(onResultPrivateObject);
    }

    public static class OnResultPrivateObject {}

    public static final class OnResultPrivateObject$$OnActivityResult implements IOnActivityResult<OnResultPrivateObject> {
        private OnResultPrivateObject$$OnActivityResult() {}

        @Override
        public void onResult(final OnResultPrivateObject extractingObjectTimeProvider, final int requestCode, final int resultCode, final Intent intent) {
            throw new RuntimeException("Extract was called");
        }
    }
}
