package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraLongParameterTest {
    @Test
    public void testExtraLong() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraLong final long test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final long testLongExtra_48 = IntentHelper.getLongExtra(intent, \"test\", 0L);",
                "t.test(testLongExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraLongDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraLong(defaultValue = 1) final long test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final long testLongExtra_49 = IntentHelper.getLongExtra(intent, \"test\", 1L);",
                "t.test(testLongExtra_49);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraLongDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraLong final long test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraLong(defaultValue = 1) final long test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraLong(defaultValue = 2) final long test) {}",
            "@OnActivityResult(requestCode = 3) public void neg(@ExtraLong(defaultValue = -3) final long test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final long testLongExtra_48 = IntentHelper.getLongExtra(intent, \"test\", 0L);",
                "t.foobar(testLongExtra_48);",

                "final long testLongExtra_49 = IntentHelper.getLongExtra(intent, \"test\", 1L);",
                "t.foo(testLongExtra_49);",

                "final long testLongExtra_50 = IntentHelper.getLongExtra(intent, \"test\", 2L);",
                "t.bar(testLongExtra_50);",

                "final long testLongExtra_1446 = IntentHelper.getLongExtra(intent, \"test\", -3L);",
                "t.neg(testLongExtra_1446);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraLongs() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraLong final long foo, @ExtraLong final long bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final long fooLongExtra_48 = IntentHelper.getLongExtra(intent, \"foo\", 0L);",
                "final long barLongExtra_48 = IntentHelper.getLongExtra(intent, \"bar\", 0L);",
                "t.test(fooLongExtra_48, barLongExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraLongSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraLong final long value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraLong final long value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final long valueLongExtra_48 = IntentHelper.getLongExtra(intent, \"value\", 0L);",
                "t.foo(valueLongExtra_48);",
                "t.bar(valueLongExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraLongSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraLong final long test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraLong final long test, @ExtraLong final long foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final long testLongExtra_48 = IntentHelper.getLongExtra(intent, \"test\", 0L);",
                "t.foo(testLongExtra_48);",
                
                "final long fooLongExtra_48 = IntentHelper.getLongExtra(intent, \"foo\", 0L);",
                "t.bar(testLongExtra_48, fooLongExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraLongWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraLong().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraLong final double test) {}"
        ).failsWithErrorMessage("@ExtraLong parameters must be of type long. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
