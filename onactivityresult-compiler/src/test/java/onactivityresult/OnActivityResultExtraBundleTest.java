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
                "final Bundle testExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"test\", null);",
                "t.test(testExtraBundle_3392903);",

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
                "final Bundle fooExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"foo\", null);",
                "final Bundle barExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"bar\", null);",
                "t.test(fooExtraBundle_3392903, barExtraBundle_3392903);",

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
                "final Bundle valueExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"value\", null);",
                "t.foo(valueExtraBundle_3392903);",
                "t.bar(valueExtraBundle_3392903);",

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
                "final Bundle testExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"test\", null);",
                "t.foo(testExtraBundle_3392903);",

                "final Bundle fooExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"foo\", null);",
                "t.bar(testExtraBundle_3392903, fooExtraBundle_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
