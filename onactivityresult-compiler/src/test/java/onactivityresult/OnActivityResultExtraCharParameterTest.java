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
                "final char testExtra = IntentHelper.getCharExtra(intent, \"test\", (char) 0);",
                "t.test(testExtra);",
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
                "final char fooExtra = IntentHelper.getCharExtra(intent, \"foo\", (char) 0);",
                "final char barExtra = IntentHelper.getCharExtra(intent, \"bar\", (char) 0);",
                "t.test(fooExtra, barExtra);",
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
                "final char valueExtra = IntentHelper.getCharExtra(intent, \"value\", (char) 0);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
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
                "final char testExtra = IntentHelper.getCharExtra(intent, \"test\", (char) 0);",
                "t.foo(testExtra);",
                
                "final char fooExtra = IntentHelper.getCharExtra(intent, \"foo\", (char) 0);",
                "t.bar(testExtra, fooExtra);",
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
