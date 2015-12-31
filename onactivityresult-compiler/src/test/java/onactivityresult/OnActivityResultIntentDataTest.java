package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertEquals;
import static onactivityresult.util.JavaFileObjectUtils.assertThatFailsWithErrorMessage;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class OnActivityResultIntentDataTest {
    @Test
    public void testOnActivityResultIntentDataOnWrongType() {
        //@formatter:off
        final JavaFileObject source = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void test(@IntentData final int uri) {}",
                "}")
        );
        //@formatter:on

        assertThatFailsWithErrorMessage(source, "@IntentData parameters must be of type android.net.Uri. (test.TestActivity.test)", 5);
    }

    @Test
    public void testOnActivityResultOnlyIntentData() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "import android.net.Uri;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void test(@IntentData final Uri uri) {}",
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
                "import onactivityresult.internal.IOnActivityResult;",
               
                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 3) {",
                            "final Uri intentData = intent.getData();",
                            "t.test(intentData);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }

    @Test
    public void testOnActivityResultOnlyIntentDataSameRequestCode() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "import android.net.Uri;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void foo(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 3) public void bar(@IntentData final Uri uri) {}",
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
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 3) {",
                            "final Uri intentData = intent.getData();",
                            "t.foo(intentData);",
                            "t.bar(intentData);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }


    @Test
    public void testOnActivityResultOnlyIntentDataDifferentRequestCode() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "import android.net.Uri;",
                "import android.content.Intent;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 10) public void bar(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 11) public void foo(@IntentData final Uri uri, final int resultCode) {}",
                    "@OnActivityResult(requestCode = 12) public void abc(final Intent intent, final int resultCode, @IntentData final Uri uri) {}",
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
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 10) {",
                            "final Uri intentData = intent.getData();",
                            "t.bar(intentData);",
                        "} else if (requestCode == 11) {",
                            "final Uri intentData = intent.getData();",
                            "t.foo(intentData, resultCode);",
                        "} else if (requestCode == 12) {",
                            "final Uri intentData = intent.getData();",
                            "t.abc(intent, resultCode, intentData);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }

    @Test
    public void testOnActivityResultIntentDataResultCodeAndIntent() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "import android.content.Intent;",
                "import android.net.Uri;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3) public void test(@IntentData final Uri uri, final int resultCode, final Intent intent) {}",
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
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 3) {",
                            "final Uri intentData = intent.getData();",
                            "t.test(intentData, resultCode, intent);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }
}
