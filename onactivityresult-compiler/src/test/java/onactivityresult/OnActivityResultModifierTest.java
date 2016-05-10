package onactivityresult;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import org.junit.Test;

import javax.tools.JavaFileObject;

import onactivityresult.compiler.OnActivityResultProcessor;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultModifierTest {
    @Test
    public void privateOnActivityResultMemberMethod() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) private void myOnActivityResult() {}"
        ).failsWithErrorMessage("@OnActivityResult methods must not be private or static. (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void staticOnActivityResultMemberMethod() {
        //@formatter:off
        TestActivity.create().build(
            "@OnActivityResult(requestCode = 3) public static void myOnActivityResult() {}"
        ).failsWithErrorMessage("@OnActivityResult methods must not be private or static. (test.TestActivity.myOnActivityResult)", 1);
        //@formatter:on
    }

    @Test
    public void privateClassWithAnnotatedOnActivityResultMethod() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
            "package test;",

            "import onactivityresult.OnActivityResult;",

            "public class TestActivity {",
                "private static class PrivateClass {",
                    "@OnActivityResult(requestCode = 3) public final void myOnActivityResult() {}",
                "}",
            "}")
        );
        //@formatter:on

        assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).failsToCompile().withErrorContaining("@OnActivityResult classes must not be private. (test.TestActivity.PrivateClass)").in(source).onLine(4);
    }

    @Test
    public void protectedClassWithAnnotatedOnActivityResultMethod() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
            "package test;",

            "import onactivityresult.OnActivityResult;",

            "public class TestActivity {",
                "protected static class PrivateClass {",
                    "@OnActivityResult(requestCode = 3) public final void myOnActivityResult() {}",
                "}",
            "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void packageClassWithAnnotatedOnActivityResultMethod() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
            "package test;",

            "import onactivityresult.OnActivityResult;",

            "class TestActivity {",
                "@OnActivityResult(requestCode = 3) public final void myOnActivityResult() {}",
            "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void finalClassWithAnnotatedOnActivityResultMethod() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
            "package test;",

            "import onactivityresult.OnActivityResult;",

            "public final class TestActivity {",
                "@OnActivityResult(requestCode = 3) public final void myOnActivityResult() {}",
            "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void allowedMethodModifiers() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
            "package test;",

            "import onactivityresult.OnActivityResult;",

            "public class TestActivity {",
                "@OnActivityResult(requestCode = 3) protected void protectedResult() {}",
                "@OnActivityResult(requestCode = 3) protected final void protectedFinalResult() {}",
                "@OnActivityResult(requestCode = 3) void packageResult() {}",
                "@OnActivityResult(requestCode = 3) final void packageFinalResult() {}",
                "@OnActivityResult(requestCode = 3) public final void publicFinalResult() {}",
            "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }

    @Test
    public void upperCasePackageName() {
        //@formatter:off
        TestActivity.create().withPackageName("com.Test").build(
            "@OnActivityResult(requestCode = 3) public void test() {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test();",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    private void assertThatSucceeds(final JavaFileObject source) {
        assertAbout(javaSource()).that(source).compilesWithoutError();
        assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).compilesWithoutError();
    }
}
