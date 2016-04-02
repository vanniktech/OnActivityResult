package onactivityresult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SuppressWarnings("checkstyle:magicnumber")
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
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
        verify(intent).getStringExtra("StringExtra");
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
        verify(intent).getIntExtra("IntExtra", -1);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetFloatExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getFloatExtra(intent, "FloatExtra", -1.f);
        verify(intent).getFloatExtra("FloatExtra", -1.f);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetByteExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getByteExtra(intent, "ByteExtra", (byte) 4);
        verify(intent).getByteExtra("ByteExtra", (byte) 4);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetBooleanExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getBooleanExtra(intent, "BooleanExtra", true);
        verify(intent).getBooleanExtra("BooleanExtra", true);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetDoubleExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getDoubleExtra(intent, "DoubleExtra", -1.d);
        verify(intent).getDoubleExtra("DoubleExtra", -1.d);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetShortExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getShortExtra(intent, "ShortExtra", (short) 1);
        verify(intent).getShortExtra("ShortExtra", (short) 1);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getCharExtra(intent, "CharExtra", (char) 65);
        verify(intent).getCharExtra("CharExtra", (char) 65);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetLongExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getLongExtra(intent, "LongExtra", -1L);
        verify(intent).getLongExtra("LongExtra", -1L);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharSequenceExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getCharSequenceExtra(intent, "CharSequenceExtra", null);
        verify(intent).getCharSequenceExtra("CharSequenceExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharSequenceExtraDefaultValue() {
        assertEquals("default", IntentHelper.getCharSequenceExtra(new Intent(), "myCharSequenceKey", "default"));
    }

    @Test
    public void testGetCharSequenceExtraValue() {
        assertEquals("awesome", IntentHelper.getCharSequenceExtra(new Intent().putExtra("myCharSequenceKey", (CharSequence) "awesome"), "myCharSequenceKey", "default"));
    }

    @Test
    public void testGetBundleExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getBundleExtra(intent, "BundleExtra", null);
        verify(intent).getBundleExtra("BundleExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetBundleExtraDefaultValue() {
        final Bundle defaultValue = new Bundle();
        assertEquals(defaultValue, IntentHelper.getBundleExtra(new Intent(), "myBundleKey", defaultValue));
    }

    @Test
    public void testGetBundleExtraValue() {
        final Bundle bundle = new Bundle();
        bundle.putString("test", "1234");
        assertEquals(bundle, IntentHelper.getBundleExtra(new Intent().putExtra("myBundleKey", bundle), "myBundleKey", null));
    }
}
