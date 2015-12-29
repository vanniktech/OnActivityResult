package onactivityresult;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import onactivityresult.internal.IOnActivityResult;

public final class ActivityResult {
    private static final String ACTIVITY_RESULT_CLASS_SUFFIX = "$$OnActivityResult";

    static IOnActivityResult<Object> createOnActivityResultClassFor(final Object object) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final Class<?> target = object.getClass();
        final String targetClassName = target.getName();
        final Class<?> activityResultClass = Class.forName(targetClassName + ACTIVITY_RESULT_CLASS_SUFFIX);
        // noinspection unchecked
        return (IOnActivityResult<Object>) activityResultClass.newInstance();
    }

    public static OnResult onResult(final int requestCode, final int resultCode, @Nullable final Intent intent) {
        return new OnResult(requestCode, resultCode, intent);
    }

    private ActivityResult() {
        throw new AssertionError("No instances.");
    }

    public static final class OnResult {
        private final int              mRequestCode;
        private final int              mResultCode;
        @Nullable private final Intent mIntent;

        OnResult(final int requestCode, final int resultCode, @Nullable final Intent intent) {
            mRequestCode = requestCode;
            mResultCode = resultCode;
            mIntent = intent;
        }

        public <T> void into(@NonNull final T object) {
            final IOnActivityResult<Object> onActivityResult;

            try {
                onActivityResult = ActivityResult.createOnActivityResultClassFor(object);
            } catch (final ClassNotFoundException classNotFound) {
                throw new ActivityResultRuntimeException("Could not find OnActivityResult class for " + object.getClass().getName(), classNotFound);
            } catch (final IllegalAccessException illegalAccessException) {
                throw new ActivityResultRuntimeException("Can't create OnActivityResult class for " + object.getClass().getName(), illegalAccessException);
            } catch (final InstantiationException instantiationException) {
                throw new ActivityResultRuntimeException("Exception when handling IOnActivityResult " + instantiationException.getMessage(), instantiationException);
            }

            onActivityResult.onResult(object, mRequestCode, mResultCode, mIntent);
        }
    }

    public static class ActivityResultRuntimeException extends RuntimeException {
        public ActivityResultRuntimeException(final String detailMessage, final Throwable throwable) {
            super(detailMessage, throwable);
        }
    }
}
