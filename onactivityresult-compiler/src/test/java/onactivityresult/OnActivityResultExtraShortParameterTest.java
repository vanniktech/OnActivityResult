package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraShortParameterTest {
    @Test
    public void testExtraShort() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraShort final short test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final short testShortExtra_48 = IntentHelper.getShortExtra(intent, \"test\", (short) 0);",
                "t.test(testShortExtra_48);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShortDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraShort(defaultValue = 1) final short test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final short testShortExtra_49 = IntentHelper.getShortExtra(intent, \"test\", (short) 1);",
                "t.test(testShortExtra_49);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShortDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraShort final short test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraShort(defaultValue = 1) final short test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraShort(defaultValue = 2) final short test) {}",
            "@OnActivityResult(requestCode = 3) public void neg(@ExtraShort(defaultValue = -3) final short test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final short testShortExtra_48 = IntentHelper.getShortExtra(intent, \"test\", (short) 0);",
                "t.foobar(testShortExtra_48);",

                "final short testShortExtra_49 = IntentHelper.getShortExtra(intent, \"test\", (short) 1);",
                "t.foo(testShortExtra_49);",

                "final short testShortExtra_50 = IntentHelper.getShortExtra(intent, \"test\", (short) 2);",
                "t.bar(testShortExtra_50);",

                "final short testShortExtra_1446 = IntentHelper.getShortExtra(intent, \"test\", (short) -3);",
                "t.neg(testShortExtra_1446);",

                "didHandle = true;",
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
                "final short fooShortExtra_48 = IntentHelper.getShortExtra(intent, \"foo\", (short) 0);",
                "final short barShortExtra_48 = IntentHelper.getShortExtra(intent, \"bar\", (short) 0);",
                "t.test(fooShortExtra_48, barShortExtra_48);",

                "didHandle = true;",
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
                "final short valueShortExtra_48 = IntentHelper.getShortExtra(intent, \"value\", (short) 0);",
                "t.foo(valueShortExtra_48);",
                "t.bar(valueShortExtra_48);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraShortSameRequestCodeDifferentExtraShorts() {
        //@formatter:off
        TestActivity.create().hasExtraShort().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraShort final short test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraShort final short test, @ExtraShort final short foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final short testShortExtra_48 = IntentHelper.getShortExtra(intent, \"test\", (short) 0);",
                "t.foo(testShortExtra_48);",
                
                "final short fooShortExtra_48 = IntentHelper.getShortExtra(intent, \"foo\", (short) 0);",
                "t.bar(testShortExtra_48, fooShortExtra_48);",

                "didHandle = true;",
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
