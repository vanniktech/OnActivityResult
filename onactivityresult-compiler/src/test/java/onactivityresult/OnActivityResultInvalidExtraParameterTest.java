package onactivityresult;

import org.junit.Test;

public class OnActivityResultInvalidExtraParameterTest {
    @Test
    public void testUnsupportedFloatExtraParameterShouldLetProcessorFail() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void myFancyFunction(@Extra final Float myFloat) {}"
        ).failsWithErrorMessage("@Extra parameter does not support type java.lang.Float. (test.TestActivity.myFancyFunction)", 1);
        //@formatter:on
    }

    @Test
    public void testUnsupportedIntegerExtraParameterShouldLetProcessorFail() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void myFancyFunction(@Extra final Integer myInteger) {}"
        ).failsWithErrorMessage("@Extra parameter does not support type java.lang.Integer. (test.TestActivity.myFancyFunction)", 1);
        //@formatter:on
    }

    @Test
    public void testUnsupportedBooleanExtraParameterShouldLetProcessorFail() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void myFancyFunction(@Extra final Boolean myBoolean) {}"
        ).failsWithErrorMessage("@Extra parameter does not support type java.lang.Boolean. (test.TestActivity.myFancyFunction)", 1);
        //@formatter:on
    }

    @Test
    public void testUnsupportedLongExtraParameterShouldLetProcessorFail() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void myFancyFunction(@Extra final Long myLong) {}"
        ).failsWithErrorMessage("@Extra parameter does not support type java.lang.Long. (test.TestActivity.myFancyFunction)", 1);
        //@formatter:on
    }
}
