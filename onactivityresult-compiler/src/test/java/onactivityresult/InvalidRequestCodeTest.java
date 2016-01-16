package onactivityresult;

import org.junit.Test;

public class InvalidRequestCodeTest {
    @Test
    public void testOnActivityResultRequestCodeMinus1ShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = -1) public void minusOnActivityResult() {}"
        ).failsWithErrorMessage("RequestCodes must be >= 0. (test.TestActivity.minusOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultRequestCodeIntegerMinShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = Integer.MIN_VALUE) public void integerMinOnActivityResult() {}"
        ).failsWithErrorMessage("RequestCodes must be >= 0. (test.TestActivity.integerMinOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultRequestCodeZeroShouldNotLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
                "@OnActivityResult(requestCode = 0) public void integerMinOnActivityResult() {}"
        ).succeeds();
        //@formatter:on
    }
}
