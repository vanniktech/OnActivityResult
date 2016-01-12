package onactivityresult;

import static onactivityresult.util.JavaFileObjectUtils.assertThatFailsWithErrorMessage;
import static onactivityresult.util.JavaFileObjectUtils.assertThatSucceeds;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultModifierTest {
    @Test
    public void testPrivateOnActivityResultMemberMethodShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) private void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods must not be private or static. (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testStaticOnActivityResultMemberMethodShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public static void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods must not be private or static. (test.TestActivity.myOnActivityResult)", 4);
    }

    @Test
    public void testFinalOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public final void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testAbstractOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public abstract class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public abstract void myOnActivityResult();",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testProtectedOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) protected void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testPackageOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }
}
