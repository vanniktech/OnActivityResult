package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertThatFailsWithErrorMessage;
import static onactivityresult.util.JavaFileObjectUtils.assertThatSucceeds;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class InvalidRequestCodeTest {
    @Test
    public void testOnActivityResultRequestCodeMinus1ShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = -1) public void minusOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "RequestCodes must be >= 0. (test.TestActivity.minusOnActivityResult)", 4);
    }

    @Test
    public void testOnActivityResultRequestCodeIntegerMinShouldLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = Integer.MIN_VALUE) public void integerMinOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "RequestCodes must be >= 0. (test.TestActivity.integerMinOnActivityResult)", 4);
    }

    @Test
    public void testOnActivityResultRequestCodeZeroShouldNotLetTheProcessorFail() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 0) public void integerMinOnActivityResult() {}",
                "}")
        );
        //@formatter:on

        assertThatSucceeds(source);
    }
}
