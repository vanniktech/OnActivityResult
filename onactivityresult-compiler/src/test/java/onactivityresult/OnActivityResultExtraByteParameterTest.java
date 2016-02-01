package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraByteParameterTest {
    @Test
    public void testExtraByte() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraByte final byte test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final byte testExtra = IntentHelper.getByteExtra(intent, \"test\", (byte) 0);",
                "t.test(testExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBytes() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraByte final byte foo, @ExtraByte final byte bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final byte fooExtra = IntentHelper.getByteExtra(intent, \"foo\", (byte) 0);",
                "final byte barExtra = IntentHelper.getByteExtra(intent, \"bar\", (byte) 0);",
                "t.test(fooExtra, barExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraByteSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraByte final byte value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraByte final byte value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final byte valueExtra = IntentHelper.getByteExtra(intent, \"value\", (byte) 0);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraByteSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraByte final byte test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraByte final byte test, @ExtraByte final byte foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final byte testExtra = IntentHelper.getByteExtra(intent, \"test\", (byte) 0);",
                "t.foo(testExtra);",
                
                "final byte fooExtra = IntentHelper.getByteExtra(intent, \"foo\", (byte) 0);",
                "t.bar(testExtra, fooExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraByteWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraByte final double test) {}"
        ).failsWithErrorMessage("@ExtraByte parameters must be of type byte. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
