package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraCharParameterTest {
    @Test
    public void testExtraChar() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraChar final char test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final char testCharExtra_48 = IntentHelper.getCharExtra(intent, \"test\", (char) 0);",
                "t.test(testCharExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraChar(defaultValue = 1) final char test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final char testCharExtra_49 = IntentHelper.getCharExtra(intent, \"test\", (char) 1);",
                "t.test(testCharExtra_49);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraChar final char test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraChar(defaultValue = 1) final char test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraChar(defaultValue = 2) final char test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final char testCharExtra_48 = IntentHelper.getCharExtra(intent, \"test\", (char) 0);",
                "t.foobar(testCharExtra_48);",

                "final char testCharExtra_49 = IntentHelper.getCharExtra(intent, \"test\", (char) 1);",
                "t.foo(testCharExtra_49);",

                "final char testCharExtra_50 = IntentHelper.getCharExtra(intent, \"test\", (char) 2);",
                "t.bar(testCharExtra_50);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraChars() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraChar final char foo, @ExtraChar final char bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final char fooCharExtra_48 = IntentHelper.getCharExtra(intent, \"foo\", (char) 0);",
                "final char barCharExtra_48 = IntentHelper.getCharExtra(intent, \"bar\", (char) 0);",
                "t.test(fooCharExtra_48, barCharExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraChar final char value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraChar final char value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final char valueCharExtra_48 = IntentHelper.getCharExtra(intent, \"value\", (char) 0);",
                "t.foo(valueCharExtra_48);",
                "t.bar(valueCharExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraChar final char test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraChar final char test, @ExtraChar final char foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final char testCharExtra_48 = IntentHelper.getCharExtra(intent, \"test\", (char) 0);",
                "t.foo(testCharExtra_48);",
                
                "final char fooCharExtra_48 = IntentHelper.getCharExtra(intent, \"foo\", (char) 0);",
                "t.bar(testCharExtra_48, fooCharExtra_48);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraChar().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraChar final double test) {}"
        ).failsWithErrorMessage("@ExtraChar parameters must be of type char. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
