package onactivityresult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.Serializable;

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
        IntentHelper.getExtraString(intent, "StringExtra", null);
        verify(intent).getStringExtra("StringExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetStringExtraDefaultValue() {
        assertEquals("my_default", IntentHelper.getExtraString(new Intent(), "myStringKey", "my_default"));
    }

    @Test
    public void testGetStringExtraValue() {
        assertEquals("awesomey", IntentHelper.getExtraString(new Intent().putExtra("myStringKey", "awesomey"), "myStringKey", "default"));
    }

    @Test
    public void testGetIntExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraInt(intent, "IntExtra", -1);
        verify(intent).getIntExtra("IntExtra", -1);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetFloatExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraFloat(intent, "FloatExtra", -1.f);
        verify(intent).getFloatExtra("FloatExtra", -1.f);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetByteExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraByte(intent, "ByteExtra", (byte) 4);
        verify(intent).getByteExtra("ByteExtra", (byte) 4);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetBooleanExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraBoolean(intent, "BooleanExtra", true);
        verify(intent).getBooleanExtra("BooleanExtra", true);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetDoubleExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraDouble(intent, "DoubleExtra", -1.d);
        verify(intent).getDoubleExtra("DoubleExtra", -1.d);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetShortExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraShort(intent, "ShortExtra", (short) 1);
        verify(intent).getShortExtra("ShortExtra", (short) 1);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraChar(intent, "CharExtra", (char) 65);
        verify(intent).getCharExtra("CharExtra", (char) 65);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetLongExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraLong(intent, "LongExtra", -1L);
        verify(intent).getLongExtra("LongExtra", -1L);
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharSequenceExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraCharSequence(intent, "CharSequenceExtra", null);
        verify(intent).getCharSequenceExtra("CharSequenceExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetCharSequenceExtraDefaultValue() {
        assertEquals("another_default", IntentHelper.getExtraCharSequence(new Intent(), "myCharSequenceKey", "another_default"));
    }

    @Test
    public void testGetCharSequenceExtraValue() {
        assertEquals("awesomeness", IntentHelper.getExtraCharSequence(new Intent().putExtra("myCharSequenceKey", (CharSequence) "awesomeness"), "myCharSequenceKey", "default"));
    }

    @Test
    public void testGetBundleExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraBundle(intent, "BundleExtra", null);
        verify(intent).getBundleExtra("BundleExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetBundleExtraDefaultValue() {
        final Bundle defaultValue = new Bundle();
        assertEquals(defaultValue, IntentHelper.getExtraBundle(new Intent(), "myBundleKey", defaultValue));
    }

    @Test
    public void testGetBundleExtraValue() {
        final Bundle bundle = new Bundle();
        bundle.putString("test", "1234");
        assertEquals(bundle, IntentHelper.getExtraBundle(new Intent().putExtra("myBundleKey", bundle), "myBundleKey", null));
    }

    @Test
    public void testGetSerializableExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraSerializable(intent, "SerializableExtra", null);
        verify(intent).getSerializableExtra("SerializableExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetSerializableExtraDefaultValue() {
        final Serializable defaultValue = mock(Serializable.class);
        assertEquals(defaultValue, IntentHelper.getExtraSerializable(new Intent(), "mySerializableKey", defaultValue));
    }

    @Test
    public void testGetSerializableExtraValue() {
        final Serializable serializable = mock(Serializable.class);
        assertEquals(serializable, IntentHelper.getExtraSerializable(new Intent().putExtra("mySerializableKey", serializable), "mySerializableKey", null));
    }

    @Test
    public void testGetParcelableExtra() {
        final Intent intent = mock(Intent.class);
        IntentHelper.getExtraParcelable(intent, "ParcelableExtra", null);
        verify(intent).getParcelableExtra("ParcelableExtra");
        verifyNoMoreInteractions(intent);
    }

    @Test
    public void testGetParcelableExtraDefaultValue() {
        final Parcelable defaultValue = mock(Parcelable.class);
        assertEquals(defaultValue, IntentHelper.getExtraParcelable(new Intent(), "myParcelableKey", defaultValue));
    }

    @Test
    public void testGetParcelableExtraValue() {
        final Parcelable parcelable = mock(Parcelable.class);
        assertEquals(parcelable, IntentHelper.getExtraParcelable(new Intent().putExtra("myParcelableKey", parcelable), "myParcelableKey", null));
    }
}
