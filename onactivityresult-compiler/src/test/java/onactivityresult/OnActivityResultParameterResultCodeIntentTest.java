package onactivityresult;

import org.junit.Test;

public class OnActivityResultParameterResultCodeIntentTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void test(final int resultCode, final Intent intent) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test(resultCode, intent);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(final int resultCode, final Intent intent) {}",
            "@OnActivityResult(requestCode = 10) public void foo(final int resultCode, final Intent intent) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(resultCode, intent);",
                "t.foo(resultCode, intent);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(final int resultCode, final Intent intent) {}",
            "@OnActivityResult(requestCode = 11) public void foo(final int resultCode, final Intent intent) {}",
            "@OnActivityResult(requestCode = 12) public void abc(final int resultCode, final Intent intent) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(resultCode, intent);",
            "} else if (requestCode == 11) {",
                "t.foo(resultCode, intent);",
            "} else if (requestCode == 12) {",
                "t.abc(resultCode, intent);",
            "}"
        );
        //@formatter:on
    }
}
