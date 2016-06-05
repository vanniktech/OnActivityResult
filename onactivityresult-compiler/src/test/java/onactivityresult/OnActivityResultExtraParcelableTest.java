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
                "final Parcelable testExtraParcelable = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.test(testExtraParcelable);",

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
                "final Parcelable fooExtraParcelable = IntentHelper.getExtraParcelable(intent, \"foo\", null);",
                "final Parcelable barExtraParcelable = IntentHelper.getExtraParcelable(intent, \"bar\", null);",
                "t.test(fooExtraParcelable, barExtraParcelable);",

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
                "final Parcelable valueExtraParcelable = IntentHelper.getExtraParcelable(intent, \"value\", null);",
                "t.foo(valueExtraParcelable);",
                "t.bar(valueExtraParcelable);",

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
                "final Parcelable testExtraParcelable = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.foo(testExtraParcelable);",

                "final Parcelable fooExtraParcelable = IntentHelper.getExtraParcelable(intent, \"foo\", null);",
                "t.bar(testExtraParcelable, fooExtraParcelable);",

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
                "final MyParcelable testExtraMyParcelable = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.foo(testExtraMyParcelable);",
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
                "final MyParcelable testExtraMyParcelable = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.foo(testExtraMyParcelable);",
                "final MyParcelable2 testExtraMyParcelable2 = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.bar(testExtraMyParcelable2);",
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
                "final MyParcelable testExtraMyParcelable = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.bar(testExtraMyParcelable);",
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
                "final MyParcelable testExtraMyParcelable = IntentHelper.getExtraParcelable(intent, \"test\", null);",
                "t.bar(testExtraMyParcelable);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
