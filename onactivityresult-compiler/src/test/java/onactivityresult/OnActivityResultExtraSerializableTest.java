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
                "final Serializable testExtraSerializable = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.test(testExtraSerializable);",

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
                "final Serializable fooExtraSerializable = IntentHelper.getExtraSerializable(intent, \"foo\", null);",
                "final Serializable barExtraSerializable = IntentHelper.getExtraSerializable(intent, \"bar\", null);",
                "t.test(fooExtraSerializable, barExtraSerializable);",

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
                "final Serializable valueExtraSerializable = IntentHelper.getExtraSerializable(intent, \"value\", null);",
                "t.foo(valueExtraSerializable);",
                "t.bar(valueExtraSerializable);",

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
                "final Serializable testExtraSerializable = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.foo(testExtraSerializable);",

                "final Serializable fooExtraSerializable = IntentHelper.getExtraSerializable(intent, \"foo\", null);",
                "t.bar(testExtraSerializable, fooExtraSerializable);",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classImplementingSerializable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").withExtraCode(
            "class MySerializable implements Serializable {}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final MySerializable test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MySerializable testExtraMySerializable = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.foo(testExtraMySerializable);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classesImplementingSerializable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").withExtraCode(
            "class MySerializable implements Serializable {}",
            "class MySerializable2 implements Serializable {}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final MySerializable test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final MySerializable2 test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MySerializable testExtraMySerializable = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.foo(testExtraMySerializable);",
                "final MySerializable2 testExtraMySerializable2 = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.bar(testExtraMySerializable2);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classInheritingClassImplementingSerializable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").withExtraCode(
            "abstract class MyClass implements Serializable {}",
            "class MySerializable extends MyClass {}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final MySerializable test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MySerializable testExtraMySerializable = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.bar(testExtraMySerializable);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void classImplementingInterfaceExtendingSerializable() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("java.io.Serializable").withExtraCode(
            "interface Seriz extends Serializable {}",
            "class MySerializable implements Seriz {}"
        ).build(
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final MySerializable test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final MySerializable testExtraMySerializable = IntentHelper.getExtraSerializable(intent, \"test\", null);",
                "t.bar(testExtraMySerializable);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }
}
