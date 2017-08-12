package onactivityresult.internal;

import android.content.Intent;

/** just a stub **/
public interface IOnActivityResult<T> {
    boolean onResult(T t, int requestCode, int resultCode, Intent intent);
}
