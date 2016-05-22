package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraParcelableTest {
    @Test
    public void extra() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcelable").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Parcelable test) {}"
        ).needsParcelable().generatesBody(
            "if (requestCode == 3) {",
                "final Parcelable testExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.test(testExtraParcelable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extras() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcelable").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Parcelable foo, @Extra final Parcelable bar) {}"
        ).needsParcelable().generatesBody(
            "if (requestCode == 3) {",
                "final Parcelable fooExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"foo\", null);",
                "final Parcelable barExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"bar\", null);",
                "t.test(fooExtraParcelable_3392903, barExtraParcelable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcelable").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Parcelable value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Parcelable value) {}"
        ).needsParcelable().generatesBody(
            "if (requestCode == 5) {",
                "final Parcelable valueExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"value\", null);",
                "t.foo(valueExtraParcelable_3392903);",
                "t.bar(valueExtraParcelable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcelable").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Parcelable test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Parcelable test, @Extra final Parcelable foo) {}"
        ).needsParcelable().generatesBody(
            "if (requestCode == 5) {",
                "final Parcelable testExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.foo(testExtraParcelable_3392903);",

                "final Parcelable fooExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"foo\", null);",
                "t.bar(testExtraParcelable_3392903, fooExtraParcelable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classImplementingParcelable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcel").addImport("android.os.Parcelable").withExtraCode(
            "class MyParcelable implements Parcelable {",
                "@Override public int describeContents() { return 0; }",
                "@Override public void writeToParcel(final Parcel dest, final int flags) {}",
            "}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final MyParcelable test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MyParcelable testExtraMyParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.foo(testExtraMyParcelable_3392903);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classesImplementingParcelable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcel").addImport("android.os.Parcelable").withExtraCode(
            "class MyParcelable implements Parcelable {",
                "@Override public int describeContents() { return 0; }",
                "@Override public void writeToParcel(final Parcel dest, final int flags) {}",
            "}",
            "class MyParcelable2 implements Parcelable {",
                "@Override public int describeContents() { return 0; }",
                "@Override public void writeToParcel(final Parcel dest, final int flags) {}",
            "}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final MyParcelable test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final MyParcelable2 test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MyParcelable testExtraMyParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.foo(testExtraMyParcelable_3392903);",
                "final MyParcelable2 testExtraMyParcelable2_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.bar(testExtraMyParcelable2_3392903);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classInheritingClassImplementingParcelable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcel").addImport("android.os.Parcelable").withExtraCode(
            "abstract class MyClass implements Parcelable {}",
            "class MyParcelable extends MyClass {",
                "@Override public int describeContents() { return 0; }",
                "@Override public void writeToParcel(final Parcel dest, final int flags) {}",
            "}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final MyParcelable test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MyParcelable testExtraMyParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.bar(testExtraMyParcelable_3392903);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classImplementingInterfaceExtendingParcelable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("android.os.Parcel").addImport("android.os.Parcelable").withExtraCode(
            "interface Seriz extends Parcelable {}",
            "class MyParcelable implements Seriz {",
                "@Override public int describeContents() { return 0; }",
                "@Override public void writeToParcel(final Parcel dest, final int flags) {}",
            "}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final MyParcelable test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MyParcelable testExtraMyParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.bar(testExtraMyParcelable_3392903);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
