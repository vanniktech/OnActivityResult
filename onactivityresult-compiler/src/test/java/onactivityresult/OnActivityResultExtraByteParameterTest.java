package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraByteParameterTest {
    @Test
    public void testExtraByte() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraByte final byte test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final byte testByteExtra_48 = IntentHelper.getByteExtra(intent, \"test\", (byte) 0);",
                "t.test(testByteExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraByteDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraByte(defaultValue = 1) final byte test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final byte testByteExtra_49 = IntentHelper.getByteExtra(intent, \"test\", (byte) 1);",
                "t.test(testByteExtra_49);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraByteDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraByte final byte test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraByte(defaultValue = 1) final byte test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraByte(defaultValue = 2) final byte test) {}",
            "@OnActivityResult(requestCode = 3) public void neg(@ExtraByte(defaultValue = -3) final byte test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final byte testByteExtra_48 = IntentHelper.getByteExtra(intent, \"test\", (byte) 0);",
                "t.foobar(testByteExtra_48);",

                "final byte testByteExtra_49 = IntentHelper.getByteExtra(intent, \"test\", (byte) 1);",
                "t.foo(testByteExtra_49);",

                "final byte testByteExtra_50 = IntentHelper.getByteExtra(intent, \"test\", (byte) 2);",
                "t.bar(testByteExtra_50);",

                "final byte testByteExtra_1446 = IntentHelper.getByteExtra(intent, \"test\", (byte) -3);",
                "t.neg(testByteExtra_1446);",
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
                "final byte fooByteExtra_48 = IntentHelper.getByteExtra(intent, \"foo\", (byte) 0);",
                "final byte barByteExtra_48 = IntentHelper.getByteExtra(intent, \"bar\", (byte) 0);",
                "t.test(fooByteExtra_48, barByteExtra_48);",
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
                "final byte valueByteExtra_48 = IntentHelper.getByteExtra(intent, \"value\", (byte) 0);",
                "t.foo(valueByteExtra_48);",
                "t.bar(valueByteExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraByteSameRequestCodeDifferentExtraBytes() {
        //@formatter:off
        TestActivity.create().hasExtraByte().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraByte final byte test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraByte final byte test, @ExtraByte final byte foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final byte testByteExtra_48 = IntentHelper.getByteExtra(intent, \"test\", (byte) 0);",
                "t.foo(testByteExtra_48);",
                
                "final byte fooByteExtra_48 = IntentHelper.getByteExtra(intent, \"foo\", (byte) 0);",
                "t.bar(testByteExtra_48, fooByteExtra_48);",
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
