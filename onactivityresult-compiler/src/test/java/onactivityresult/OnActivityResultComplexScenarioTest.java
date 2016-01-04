package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static onactivityresult.util.JavaFileObjectUtils.assertEquals;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultComplexScenarioTest {
    @Test
    public void testComplexScenario() {
        //@formatter:off
        final JavaFileObject actualSource = JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(
                "package test;",

                "import android.net.Uri;",
                "import android.content.Intent;",
                
                "import onactivityresult.OnActivityResult;",
                "import onactivityresult.IntentData;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3, resultCodes = { -1, 0 }) public void three(final Intent intent, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 3, resultCodes = { 1 }) public void three(final Intent intent) {}",
                
                    "@OnActivityResult(requestCode = 5, resultCodes = { -1 }) public void five() {}",
                    "@OnActivityResult(requestCode = 5, resultCodes = { 0 }) public void five(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 5, resultCodes = { 1 }) public void five(final int resultCode, @IntentData final Uri uri) {}",

                    "@OnActivityResult(requestCode = 6) public void sixA(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 6, resultCodes = { 0 }) public void sixB(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 6, resultCodes = { 1 }) public void sixC(@IntentData final Uri uri) {}",

                    "@OnActivityResult(requestCode = 7) public void seven(final Intent intent, final int resultCode, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 7, resultCodes = { 1 }) public void seven(final Intent intent, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 7, resultCodes = { -1 }) public void seven(@IntentData final Uri uri) {}",

                    "@OnActivityResult(requestCode = 8, resultCodes = { 0 }) public void eight(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 8, resultCodes = { 1 }) public void eight(final Intent intent, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 8) public void eight(final Intent intent, final int resultCode, @IntentData final Uri uri) {}",
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
                            "if (resultCode == -1 || resultCode == 0) {",
                                "final Uri intentData = intent.getData();",
                                "t.three(intent, intentData);",
                            "} else if (resultCode == 1) {",
                                "t.three(intent);",
                            "}",
                        "} else if (requestCode == 5) {",
                            "if (resultCode == -1) {",
                                "t.five();",
                            "} else if (resultCode == 0) {",
                                "final Uri intentData = intent.getData();",
                                "t.five(intentData);",
                            "} else if (resultCode == 1) {",
                                "final Uri intentData = intent.getData();",
                                "t.five(resultCode, intentData);",
                            "}",
                        "} else if (requestCode == 6) {",
                            "if (resultCode == 0) {",
                                "final Uri intentData = intent.getData();",
                                "t.sixB(intentData);",
                            "} else if (resultCode == 1) {",
                                "final Uri intentData = intent.getData();",
                                "t.sixC(intentData);",
                            "}",

                            "final Uri intentData = intent.getData();",
                            "t.sixA(intentData);",
                        "} else if (requestCode == 7) {",
                            "if (resultCode == 1) {",
                                "final Uri intentData = intent.getData();",
                                "t.seven(intent, intentData);",
                            "} else if (resultCode == -1) {",
                                "final Uri intentData = intent.getData();",
                                "t.seven(intentData);",
                            "}",

                            "final Uri intentData = intent.getData();",
                            "t.seven(intent, resultCode, intentData);",
                        "} else if (requestCode == 8) {",
                            "if (resultCode == 0) {",
                                "final Uri intentData = intent.getData();",
                                "t.eight(intentData);",
                            "} else if (resultCode == 1) {",
                                "final Uri intentData = intent.getData();",
                                "t.eight(intent, intentData);",
                            "}",

                            "final Uri intentData = intent.getData();",
                            "t.eight(intent, resultCode, intentData);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }
}
