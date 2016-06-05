package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraCharSequenceTest {
    @Test
    public void extra() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final CharSequence test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final CharSequence testExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"test\", null);",
                "t.test(testExtraCharSequence);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extras() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final CharSequence foo, @Extra final CharSequence bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final CharSequence fooExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"foo\", null);",
                "final CharSequence barExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"bar\", null);",
                "t.test(fooExtraCharSequence, barExtraCharSequence);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final CharSequence value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final CharSequence value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final CharSequence valueExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"value\", null);",
                "t.foo(valueExtraCharSequence);",
                "t.bar(valueExtraCharSequence);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final CharSequence test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final CharSequence test, @Extra final CharSequence foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final CharSequence testExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"test\", null);",
                "t.foo(testExtraCharSequence);",

                "final CharSequence fooExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"foo\", null);",
                "t.bar(testExtraCharSequence, fooExtraCharSequence);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
