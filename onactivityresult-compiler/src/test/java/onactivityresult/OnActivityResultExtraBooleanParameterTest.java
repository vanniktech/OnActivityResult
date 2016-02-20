package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraBooleanParameterTest {
    @Test
    public void testExtraBoolean() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean final boolean test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean testBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"test\", false);",
                "t.test(testBooleanExtra_97196323);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanDefaultValue() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean(defaultValue = true) final boolean test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean testBooleanExtra_3569038 = IntentHelper.getBooleanExtra(intent, \"test\", true);",
                "t.test(testBooleanExtra_3569038);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void foobar(@ExtraBoolean final boolean test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@ExtraBoolean(defaultValue = false) final boolean test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@ExtraBoolean(defaultValue = true) final boolean test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean testBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"test\", false);",
                "t.foobar(testBooleanExtra_97196323);",

                "t.foo(testBooleanExtra_97196323);",

                "final boolean testBooleanExtra_3569038 = IntentHelper.getBooleanExtra(intent, \"test\", true);",
                "t.bar(testBooleanExtra_3569038);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleans() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean final boolean foo, @ExtraBoolean final boolean bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean fooBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"foo\", false);",
                "final boolean barBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"bar\", false);",
                "t.test(fooBooleanExtra_97196323, barBooleanExtra_97196323);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraBoolean final boolean value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraBoolean final boolean value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final boolean valueBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"value\", false);",
                "t.foo(valueBooleanExtra_97196323);",
                "t.bar(valueBooleanExtra_97196323);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanSameRequestCodeDifferentExtraBooleans() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 5) public void foo(@ExtraBoolean final boolean test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@ExtraBoolean final boolean test, @ExtraBoolean final boolean foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final boolean testBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"test\", false);",
                "t.foo(testBooleanExtra_97196323);",
                
                "final boolean fooBooleanExtra_97196323 = IntentHelper.getBooleanExtra(intent, \"foo\", false);",
                "t.bar(testBooleanExtra_97196323, fooBooleanExtra_97196323);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBooleanWrongType() {
        //@formatter:off
        TestActivity.create().hasExtraBoolean().build(
            "@OnActivityResult(requestCode = 3) public void test(@ExtraBoolean final double test) {}"
        ).failsWithErrorMessage("@ExtraBoolean parameters must be of type boolean. (test.TestActivity.test)", 1);
        //@formatter:on
    }
}
