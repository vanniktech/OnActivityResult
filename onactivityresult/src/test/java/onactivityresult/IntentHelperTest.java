package onactivityresult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import android.content.Intent;
import android.net.Uri;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

@SuppressWarnings("checkstyle:magicnumber")
@RunWith(RobolectricTestRunner.class)
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

    @Test
    public void testGetStringExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getStringExtra(intent, "StringExtra", null);
        verify(intent, times(1)).getStringExtra("StringExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetStringExtraDefaultValue() {
        assertEquals("default", IntentHelper.getStringExtra(new Intent(), "myStringKey", "default"));
    }

    @Test
    public void testGetStringExtraValue() {
        assertEquals("awesome", IntentHelper.getStringExtra(new Intent().putExtra("myStringKey", "awesome"), "myStringKey", "default"));
    }

    @Test
    public void testGetIntExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getIntExtra(intent, "IntExtra", -1);
        verify(intent, times(1)).getIntExtra("IntExtra", -1);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetFloatExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getFloatExtra(intent, "FloatExtra", -1.f);
        verify(intent, times(1)).getFloatExtra("FloatExtra", -1.f);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetByteExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getByteExtra(intent, "ByteExtra", (byte) 4);
        verify(intent, times(1)).getByteExtra("ByteExtra", (byte) 4);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetBooleanExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getBooleanExtra(intent, "BooleanExtra", true);
        verify(intent, times(1)).getBooleanExtra("BooleanExtra", true);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetDoubleExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getDoubleExtra(intent, "DoubleExtra", -1.d);
        verify(intent, times(1)).getDoubleExtra("DoubleExtra", -1.d);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetShortExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getShortExtra(intent, "ShortExtra", (short) 1);
        verify(intent, times(1)).getShortExtra("ShortExtra", (short) 1);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getCharExtra(intent, "CharExtra", (char) 65);
        verify(intent, times(1)).getCharExtra("CharExtra", (char) 65);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetLongExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getLongExtra(intent, "LongExtra", -1L);
        verify(intent, times(1)).getLongExtra("LongExtra", -1L);
        verifyNoMoreInteractions(intent);
    }
}
