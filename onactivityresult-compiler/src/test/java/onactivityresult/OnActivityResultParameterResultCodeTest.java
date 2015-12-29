package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertEquals;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultParameterResultCodeTest {
    @Test
    public void testOnActivityResult() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void test(final int resultCode) {}",
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
                            "t.test(resultCode);",
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

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 10) public void bar(final int resultCode) {}",
                    "@OnActivityResult(requestCode = 10) public void foo(final int resultCode) {}",
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
                            "t.bar(resultCode);",
                            "t.foo(resultCode);",
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
                    "@OnActivityResult(requestCode = 10) public void bar(final int resultCode) {}",
                    "@OnActivityResult(requestCode = 11) public void foo(final int resultCode) {}",
                    "@OnActivityResult(requestCode = 12) public void abc(final int resultCode) {}",
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
                            "t.bar(resultCode);",
                        "} else if (requestCode == 11) {",
                            "t.foo(resultCode);",
                        "} else if (requestCode == 12) {",
                            "t.abc(resultCode);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }
}
