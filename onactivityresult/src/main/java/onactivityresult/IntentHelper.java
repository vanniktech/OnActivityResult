package onactivityresult;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    public static String getStringExtra(final Intent intent, final String key, final String defaultValue) {
        final String extra = intent.getStringExtra(key);
        return extra != null ? extra : defaultValue;
    }

    public static int getIntExtra(final Intent intent, final String key, final int defaultValue) {
        return intent.getIntExtra(key, defaultValue);
    }

    public static boolean getBooleanExtra(final Intent intent, final String key, final boolean defaultValue) {
        return intent.getBooleanExtra(key, defaultValue);
    }

    public static float getFloatExtra(final Intent intent, final String key, final float defaultValue) {
        return intent.getFloatExtra(key, defaultValue);
    }

    public static double getDoubleExtra(final Intent intent, final String key, final double defaultValue) {
        return intent.getDoubleExtra(key, defaultValue);
    }

    public static byte getByteExtra(final Intent intent, final String key, final byte defaultValue) {
        return intent.getByteExtra(key, defaultValue);
    }

    public static short getShortExtra(final Intent intent, final String key, final short defaultValue) {
        return intent.getShortExtra(key, defaultValue);
    }

    public static char getCharExtra(final Intent intent, final String key, final char defaultValue) {
        return intent.getCharExtra(key, defaultValue);
    }

    public static long getLongExtra(final Intent intent, final String key, final long defaultValue) {
        return intent.getLongExtra(key, defaultValue);
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
