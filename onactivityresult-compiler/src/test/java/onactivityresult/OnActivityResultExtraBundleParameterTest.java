package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraBundleParameterTest {
    @Test
    public void testExtraBundle() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Bundle test) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 3) {",
                "final Bundle testBundleExtra_3392903 = IntentHelper.getBundleExtra(intent, \"test\", null);",
                "t.test(testBundleExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBundles() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Bundle foo, @Extra final Bundle bar) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 3) {",
                "final Bundle fooBundleExtra_3392903 = IntentHelper.getBundleExtra(intent, \"foo\", null);",
                "final Bundle barBundleExtra_3392903 = IntentHelper.getBundleExtra(intent, \"bar\", null);",
                "t.test(fooBundleExtra_3392903, barBundleExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBundleSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Bundle value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Bundle value) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 5) {",
                "final Bundle valueBundleExtra_3392903 = IntentHelper.getBundleExtra(intent, \"value\", null);",
                "t.foo(valueBundleExtra_3392903);",
                "t.bar(valueBundleExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraBundleSameRequestCodeDifferentExtraBundles() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Bundle").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Bundle test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Bundle test, @Extra final Bundle foo) {}"
        ).needsBundle().generatesBody(
            "if (requestCode == 5) {",
                "final Bundle testBundleExtra_3392903 = IntentHelper.getBundleExtra(intent, \"test\", null);",
                "t.foo(testBundleExtra_3392903);",
                
                "final Bundle fooBundleExtra_3392903 = IntentHelper.getBundleExtra(intent, \"foo\", null);",
                "t.bar(testBundleExtra_3392903, fooBundleExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
