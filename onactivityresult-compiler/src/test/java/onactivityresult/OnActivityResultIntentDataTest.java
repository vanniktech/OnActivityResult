package onactivityresult;

import org.junit.Test;

public class OnActivityResultIntentDataTest {
    @Test
    public void testOnActivityResultIntentDataOnWrongType() {
        //@formatter:off
        TestActivity.create().hasIntentData().build(
            "@OnActivityResult(requestCode = 3) public void test(@IntentData final int uri) {}"
        ).failsWithErrorMessage("@IntentData parameters must be of type android.net.Uri. (test.TestActivity.test)", 1);
        //@formatter:on
    }

    @Test
    public void testOnActivityResultOnlyIntentData() {
        //@formatter:off
        TestActivity.create().hasIntentData().build(
            "@OnActivityResult(requestCode = 3) public void test(@IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.test(intentData);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultNullableIntentData() {
        //@formatter:off
        TestActivity.create().hasIntentData().hasNullable().build(
            "@OnActivityResult(requestCode = 3) public void test(@Nullable @IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final Uri intentDataNullable = IntentHelper.getIntentDataNullable(intent);",
                "t.test(intentDataNullable);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultNotNullIntentData() {
        //@formatter:off
        TestActivity.create().hasIntentData().hasNotNull().build(
            "@OnActivityResult(requestCode = 3) public void test(@NotNull @IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final Uri intentDataNonNull = IntentHelper.getIntentDataNonNull(intent);",
                "t.test(intentDataNonNull);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultNullableNonNullAndDefaultIntentDataWithSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntentData().hasNullable().hasNotNull().build(
            "@OnActivityResult(requestCode = 3) public void bar(@Nullable @IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void abc(@NotNull @IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final Uri intentDataNullable = IntentHelper.getIntentDataNullable(intent);",
                "t.bar(intentDataNullable);",
                "final Uri intentDataNonNull = IntentHelper.getIntentDataNonNull(intent);",
                "t.abc(intentDataNonNull);",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.foo(intentData);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultOnlyIntentDataDefaultNullAbleAndNonNullSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntentData().hasNullable().hasNotNull().build(
            "@OnActivityResult(requestCode = 3) public void foo(@IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void fooNullable(@Nullable @IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void barNullable(@Nullable @IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void fooNotNull(@NotNull @IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 3) public void barNotNull(@NotNull @IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.foo(intentData);",
                "t.bar(intentData);",
                "final Uri intentDataNullable = IntentHelper.getIntentDataNullable(intent);",
                "t.fooNullable(intentDataNullable);",
                "t.barNullable(intentDataNullable);",
                "final Uri intentDataNonNull = IntentHelper.getIntentDataNonNull(intent);",
                "t.fooNotNull(intentDataNonNull);",
                "t.barNotNull(intentDataNonNull);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultOnlyIntentDataDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntentData().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(@IntentData final Uri uri) {}",
            "@OnActivityResult(requestCode = 11) public void foo(@IntentData final Uri uri, final int resultCode) {}",
            "@OnActivityResult(requestCode = 12) public void abc(final Intent intent, final int resultCode, @IntentData final Uri uri) {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.bar(intentData);",
            "} else if (requestCode == 11) {",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.foo(intentData, resultCode);",
            "} else if (requestCode == 12) {",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.abc(intent, resultCode, intentData);",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testOnActivityResultIntentDataResultCodeAndIntent() {
        //@formatter:off
        TestActivity.create().hasIntentData().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void test(@IntentData final Uri uri, final int resultCode, final Intent intent) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final Uri intentData = IntentHelper.getIntentData(intent);",
                "t.test(intentData, resultCode, intent);",
            "}"
        );
        //@formatter:on
    }
}
