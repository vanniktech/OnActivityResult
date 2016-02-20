package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultComplexScenarioTest {
    @Test
    public void testComplexScenario() {
        //@formatter:off
        TestActivity.create().hasNullable().hasNotNull().hasIntentData().hasIntent().build(
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

            "@OnActivityResult(requestCode = 9, resultCodes = { 1 }) public void nine(@IntentData final Uri uri, final Intent intent) {}",
            "@OnActivityResult(requestCode = 9, resultCodes = { 1 }) public void nine(@IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "if (resultCode == 1) {",
                    "t.three(intent);",
                "} else if (resultCode == -1 || resultCode == 0) {",
                    " final Uri intentData = IntentHelper.getIntentData(intent);",
                    "t.three(intent, intentData);",
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
                "if (resultCode == -1) {",
                    "final Uri intentData = IntentHelper.getIntentData(intent);",
                    "t.seven(intentData);",
                "} else if (resultCode == 1) {",
                    "final Uri intentData = IntentHelper.getIntentData(intent);",
                    "t.seven(intent, intentData);",
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
            "} else if (requestCode == 9) {",
                "if (resultCode == 1) {",
                    "final Uri intentData = IntentHelper.getIntentData(intent);",
                    "t.nine(intentData, intent);",
                    "t.nine(intentData);",
                "}",
            "}"
        );
        //@formatter:on
    }
}
