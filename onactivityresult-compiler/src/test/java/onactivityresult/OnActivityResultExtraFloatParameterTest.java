package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraFloatParameterTest {
    @Test
    public void testExtraFloat() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraFloat final float test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final float testExtra = IntentHelper.getFloatExtra(intent, \"test\", 0.f);",
                "t.test(testExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraFloats() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraFloat final float foo, @ExtraFloat final float bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final float fooExtra = IntentHelper.getFloatExtra(intent, \"foo\", 0.f);",
                "final float barExtra = IntentHelper.getFloatExtra(intent, \"bar\", 0.f);",
                "t.test(fooExtra, barExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraFloatSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraFloat final float value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraFloat final float value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final float valueExtra = IntentHelper.getFloatExtra(intent, \"value\", 0.f);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraFloatSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraFloat final float test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraFloat final float test, @ExtraFloat final float foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final float testExtra = IntentHelper.getFloatExtra(intent, \"test\", 0.f);",
                "t.foo(testExtra);",
                
                "final float fooExtra = IntentHelper.getFloatExtra(intent, \"foo\", 0.f);",
                "t.bar(testExtra, fooExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraFloatWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraFloat final double test) {}"
        ).failsWithErrorMessage("@ExtraFloat parameters must be of type float. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
