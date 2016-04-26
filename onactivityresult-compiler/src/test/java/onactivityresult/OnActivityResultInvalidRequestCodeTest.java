package onactivityresult;

import org.junit.Test;

public class OnActivityResultInvalidRequestCodeTest {
    @Test
    public void requestCodeMinus1() {
        TestActivity.create().build("@OnActivityResult(requestCode = -1) public void minusOnActivityResult() {}").failsWithErrorMessage("RequestCodes must be >= 0. (test.TestActivity.minusOnActivityResult)", 1);
    }

    @Test
    public void requestCodeIntegerMin() {
        TestActivity.create().build("@OnActivityResult(requestCode = Integer.MIN_VALUE) public void integerMinOnActivityResult() {}").failsWithErrorMessage("RequestCodes must be >= 0. (test.TestActivity.integerMinOnActivityResult)", 1);
    }

    @Test
    public void requestCodeZero() {
        TestActivity.create().build("@OnActivityResult(requestCode = 0) public void integerMinOnActivityResult() {}").succeeds();
    }
}
