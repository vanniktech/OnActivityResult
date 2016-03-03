package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultParameterResultCodeTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void test(final int resultCode) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test(resultCode);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultSameRequestCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10) public void bar(final int resultCode) {}",
            "@OnActivityResult(requestCode = 10) public void foo(final int resultCode) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(resultCode);",
                "t.foo(resultCode);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10) public void bar(final int resultCode) {}",
            "@OnActivityResult(requestCode = 11) public void foo(final int resultCode) {}",
            "@OnActivityResult(requestCode = 12) public void abc(final int resultCode) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(resultCode);",

                "didHandle = true;",
            "} else if (requestCode == 11) {",
                "t.foo(resultCode);",

                "didHandle = true;",
            "} else if (requestCode == 12) {",
                "t.abc(resultCode);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
