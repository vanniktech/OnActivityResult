package onactivityresult.internal;

import android.content.Intent;

public interface IOnActivityResult<T> {
    boolean onResult(T t, int requestCode, int resultCode, Intent intent);
}
