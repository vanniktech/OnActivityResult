package onactivityresult;

import static onactivityresult.util.JavaFileObjectUtils.assertEquals;

import javax.tools.JavaFileObject;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

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
                "import org.jetbrains.annotations.Nullable;",
                "import org.jetbrains.annotations.NotNull;",

                "public class TestActivity {",
                    "@OnActivityResult(requestCode = 3, resultCodes = { -1, 0 }) public void three(final Intent intent, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 3, resultCodes = { 1 }) public void three(final Intent intent) {}",
                
                    "@OnActivityResult(requestCode = 5, resultCodes = { -1 }) public void five() {}",
                    "@OnActivityResult(requestCode = 5, resultCodes = { 0 }) public void five(@IntentData @Nullable final Uri uri) {}",
                    "@OnActivityResult(requestCode = 5, resultCodes = { 1 }) public void five(final int resultCode, @NotNull @IntentData final Uri uri) {}",

                    "@OnActivityResult(requestCode = 6) public void sixA(@Nullable @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 6, resultCodes = { 0 }) public void sixB(@NotNull @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 6, resultCodes = { 1 }) public void sixC(@IntentData final Uri uri) {}",

                    "@OnActivityResult(requestCode = 7) public void seven(final Intent intent, final int resultCode, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 7, resultCodes = { 1 }) public void seven(final Intent intent, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 7, resultCodes = { -1 }) public void seven(@IntentData final Uri uri) {}",

                    "@OnActivityResult(requestCode = 8, resultCodes = { 0 }) public void eight(@IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 8, resultCodes = { 1 }) public void eight(final Intent intent, @IntentData final Uri uri) {}",
                    "@OnActivityResult(requestCode = 8) public void eight(final Intent intent, final int resultCode, @Nullable @IntentData final Uri uri) {}",
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
                "import onactivityresult.IntentHelper;",
                "import onactivityresult.internal.IOnActivityResult;",

                "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                    "@Override",
                    "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                        "if (requestCode == 3) {",
                            "if (resultCode == -1 || resultCode == 0) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.three(intent, intentData);",
                            "} else if (resultCode == 1) {",
                                "t.three(intent);",
                            "}",
                        "} else if (requestCode == 5) {",
                            "if (resultCode == -1) {",
                                "t.five();",
                            "} else if (resultCode == 0) {",
                                "final Uri intentDataNullable = IntentHelper.getIntentDataNullable(intent);",
                                "t.five(intentDataNullable);",
                            "} else if (resultCode == 1) {",
                                "final Uri intentDataNonNull = IntentHelper.getIntentDataNonNull(intent);",
                                "t.five(resultCode, intentDataNonNull);",
                            "}",
                        "} else if (requestCode == 6) {",
                            "if (resultCode == 0) {",
                                "final Uri intentDataNonNull = IntentHelper.getIntentDataNonNull(intent);",
                                "t.sixB(intentDataNonNull);",
                            "} else if (resultCode == 1) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.sixC(intentData);",
                            "}",

                            "final Uri intentDataNullable = IntentHelper.getIntentDataNullable(intent);",
                            "t.sixA(intentDataNullable);",
                        "} else if (requestCode == 7) {",
                            "if (resultCode == 1) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.seven(intent, intentData);",
                            "} else if (resultCode == -1) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.seven(intentData);",
                            "}",

                            "final Uri intentData = IntentHelper.getIntentData(intent);",
                            "t.seven(intent, resultCode, intentData);",
                        "} else if (requestCode == 8) {",
                            "if (resultCode == 0) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.eight(intentData);",
                            "} else if (resultCode == 1) {",
                                "final Uri intentData = IntentHelper.getIntentData(intent);",
                                "t.eight(intent, intentData);",
                            "}",

                            "final Uri intentDataNullable = IntentHelper.getIntentDataNullable(intent);",
                            "t.eight(intent, resultCode, intentDataNullable);",
                        "}",
                    "}",
                "}")
        );
        //@formatter:on

        assertEquals(actualSource, expectedSource);
    }
}
