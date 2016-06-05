package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraBundleTest {
    @Test
    public void extra() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Bundle test) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 3) {",
                "final Bundle testExtraBundle = IntentHelper.getExtraBundle(intent, \"test\", null);",
                "t.test(testExtraBundle);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extras() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Bundle foo, @Extra final Bundle bar) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 3) {",
                "final Bundle fooExtraBundle = IntentHelper.getExtraBundle(intent, \"foo\", null);",
                "final Bundle barExtraBundle = IntentHelper.getExtraBundle(intent, \"bar\", null);",
                "t.test(fooExtraBundle, barExtraBundle);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Bundle value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Bundle value) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 5) {",
                "final Bundle valueExtraBundle = IntentHelper.getExtraBundle(intent, \"value\", null);",
                "t.foo(valueExtraBundle);",
                "t.bar(valueExtraBundle);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Bundle test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Bundle test, @Extra final Bundle foo) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 5) {",
                "final Bundle testExtraBundle = IntentHelper.getExtraBundle(intent, \"test\", null);",
                "t.foo(testExtraBundle);",

                "final Bundle fooExtraBundle = IntentHelper.getExtraBundle(intent, \"foo\", null);",
                "t.bar(testExtraBundle, fooExtraBundle);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
