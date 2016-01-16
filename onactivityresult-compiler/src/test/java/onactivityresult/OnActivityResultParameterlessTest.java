package onactivityresult;

import org.junit.Test;

public class OnActivityResultParameterlessTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public void test() {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test();",
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
            "} else if (requestCode == 11) {",
                "t.foo();",
            "} else if (requestCode == 12) {",
                "t.abc();",
            "}"
        );
        //@formatter:on
    }
}
