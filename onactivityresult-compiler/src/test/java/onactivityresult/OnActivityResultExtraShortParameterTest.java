package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraShortParameterTest {
    @Test
    public void testExtraShort() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraShort final short test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final short testExtra = IntentHelper.getShortExtra(intent, \"test\", (short) 0);",
                "t.test(testExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShorts() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraShort final short foo, @ExtraShort final short bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final short fooExtra = IntentHelper.getShortExtra(intent, \"foo\", (short) 0);",
                "final short barExtra = IntentHelper.getShortExtra(intent, \"bar\", (short) 0);",
                "t.test(fooExtra, barExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShortSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraShort final short value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraShort final short value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final short valueExtra = IntentHelper.getShortExtra(intent, \"value\", (short) 0);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShortSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraShort final short test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraShort final short test, @ExtraShort final short foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final short testExtra = IntentHelper.getShortExtra(intent, \"test\", (short) 0);",
                "t.foo(testExtra);",
                
                "final short fooExtra = IntentHelper.getShortExtra(intent, \"foo\", (short) 0);",
                "t.bar(testExtra, fooExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShortWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraShort final double test) {}"
        ).failsWithErrorMessage("@ExtraShort parameters must be of type short. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
