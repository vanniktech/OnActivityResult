package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertEquals;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultResultCodesTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.content.Intent;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3, resultCodes = { 0, 1 }) public void test() {}",
                "}")
        );
        //@formatter:on

        //@formatter:off
        final JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestActivity$$OnActivityResult", Joiner.on('\n').join(
                "// Generated code from OnActivityResult. Do not modify!",
                "package test;",

                "import android.content.Intent;",
                "import java.lang.Override;",
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 3) {",
                            "if (resultCode == 0 || resultCode == 1) {",
                                "t.test();",
                            "}",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }

    @Test
    public void testOnActivityResultSameRequestCode() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.content.Intent;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 10, resultCodes = { 0, -1 }) public void bar() {}",
                    "@OnActivityResult(requestCode = 10, resultCodes = { 1 }) public void foo() {}",
                "}")
        );
        //@formatter:on

        //@formatter:off
        final JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestActivity$$OnActivityResult", Joiner.on('\n').join(
                "// Generated code from OnActivityResult. Do not modify!",
                "package test;",

                "import android.content.Intent;",
                "import java.lang.Override;",
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 10) {",
                            "if (resultCode == 0 || resultCode == -1) {",
                                "t.bar();",
                            "} else if (resultCode == 1) {",
                                "t.foo();",
                            "}",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }

    @Test
    public void testOnActivityResultDifferentRequestCode() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 10, resultCodes = { 1 }) public void bar() {}",
                    "@OnActivityResult(requestCode = 11, resultCodes = { 0 }) public void foo() {}",
                    "@OnActivityResult(requestCode = 12, resultCodes = { -1 }) public void abc() {}",
                "}")
        );
        //@formatter:on

        //@formatter:off
        final JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestActivity$$OnActivityResult", Joiner.on('\n').join(
                "// Generated code from OnActivityResult. Do not modify!",
                "package test;",

                "import android.content.Intent;",
                "import java.lang.Override;",
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 10) {",
                            "if (resultCode == 1) {",
                                "t.bar();",
                            "}",
                        "} else if (requestCode == 11) {",
                            "if (resultCode == 0) {",
                                "t.foo();",
                            "}",
                        "} else if (requestCode == 12) {",
                            "if (resultCode == -1) {",
                                "t.abc();",
                            "}",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }

    @Test
    public void testOnActivityResultRequestCodesAndNonRequestCodes() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.net.Uri;",
                "import android.content.Intent;",

                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 4) public void foo(final Intent intent, final int resultCode, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 4, resultCodes = { 1 }) public void bar(final Intent intent, @IntentData final Uri uri) {}",
                "}")
        );
        //@formatter:on

        //@formatter:off
        final JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestActivity$$OnActivityResult", Joiner.on('\n').join(
                "// Generated code from OnActivityResult. Do not modify!",
                "package test;",

                "import android.content.Intent;",
                "import android.net.Uri;",
                "import java.lang.Override;",
                "import onactivityresult.IntentHelper;",
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 4) {",
                            "if (resultCode == 1) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.bar(intent, intentData);",
                            "}",

                            "final Uri intentData = IntentHelper.getIntentData(intent);",
                            "t.foo(intent, resultCode, intentData);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }
}
