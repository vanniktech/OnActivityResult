package onactivityresult;

import org.junit.Test;

public class OnActivityResultInvalidParametersTest {
    @Test
    public void testOnActivityResultMemberMethodDuplicatedIntArgumentsShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final int resultCode, final int foo) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (int, int). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultMemberMethodDuplicatedIntentArgumentsShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Intent intent, final Intent foo) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Intent, Intent). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentsShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Float myFloat) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Float). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentFollowingResultCodeShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final int resultCode, final Float myFloat) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (int, Float). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentFollowingIntentShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Intent intent, final Float myFloat) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Intent, Float). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentPrecedingResultCodeShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Double myDouble, final int resultCode) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Double, int). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentPrecedingIntentShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Double myDouble, final Intent intent) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Double, Intent). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }
}
