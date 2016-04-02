package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraCharSequenceParameterTest {
    @Test
    public void testExtraCharSequence() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final CharSequence test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final CharSequence testCharSequenceExtra_3392903 = IntentHelper.getCharSequenceExtra(intent, \"test\", null);",
                "t.test(testCharSequenceExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharSequences() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final CharSequence foo, @Extra final CharSequence bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final CharSequence fooCharSequenceExtra_3392903 = IntentHelper.getCharSequenceExtra(intent, \"foo\", null);",
                "final CharSequence barCharSequenceExtra_3392903 = IntentHelper.getCharSequenceExtra(intent, \"bar\", null);",
                "t.test(fooCharSequenceExtra_3392903, barCharSequenceExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharSequenceSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final CharSequence value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final CharSequence value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final CharSequence valueCharSequenceExtra_3392903 = IntentHelper.getCharSequenceExtra(intent, \"value\", null);",
                "t.foo(valueCharSequenceExtra_3392903);",
                "t.bar(valueCharSequenceExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraCharSequenceSameRequestCodeDifferentExtraCharSequences() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final CharSequence test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final CharSequence test, @Extra final CharSequence foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final CharSequence testCharSequenceExtra_3392903 = IntentHelper.getCharSequenceExtra(intent, \"test\", null);",
                "t.foo(testCharSequenceExtra_3392903);",
                
                "final CharSequence fooCharSequenceExtra_3392903 = IntentHelper.getCharSequenceExtra(intent, \"foo\", null);",
                "t.bar(testCharSequenceExtra_3392903, fooCharSequenceExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
