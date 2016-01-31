package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraIntParameterTest {
    @Test
    public void testExtraInt() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraInt final int test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final int testExtra = IntentHelper.getIntExtra(intent, \"test\", 0);",
                "t.test(testExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraInt final int foo, @ExtraInt final int bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final int fooExtra = IntentHelper.getIntExtra(intent, \"foo\", 0);",
                "final int barExtra = IntentHelper.getIntExtra(intent, \"bar\", 0);",
                "t.test(fooExtra, barExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraIntSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraInt final int value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraInt final int value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final int valueExtra = IntentHelper.getIntExtra(intent, \"value\", 0);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraIntSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraInt final int test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraInt final int test, @ExtraInt final int foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final int testExtra = IntentHelper.getIntExtra(intent, \"test\", 0);",
                "t.foo(testExtra);",
                
                "final int fooExtra = IntentHelper.getIntExtra(intent, \"foo\", 0);",
                "t.bar(testExtra, fooExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraIntWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraInt final float test) {}"
        ).failsWithErrorMessage("@ExtraInt parameters must be of type int. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
