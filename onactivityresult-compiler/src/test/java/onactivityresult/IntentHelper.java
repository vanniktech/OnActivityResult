package onactivityresult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.Serializable;

@SuppressWarnings({ "unused", "PMD.AvoidDuplicateLiterals" })
public final class IntentHelper {
    public static Uri getIntentData(final Intent intent) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static Uri getIntentDataNonNull(final Intent intent) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static Uri getIntentDataNullable(final Intent intent) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static String getExtraString(final Intent intent, final String key, final String defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static int getExtraInt(final Intent intent, final String key, final int defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static boolean getExtraBoolean(final Intent intent, final String key, final boolean defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static float getExtraFloat(final Intent intent, final String key, final float defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static double getExtraDouble(final Intent intent, final String key, final double defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static byte getExtraByte(final Intent intent, final String key, final byte defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static short getExtraShort(final Intent intent, final String key, final short defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static char getExtraChar(final Intent intent, final String key, final char defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static long getExtraLong(final Intent intent, final String key, final long defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static CharSequence getExtraCharSequence(final Intent intent, final String key, final CharSequence defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static Bundle getExtraBundle(final Intent intent, final String key, final Bundle defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    public static <T extends Serializable> T getExtraSerializable(final Intent intent, final String key, final T defaultValue) {
        throw new UnsupportedOperationException("Just a stub for testing purposes");
    }

    private IntentHelper() {
        throw new AssertionError("No instances.");
    }
}
