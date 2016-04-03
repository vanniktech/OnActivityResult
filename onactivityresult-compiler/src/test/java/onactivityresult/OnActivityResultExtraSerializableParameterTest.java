package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraSerializableParameterTest {
    @Test
    public void testExtraSerializable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Serializable test) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 3) {",
                "final Serializable testSerializableExtra_3392903 = IntentHelper.getSerializableExtra(intent, \"test\", null);",
                "t.test(testSerializableExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSerializables() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final Serializable foo, @Extra final Serializable bar) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 3) {",
                "final Serializable fooSerializableExtra_3392903 = IntentHelper.getSerializableExtra(intent, \"foo\", null);",
                "final Serializable barSerializableExtra_3392903 = IntentHelper.getSerializableExtra(intent, \"bar\", null);",
                "t.test(fooSerializableExtra_3392903, barSerializableExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSerializableSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Serializable value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Serializable value) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 5) {",
                "final Serializable valueSerializableExtra_3392903 = IntentHelper.getSerializableExtra(intent, \"value\", null);",
                "t.foo(valueSerializableExtra_3392903);",
                "t.bar(valueSerializableExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSerializableSameRequestCodeDifferentExtraSerializables() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final Serializable test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final Serializable test, @Extra final Serializable foo) {}"
        ).needsSerializable().generatesBody(
            "if (requestCode == 5) {",
                "final Serializable testSerializableExtra_3392903 = IntentHelper.getSerializableExtra(intent, \"test\", null);",
                "t.foo(testSerializableExtra_3392903);",
                
                "final Serializable fooSerializableExtra_3392903 = IntentHelper.getSerializableExtra(intent, \"foo\", null);",
                "t.bar(testSerializableExtra_3392903, fooSerializableExtra_3392903);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
