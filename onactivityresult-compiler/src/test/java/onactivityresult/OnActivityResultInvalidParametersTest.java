package onactivityresult;

import org.junit.Test;

public class OnActivityResultInvalidParametersTest {
    @Test
    public void memberMethodDuplicatedIntArguments() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final int resultCode, final int foo) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (int, int). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void memberMethodDuplicatedIntentArguments() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Intent intent, final Intent foo) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Intent, Intent). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void memberMethodIncorrectArguments() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Float myFloat) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Float). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void memberMethodIncorrectArgumentFollowingResultCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final int resultCode, final Float myFloat) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (int, Float). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void memberMethodIncorrectArgumentFollowingIntent() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Intent intent, final Float myFloat) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Intent, Float). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void memberMethodIncorrectArgumentPrecedingResultCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Double myDouble, final int resultCode) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Double, int). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void memberMethodIncorrectArgumentPrecedingIntent() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Double myDouble, final Intent intent) {}"
        ).failsWithErrorMessage("@OnActivityResult methods do not support the following parameter(s) - (Double, Intent). (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void unsupportedExtraParameter() {
        //@formatter:off
        TestActivity.create().hasExtra().withExtraCode("class UnsupportedClass {}").build(
            "@OnActivityResult(requestCode = 3) public void myFancyFunction(@Extra final UnsupportedClass unsupported) {}"
        ).failsWithErrorMessage("@Extra parameter does not support type test.UnsupportedClass. (test.TestActivity.myFancyFunction)", 1);
        //@formatter:on
    }
}
