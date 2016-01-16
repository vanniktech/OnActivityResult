package onactivityresult;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import javax.tools.JavaFileObject;

import onactivityresult.compiler.OnActivityResultProcessor;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

public class OnActivityResultModifierTest {
    @Test
    public void testPrivateOnActivityResultMemberMethodShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) private void myOnActivityResult() {}"
        ).failsWithErrorMessage("@OnActivityResult methods must not be private or static. (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void testStaticOnActivityResultMemberMethodShouldLetTheProcessorFail() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public static void myOnActivityResult() {}"
        ).failsWithErrorMessage("@OnActivityResult methods must not be private or static. (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
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

    private void assertThatSucceeds(final JavaFileObject source) {
        assertAbout(javaSource()).that(source).compilesWithoutError();
        assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).compilesWithoutError();
    }
}
