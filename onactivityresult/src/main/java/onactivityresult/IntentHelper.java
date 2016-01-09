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
