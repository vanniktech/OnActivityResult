package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraStringParameterTest {
    @Test
    public void testExtraString() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraString final String test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final String testStringExtra_0 = IntentHelper.getStringExtra(intent, \"test\", \"\");",
                "t.test(testStringExtra_0);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStringDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraString(defaultValue = \"defaultValue\") final String test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final String testStringExtra_N659125328 = IntentHelper.getStringExtra(intent, \"test\", \"defaultValue\");",
                "t.test(testStringExtra_N659125328);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStringDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraString final String test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraString(defaultValue = \"defaultValue\") final String test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraString(defaultValue = \"abcdef\") final String test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final String testStringExtra_0 = IntentHelper.getStringExtra(intent, \"test\", \"\");",
                "t.foobar(testStringExtra_0);",

                "final String testStringExtra_N659125328 = IntentHelper.getStringExtra(intent, \"test\", \"defaultValue\");",
                "t.foo(testStringExtra_N659125328);",

                "final String testStringExtra_N1424385949 = IntentHelper.getStringExtra(intent, \"test\", \"abcdef\");",
                "t.bar(testStringExtra_N1424385949);",

                "didHandle = true;",
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
                "final String fooStringExtra_0 = IntentHelper.getStringExtra(intent, \"foo\", \"\");",
                "final String barStringExtra_0 = IntentHelper.getStringExtra(intent, \"bar\", \"\");",
                "t.test(fooStringExtra_0, barStringExtra_0);",

                "didHandle = true;",
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
                "final String valueStringExtra_0 = IntentHelper.getStringExtra(intent, \"value\", \"\");",
                "t.foo(valueStringExtra_0);",
                "t.bar(valueStringExtra_0);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraStringSameRequestCodeDifferentExtraStrings() {
        //@formatter:off
        TestActivity.create().hasExtraString().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraString final String test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraString final String test, @ExtraString final String foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final String testStringExtra_0 = IntentHelper.getStringExtra(intent, \"test\", \"\");",
                "t.foo(testStringExtra_0);",
                
                "final String fooStringExtra_0 = IntentHelper.getStringExtra(intent, \"foo\", \"\");",
                "t.bar(testStringExtra_0, fooStringExtra_0);",

                "didHandle = true;",
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
