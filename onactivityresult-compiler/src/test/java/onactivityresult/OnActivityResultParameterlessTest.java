package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultParameterlessTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void test() {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test();",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultSameRequestCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10) public void bar() {}",
            "@OnActivityResult(requestCode = 10) public void foo() {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar();",
                "t.foo();",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10) public void bar() {}",
            "@OnActivityResult(requestCode = 11) public void foo() {}",
            "@OnActivityResult(requestCode = 12) public void abc() {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar();",

                "didHandle = true;",
            "} else if (requestCode == 11) {",
                "t.foo();",

                "didHandle = true;",
            "} else if (requestCode == 12) {",
                "t.abc();",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
