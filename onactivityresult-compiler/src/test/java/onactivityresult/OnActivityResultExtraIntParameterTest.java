package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraIntParameterTest {
    @Test
    public void testExtraInt() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraInt final int test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final int testIntExtra_48 = IntentHelper.getIntExtra(intent, \"test\", 0);",
                "t.test(testIntExtra_48);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraIntDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraInt(defaultValue = 1) final int test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final int testIntExtra_49 = IntentHelper.getIntExtra(intent, \"test\", 1);",
                "t.test(testIntExtra_49);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraIntDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraInt().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraInt final int test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraInt(defaultValue = 1) final int test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraInt(defaultValue = 2) final int test) {}",
            "@OnActivityResult(requestCode = 3) public void neg(@ExtraInt(defaultValue = -3) final int test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final int testIntExtra_48 = IntentHelper.getIntExtra(intent, \"test\", 0);",
                "t.foobar(testIntExtra_48);",

                "final int testIntExtra_49 = IntentHelper.getIntExtra(intent, \"test\", 1);",
                "t.foo(testIntExtra_49);",

                "final int testIntExtra_50 = IntentHelper.getIntExtra(intent, \"test\", 2);",
                "t.bar(testIntExtra_50);",

                "final int testIntExtra_1446 = IntentHelper.getIntExtra(intent, \"test\", -3);",
                "t.neg(testIntExtra_1446);",

                "didHandle = true;",
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
                "final int fooIntExtra_48 = IntentHelper.getIntExtra(intent, \"foo\", 0);",
                "final int barIntExtra_48 = IntentHelper.getIntExtra(intent, \"bar\", 0);",
                "t.test(fooIntExtra_48, barIntExtra_48);",

                "didHandle = true;",
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
                "final int valueIntExtra_48 = IntentHelper.getIntExtra(intent, \"value\", 0);",
                "t.foo(valueIntExtra_48);",
                "t.bar(valueIntExtra_48);",

                "didHandle = true;",
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
                "final int testIntExtra_48 = IntentHelper.getIntExtra(intent, \"test\", 0);",
                "t.foo(testIntExtra_48);",
                
                "final int fooIntExtra_48 = IntentHelper.getIntExtra(intent, \"foo\", 0);",
                "t.bar(testIntExtra_48, fooIntExtra_48);",

                "didHandle = true;",
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
