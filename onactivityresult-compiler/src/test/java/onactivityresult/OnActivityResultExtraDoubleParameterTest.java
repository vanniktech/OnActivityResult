package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraDoubleParameterTest {
    @Test
    public void testExtraDouble() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraDouble final double test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final double testDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"test\", 0d);",
                "t.test(testDoubleExtra_47602);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDoubleDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraDouble(defaultValue = 1) final double test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final double testDoubleExtra_48563 = IntentHelper.getDoubleExtra(intent, \"test\", 1d);",
                "t.test(testDoubleExtra_48563);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDoubleDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraDouble final double test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraDouble(defaultValue = 1) final double test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraDouble(defaultValue = 2) final double test) {}",
            "@OnActivityResult(requestCode = 3) public void neg(@ExtraDouble(defaultValue = -3) final double test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final double testDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"test\", 0d);",
                "t.foobar(testDoubleExtra_47602);",

                "final double testDoubleExtra_48563 = IntentHelper.getDoubleExtra(intent, \"test\", 1d);",
                "t.foo(testDoubleExtra_48563);",

                "final double testDoubleExtra_49524 = IntentHelper.getDoubleExtra(intent, \"test\", 2d);",
                "t.bar(testDoubleExtra_49524);",

                "final double testDoubleExtra_1391080 = IntentHelper.getDoubleExtra(intent, \"test\", -3d);",
                "t.neg(testDoubleExtra_1391080);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDoubles() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraDouble final double foo, @ExtraDouble final double bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final double fooDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"foo\", 0d);",
                "final double barDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"bar\", 0d);",
                "t.test(fooDoubleExtra_47602, barDoubleExtra_47602);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDoubleSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraDouble final double value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraDouble final double value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final double valueDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"value\", 0d);",
                "t.foo(valueDoubleExtra_47602);",
                "t.bar(valueDoubleExtra_47602);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDoubleSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraDouble final double test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraDouble final double test, @ExtraDouble final double foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final double testDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"test\", 0d);",
                "t.foo(testDoubleExtra_47602);",
                
                "final double fooDoubleExtra_47602 = IntentHelper.getDoubleExtra(intent, \"foo\", 0d);",
                "t.bar(testDoubleExtra_47602, fooDoubleExtra_47602);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDoubleWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraDouble().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraDouble final float test) {}"
        ).failsWithErrorMessage("@ExtraDouble parameters must be of type double. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
