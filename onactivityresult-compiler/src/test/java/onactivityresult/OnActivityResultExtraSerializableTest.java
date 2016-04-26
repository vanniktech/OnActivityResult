package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraSerializableTest {
    @Test
    public void extra() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Serializable test) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 3) {",
                "final Serializable testExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.test(testExtraSerializable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extras() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Serializable foo, @Extra final Serializable bar) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 3) {",
                "final Serializable fooExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"foo\", null);",
                "final Serializable barExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"bar\", null);",
                "t.test(fooExtraSerializable_3392903, barExtraSerializable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Serializable value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Serializable value) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 5) {",
                "final Serializable valueExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"value\", null);",
                "t.foo(valueExtraSerializable_3392903);",
                "t.bar(valueExtraSerializable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Serializable test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Serializable test, @Extra final Serializable foo) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 5) {",
                "final Serializable testExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.foo(testExtraSerializable_3392903);",

                "final Serializable fooExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"foo\", null);",
                "t.bar(testExtraSerializable_3392903, fooExtraSerializable_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
