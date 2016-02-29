package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultParameterIntentResultCodeTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void test(final Intent intent, final int resultCode) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test(intent, resultCode);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(final Intent intent, final int resultCode) {}",
            "@OnActivityResult(requestCode = 10) public void foo(final Intent intent, final int resultCode) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(intent, resultCode);",
                "t.foo(intent, resultCode);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(final Intent intent, final int resultCode) {}",
            "@OnActivityResult(requestCode = 11) public void foo(final Intent intent, final int resultCode) {}",
            "@OnActivityResult(requestCode = 12) public void abc(final Intent intent, final int resultCode) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(intent, resultCode);",

                "didHandle = true;",
            "} else if (requestCode == 11) {",
                "t.foo(intent, resultCode);",

                "didHandle = true;",
            "} else if (requestCode == 12) {",
                "t.abc(intent, resultCode);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
