package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertThatFailsWithErrorMessage;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultInvalidParametersTest {
    @Test
    public void testOnActivityResultMemberMethodDuplicatedIntArgumentsShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final int resultCode, final int foo) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (int, int). (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testOnActivityResultMemberMethodDuplicatedIntentArgumentsShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.content.Intent;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Intent intent, final Intent foo) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (Intent, Intent). (test.TestActivity.myOnActivityResult)", 5);
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentsShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Float myFloat) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (Float). (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentFollowingResultCodeShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final int resultCode, final Float myFloat) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (int, Float). (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentFollowingIntentShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.content.Intent;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Intent intent, final Float myFloat) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (Intent, Float). (test.TestActivity.myOnActivityResult)", 5);
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentPrecedingResultCodeShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Double myDouble, final int resultCode) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (Double, int). (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testOnActivityResultMemberMethodIncorrectArgumentPrecedingIntentShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.content.Intent;",
                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void myOnActivityResult(final Double myDouble, final Intent intent) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods do not support the following parameter(s) - (Double, Intent). (test.TestActivity.myOnActivityResult)", 5);
    }

}
