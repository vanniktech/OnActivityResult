package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertThatFailsWithErrorMessage;
import static onactivityresult.util.JavaFileObjectUtils.assertThatSucceeds;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultModifierTest {
    @Test
    public void testPrivateOnActivityResultMemberMethodShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/OnActivityResultPrivateTest", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class OnActivityResultPrivateTest {",
                    "@OnActivityResult(requestCode = 3) private void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods must not be private or static. (test.OnActivityResultPrivateTest.myOnActivityResult)", 4);
    }

    @Test
    public void testStaticOnActivityResultMemberMethodShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/OnActivityResultStaticTest", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class OnActivityResultStaticTest {",
                    "@OnActivityResult(requestCode = 3) public static void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@OnActivityResult methods must not be private or static. (test.OnActivityResultStaticTest.myOnActivityResult)", 4);
    }

    @Test
    public void testFinalOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/OnActivityResultFinalTest", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class OnActivityResultFinalTest {",
                    "@OnActivityResult(requestCode = 3) public final void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testAbstractOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/OnActivityResultAbstractTest", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public abstract class OnActivityResultAbstractTest {",
                    "@OnActivityResult(requestCode = 3) public abstract void myOnActivityResult();",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testProtectedOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/OnActivityResultProtectedTest", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class OnActivityResultProtectedTest {",
                    "@OnActivityResult(requestCode = 3) protected void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void testPackageOnActivityResultMemberMethodShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/OnActivityResultPackageTest", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class OnActivityResultPackageTest {",
                    "@OnActivityResult(requestCode = 3) void myOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

}
