package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertThatFailsWithErrorMessage;
import static onactivityresult.util.JavaFileObjectUtils.assertThatSucceeds;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultResultCodesValidationTest {
    @Test
    public void testNegativeInvalidFilterResultCodesShouldLetProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 1, resultCodes = {-2}) void myOnActivityResult() { }",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "Invalid resultCode -2. (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testPositiveInvalidFilterResultCodesShouldLetProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 1, resultCodes = {2}) void myOnActivityResult() { }",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "Invalid resultCode 2. (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testActivityResultDuplicatedFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.app.Activity;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_CANCELED, Activity.RESULT_CANCELED }) void myOnActivityResult() { }",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "Duplicate resultCode 0. (test.TestActivity.myOnActivityResult)", 5);
    }

    @Test
    public void testActivityResultCanceledFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.app.Activity;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_CANCELED }) void myOnActivityResult() { }",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testActivityResultOkFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.app.Activity;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_OK }) void myOnActivityResult() { }",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testActivityResultFirstUserFilterResultCodesShouldLetProcessorNotFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.app.Activity;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                "@OnActivityResult(requestCode = 1, resultCodes = { Activity.RESULT_FIRST_USER }) void myOnActivityResult() { }",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }
}
