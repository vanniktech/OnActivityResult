package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultResultCodesTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3, resultCodes = { 0, 1 }) public void test() {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "if (resultCode == 0 || resultCode == 1) {",
                    "t.test();",

                    "didHandle = true;",
                "}",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultSameRequestCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10, resultCodes = { 0, -1 }) public void bar() {}",
            "@OnActivityResult(requestCode = 10, resultCodes = { 1 }) public void foo() {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "if (resultCode == 1) {",
                    "t.foo();",

                    "didHandle = true;",
                "} else if (resultCode == 0 || resultCode == -1) {",
                    "t.bar();",

                    "didHandle = true;",
                "}",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultSameRequestCodeAndResultCodes() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10, resultCodes = { 1 }) public void bar() {}",
            "@OnActivityResult(requestCode = 10, resultCodes = { 1 }) public void foo() {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "if (resultCode == 1) {",
                    "t.bar();",
                    "t.foo();",

                    "didHandle = true;",
                "}",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 10, resultCodes = { 1 }) public void bar() {}",
            "@OnActivityResult(requestCode = 11, resultCodes = { 0 }) public void foo() {}",
            "@OnActivityResult(requestCode = 12, resultCodes = { -1 }) public void abc() {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "if (resultCode == 1) {",
                    "t.bar();",

                    "didHandle = true;",
                "}",
            "} else if (requestCode == 11) {",
                "if (resultCode == 0) {",
                    "t.foo();",

                    "didHandle = true;",
                "}",
            "} else if (requestCode == 12) {",
                "if (resultCode == -1) {",
                    "t.abc();",

                    "didHandle = true;",
                "}",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultRequestCodesAndNonRequestCodes() {
        //@formatter:off
        TestActivity.create().hasIntent().hasIntentData().build(
            "@OnActivityResult(requestCode = 4) public void foo(final Intent intent, final int resultCode, @IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 4, resultCodes = { 1 }) public void bar(final Intent intent, @IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 4) {",
                "if (resultCode == 1) {",
                    "final Uri intentData = IntentHelper.getIntentData(intent);",
                    "t.bar(intent, intentData);",

                    "didHandle = true;",
                "}",

                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.foo(intent, resultCode, intentData);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
