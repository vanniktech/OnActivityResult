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
                "final float testFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"test\", 0.0f);",
                "t.test(testFloatExtra_47602);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraFloatDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraFloat(defaultValue = 1) final float test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final float testFloatExtra_48563 = IntentHelper.getFloatExtra(intent, \"test\", 1.0f);",
                "t.test(testFloatExtra_48563);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraFloatDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraFloat().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraFloat final float test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraFloat(defaultValue = 1) final float test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraFloat(defaultValue = 2) final float test) {}",
            "@OnActivityResult(requestCode = 3) public void neg(@ExtraFloat(defaultValue = -3) final float test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final float testFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"test\", 0.0f);",
                "t.foobar(testFloatExtra_47602);",

                "final float testFloatExtra_48563 = IntentHelper.getFloatExtra(intent, \"test\", 1.0f);",
                "t.foo(testFloatExtra_48563);",

                "final float testFloatExtra_49524 = IntentHelper.getFloatExtra(intent, \"test\", 2.0f);",
                "t.bar(testFloatExtra_49524);",

                "final float testFloatExtra_1391080 = IntentHelper.getFloatExtra(intent, \"test\", -3.0f);",
                "t.neg(testFloatExtra_1391080);",
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
                "final float fooFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"foo\", 0.0f);",
                "final float barFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"bar\", 0.0f);",
                "t.test(fooFloatExtra_47602, barFloatExtra_47602);",
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
                "final float valueFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"value\", 0.0f);",
                "t.foo(valueFloatExtra_47602);",
                "t.bar(valueFloatExtra_47602);",
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
                "final float testFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"test\", 0.0f);",
                "t.foo(testFloatExtra_47602);",
                
                "final float fooFloatExtra_47602 = IntentHelper.getFloatExtra(intent, \"foo\", 0.0f);",
                "t.bar(testFloatExtra_47602, fooFloatExtra_47602);",
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
