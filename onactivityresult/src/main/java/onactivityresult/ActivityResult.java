package onactivityresult;

import java.util.LinkedHashMap;
import java.util.Map;

import onactivityresult.internal.IOnActivityResult;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class ActivityResult {
    private static final String ACTIVITY_RESULT_CLASS_SUFFIX = "$$OnActivityResult";

    static final Map<Class<?>, IOnActivityResult<Object>> ON_ACTIVITY_RESULTS = new LinkedHashMap<>();

    @NonNull
    static IOnActivityResult<Object> createOnActivityResultClassFor(final Object object) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        final IOnActivityResult<Object> activityResultForClass = findActivityResultForClass(object.getClass());

        if (activityResultForClass == null) {
            throw new ClassNotFoundException();
        }

        return activityResultForClass;
    }

    @Nullable
    private static IOnActivityResult<Object> findActivityResultForClass(final Class<?> clazz) throws IllegalAccessException, InstantiationException {
        final IOnActivityResult<Object> cachedOnActivityResult = ON_ACTIVITY_RESULTS.get(clazz);

        if (cachedOnActivityResult != null) {
            return cachedOnActivityResult;
        }

        final String className = clazz.getName();

        if (className.startsWith("android.") || className.startsWith("java.")) {
            return null;
        }

        IOnActivityResult<Object> onActivityResult;

        try {
            final Class<?> onActivityResultClass = clazz.getClassLoader().loadClass(className + ACTIVITY_RESULT_CLASS_SUFFIX);
            // noinspection unchecked
            onActivityResult = (IOnActivityResult<Object>) onActivityResultClass.newInstance();
        } catch (final ClassNotFoundException ignore) {
            onActivityResult = findActivityResultForClass(clazz.getSuperclass());
        }

        ON_ACTIVITY_RESULTS.put(clazz, onActivityResult);

        return onActivityResult;
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

        /**
         * @param object with annotated {@link OnActivityResult} methods which will be called depending on the given parameters
         * @return whether or not a function was called for the given parameters
         */
        public <T> boolean into(@NonNull final T object) {
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

            return onActivityResult.onResult(object, mRequestCode, mResultCode, mIntent);
        }
    }

    public static class ActivityResultRuntimeException extends RuntimeException {
        public ActivityResultRuntimeException(final String detailMessage, final Throwable throwable) {
            super(detailMessage, throwable);
        }
    }
}
