package onactivityresult;

import org.junit.Test;

public class OnActivityResultExtraStringParameterTest {
    @Test
    public void testExtraString() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraString final String test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final String testExtra = IntentHelper.getStringExtra(intent, \"test\", null);",
                "t.test(testExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStrings() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraString final String foo, @ExtraString final String bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final String fooExtra = IntentHelper.getStringExtra(intent, \"foo\", null);",
                "final String barExtra = IntentHelper.getStringExtra(intent, \"bar\", null);",
                "t.test(fooExtra, barExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStringSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraString final String value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraString final String value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final String valueExtra = IntentHelper.getStringExtra(intent, \"value\", null);",
                "t.foo(valueExtra);",
                "t.bar(valueExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStringSameRequestCodeDifferentExtraInts() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraString final String test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraString final String test, @ExtraString final String foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final String testExtra = IntentHelper.getStringExtra(intent, \"test\", null);",
                "t.foo(testExtra);",
                
                "final String fooExtra = IntentHelper.getStringExtra(intent, \"foo\", null);",
                "t.bar(testExtra, fooExtra);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStringWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraString final double test) {}"
        ).failsWithErrorMessage("@ExtraString parameters must be of type java.lang.String. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
