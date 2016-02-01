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
                "final long testExtra = IntentHelper.getLongExtra(intent, \"test\", 0L);",
                "t.test(testExtra);",
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
                "final long fooExtra = IntentHelper.getLongExtra(intent, \"foo\", 0L);",
                "final long barExtra = IntentHelper.getLongExtra(intent, \"bar\", 0L);",
                "t.test(fooExtra, barExtra);",
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
                "final long valueExtra = IntentHelper.getLongExtra(intent, \"value\", 0L);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
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
                "final long testExtra = IntentHelper.getLongExtra(intent, \"test\", 0L);",
                "t.foo(testExtra);",
                
                "final long fooExtra = IntentHelper.getLongExtra(intent, \"foo\", 0L);",
                "t.bar(testExtra, fooExtra);",
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
