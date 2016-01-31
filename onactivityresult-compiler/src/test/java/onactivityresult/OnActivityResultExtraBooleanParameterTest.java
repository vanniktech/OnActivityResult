package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraBooleanParameterTest {
    @Test
    public void testExtraBoolean() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean final boolean test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean testExtra = IntentHelper.getBooleanExtra(intent, \"test\", false);",
                "t.test(testExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleans() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean final boolean foo, @ExtraBoolean final boolean bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean fooExtra = IntentHelper.getBooleanExtra(intent, \"foo\", false);",
                "final boolean barExtra = IntentHelper.getBooleanExtra(intent, \"bar\", false);",
                "t.test(fooExtra, barExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraBoolean final boolean value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraBoolean final boolean value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final boolean valueExtra = IntentHelper.getBooleanExtra(intent, \"value\", false);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraBoolean final boolean test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraBoolean final boolean test, @ExtraBoolean final boolean foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final boolean testExtra = IntentHelper.getBooleanExtra(intent, \"test\", false);",
                "t.foo(testExtra);",
                
                "final boolean fooExtra = IntentHelper.getBooleanExtra(intent, \"foo\", false);",
                "t.bar(testExtra, fooExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean final double test) {}"
        ).failsWithErrorMessage("@ExtraBoolean parameters must be of type boolean. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
