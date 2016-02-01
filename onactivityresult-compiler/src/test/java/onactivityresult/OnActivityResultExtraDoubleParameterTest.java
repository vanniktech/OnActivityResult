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
                "final double testExtra = IntentHelper.getDoubleExtra(intent, \"test\", 0d);",
                "t.test(testExtra);",
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
                "final double fooExtra = IntentHelper.getDoubleExtra(intent, \"foo\", 0d);",
                "final double barExtra = IntentHelper.getDoubleExtra(intent, \"bar\", 0d);",
                "t.test(fooExtra, barExtra);",
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
                "final double valueExtra = IntentHelper.getDoubleExtra(intent, \"value\", 0d);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
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
                "final double testExtra = IntentHelper.getDoubleExtra(intent, \"test\", 0d);",
                "t.foo(testExtra);",
                
                "final double fooExtra = IntentHelper.getDoubleExtra(intent, \"foo\", 0d);",
                "t.bar(testExtra, fooExtra);",
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
