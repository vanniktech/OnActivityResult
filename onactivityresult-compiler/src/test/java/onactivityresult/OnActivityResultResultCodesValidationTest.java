package onactivityresult;

import org.junit.Test;

public class OnActivityResultResultCodesValidationTest {
    @Test
    public void negativeInvalidFilterResultCodes() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 1, resultCodes = {-2}) void myOnActivityResult() { }"
        ).failsWithErrorMessage("Invalid resultCode -2. (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void activityResultDuplicatedFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        TestActivity.create().hasActivity().build(
            "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_CANCELED, Activity.RESULT_CANCELED }) void myOnActivityResult() { }"
        ).failsWithErrorMessage("Duplicate resultCode 0. (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void activityResultCanceledFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        TestActivity.create().hasActivity().build(
            "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_CANCELED }) void myOnActivityResult() { }"
        ).succeeds();
        //@formatter:on
    }

    @Test
    public void activityResultOkFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        TestActivity.create().hasActivity().build(
            "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_OK }) void myOnActivityResult() { }"
        ).succeeds();
        //@formatter:on
    }

    @Test
    public void activityResultFirstUserFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        TestActivity.create().hasActivity().build(
            "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_FIRST_USER }) void myOnActivityResult() { }"
        ).succeeds();
        //@formatter:on
    }

    @Test
    public void activityResultUserDefinedFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        TestActivity.create().hasActivity().build(
                "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_FIRST_USER + 1 }) void myOnActivityResult() { }"
        ).succeeds();
        //@formatter:on
    }
}
