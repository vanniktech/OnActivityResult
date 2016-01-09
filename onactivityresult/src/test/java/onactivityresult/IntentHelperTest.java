package onactivityresult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import android.content.Intent;
import android.net.Uri;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

public class IntentHelperTest {
    @Rule public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testConstructorShouldBePrivate() {
        PrivateConstructorChecker.forClass(IntentHelper.class).expectedTypeOfException(AssertionError.class).expectedExceptionMessage("No instances.").check();
    }

    @Test
    public void testGetIntentDataWithNullIntent() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("intent is null");
        IntentHelper.getIntentData(null);
    }

    @Test
    public void testGetIntentDataWithNullIntentData() {
        final Intent intent = mock(Intent.class);
        doReturn(null).when(intent).getData();

        assertEquals(null, IntentHelper.getIntentData(intent));
    }

    @Test
    public void testGetIntentDataWithNonNullIntentData() {
        final Intent intent = mock(Intent.class);
        final Uri uri = mock(Uri.class);
        doReturn(uri).when(intent).getData();

        assertEquals(uri, IntentHelper.getIntentData(intent));
    }

    @Test
    public void testGetIntentDataNonNullWithNullIntent() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("intent is null");
        IntentHelper.getIntentDataNonNull(null);
    }

    @Test
    public void testGetIntentDataNonNullWithNullIntentData() {
        final Intent intent = mock(Intent.class);
        doReturn(null).when(intent).getData();

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("intentData is null");
        IntentHelper.getIntentDataNonNull(intent);
    }

    @Test
    public void testGetIntentDataNonNullWithNonNullIntentData() {
        final Intent intent = mock(Intent.class);
        final Uri uri = mock(Uri.class);
        doReturn(uri).when(intent).getData();

        assertEquals(uri, IntentHelper.getIntentDataNonNull(intent));
    }

    @Test
    public void testGetIntentOptionalDataWithNullIntent() {
        assertEquals(null, IntentHelper.getIntentDataNullable(null));
    }

    @Test
    public void testGetIntentOptionalDataWithNullIntentData() {
        final Intent intent = mock(Intent.class);
        doReturn(null).when(intent).getData();

        assertEquals(null, IntentHelper.getIntentDataNullable(intent));
    }

    @Test
    public void testGetIntentOptionalDataWithNonNullIntentData() {
        final Intent intent = mock(Intent.class);
        final Uri uri = mock(Uri.class);
        doReturn(uri).when(intent).getData();

        assertEquals(uri, IntentHelper.getIntentDataNullable(intent));
    }
}
