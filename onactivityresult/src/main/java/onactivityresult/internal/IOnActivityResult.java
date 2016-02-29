package onactivityresult.internal;

import android.content.Intent;

public interface IOnActivityResult<T> {
    boolean onResult(final T t, final int requestCode, final int resultCode, final Intent intent);
}
