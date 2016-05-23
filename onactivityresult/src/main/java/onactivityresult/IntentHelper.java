package onactivityresult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;

public final class IntentHelper {
    @NonNull
    public static Uri getIntentData(final Intent intent) {
        checkNotNull(intent, "intent is null");
        return intent.getData();
    }

    @NonNull
    public static Uri getIntentDataNonNull(final Intent intent) {
        checkNotNull(intent, "intent is null");
        return checkNotNull(intent.getData(), "intentData is null");
    }

    @Nullable
    public static Uri getIntentDataNullable(final Intent intent) {
        return intent != null ? intent.getData() : null;
    }

    public static String getExtraString(final Intent intent, final String key, final String defaultValue) {
        final String extra = intent.getStringExtra(key);
        return extra != null ? extra : defaultValue;
    }

    public static int getExtraInt(final Intent intent, final String key, final int defaultValue) {
        return intent.getIntExtra(key, defaultValue);
    }

    public static boolean getExtraBoolean(final Intent intent, final String key, final boolean defaultValue) {
        return intent.getBooleanExtra(key, defaultValue);
    }

    public static float getExtraFloat(final Intent intent, final String key, final float defaultValue) {
        return intent.getFloatExtra(key, defaultValue);
    }

    public static double getExtraDouble(final Intent intent, final String key, final double defaultValue) {
        return intent.getDoubleExtra(key, defaultValue);
    }

    public static byte getExtraByte(final Intent intent, final String key, final byte defaultValue) {
        return intent.getByteExtra(key, defaultValue);
    }

    public static short getExtraShort(final Intent intent, final String key, final short defaultValue) {
        return intent.getShortExtra(key, defaultValue);
    }

    public static char getExtraChar(final Intent intent, final String key, final char defaultValue) {
        return intent.getCharExtra(key, defaultValue);
    }

    public static long getExtraLong(final Intent intent, final String key, final long defaultValue) {
        return intent.getLongExtra(key, defaultValue);
    }

    public static CharSequence getExtraCharSequence(final Intent intent, final String key, final CharSequence defaultValue) {
        final CharSequence extra = intent.getCharSequenceExtra(key);
        return extra != null ? extra : defaultValue;
    }

    public static Bundle getExtraBundle(final Intent intent, final String key, final Bundle defaultValue) {
        final Bundle extra = intent.getBundleExtra(key);
        return extra != null ? extra : defaultValue;
    }

    public static <T extends Serializable> T getExtraSerializable(final Intent intent, final String key, final T defaultValue) {
        //noinspection unchecked
        final T extra = (T) intent.getSerializableExtra(key);
        return extra != null ? extra : defaultValue;
    }

    public static <T extends Parcelable> T getExtraParcelable(final Intent intent, final String key, final T defaultValue) {
        //noinspection unchecked
        final T extra = (T) intent.getParcelableExtra(key);
        return extra != null ? extra : defaultValue;
    }

    private static <T> T checkNotNull(final T t, final String message) {
        if (t == null) {
            throw new IllegalArgumentException(message);
        }

        return t;
    }

    private IntentHelper() {
        throw new AssertionError("No instances.");
    }
}
