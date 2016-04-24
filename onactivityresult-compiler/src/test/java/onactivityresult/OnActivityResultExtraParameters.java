package onactivityresult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(Parameterized.class)
public class OnActivityResultExtraParameters {
    @Parameterized.Parameters(name = "Type {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { Type.BOOLEAN }, { Type.BYTE }, { Type.CHAR }, { Type.DOUBLE }, { Type.FLOAT }, { Type.INT }, { Type.LONG }, { Type.SHORT }, { Type.STRING } });
    }

    @Parameterized.Parameter public Type type;

    @Test
    public void extra() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 3) public void test(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final " + type.type.getSimpleName() + " test" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"test\", " + type.defaultValueString + ");",
                "t.test(test" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extras() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 3) public void test(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " foo, @" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final " + type.type.getSimpleName() + " foo" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"foo\", " + type.defaultValueString + ");",
                "final " + type.type.getSimpleName() + " bar" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"bar\", " + type.defaultValueString + ");",
                "t.test(foo" + type.annotation.getSimpleName() + "_" + type.identifier() + ", bar" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCode() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 5) public void foo(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " value) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " value) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final " + type.type.getSimpleName() + " value" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"value\", " + type.defaultValueString + ");",
                "t.foo(value" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",
                "t.bar(value" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraSameRequestCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 5) public void foo(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " test, @" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "final " + type.type.getSimpleName() + " test" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"test\", " + type.defaultValueString + ");",
                "t.foo(test" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "final " + type.type.getSimpleName() + " foo" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"foo\", " + type.defaultValueString + ");",
                "t.bar(test" + type.annotation.getSimpleName() + "_" + type.identifier() + ", foo" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraDefaultValue() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 3) public void test(@" + type.annotation.getSimpleName() + "(defaultValue = " + type.defaultValueString2 + ") final " + type.type.getSimpleName() + " test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final " + type.type.getSimpleName() + " test" + type.annotation.getSimpleName() + "_" + type.identifier2() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"test\", " + type.defaultValueString2 + ");",
                "t.test(test" + type.annotation.getSimpleName() + "_" + type.identifier2() + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraDefaultValueOnDifferentMethods() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 3) public void foobar(@" + type.annotation.getSimpleName() + " final " + type.type.getSimpleName() + " test) {}",
            "@OnActivityResult(requestCode = 3) public void foo(@" + type.annotation.getSimpleName() + "(defaultValue = " + type.defaultValueString + ") final " + type.type.getSimpleName() + " test) {}",
            "@OnActivityResult(requestCode = 3) public void bar(@" + type.annotation.getSimpleName() + "(defaultValue = " + type.defaultValueString2 + ") final " + type.type.getSimpleName() + " test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final " + type.type.getSimpleName() + " test" + type.annotation.getSimpleName() + "_" + type.identifier() + " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"test\", " + type.defaultValueString + ");",
                "t.foobar(test" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "t.foo(test" + type.annotation.getSimpleName() + "_" + type.identifier() + ");",

                "final " + type.type.getSimpleName() + " test" + type.annotation.getSimpleName() + "_" + type.identifier2()+ " = IntentHelper.get" + type.annotation.getSimpleName() + "(intent, \"test\", " + type.defaultValueString2 + ");",
                "t.bar(test" + type.annotation.getSimpleName() + "_" + type.identifier2()+ ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraWrongType() {
        //@formatter:off
        TestActivity.create().addImport(type.annotation.getCanonicalName()).build(
            "@OnActivityResult(requestCode = 3) public void test(@" + type.annotation.getSimpleName() + " final Object test) {}"
        ).failsWithErrorMessage("@" + type.annotation.getSimpleName() + " parameters must be of type " + type.type.getCanonicalName() + ". (test.TestActivity.test)", 1);
        //@formatter:on
    }

    enum Type {
        //@formatter:off
        BOOLEAN(ExtraBoolean.class, boolean.class, "false", "false", "true", "true"),
        BYTE(ExtraByte.class, byte.class, String.valueOf((byte) 0), "(byte) 0", String.valueOf((byte) 1), "(byte) 1"),
        CHAR(ExtraChar.class, char.class, String.valueOf(0), "(char) 0", String.valueOf(1), "(char) 1"),
        DOUBLE(ExtraDouble.class, double.class, String.valueOf(0d), "0.0", String.valueOf(1d), "1.0"),
        FLOAT(ExtraFloat.class, float.class, String.valueOf(0.0f), "0.0f", String.valueOf(1.3f), "1.3f"),
        INT(ExtraInt.class, int.class, "0", "0", "1", "1"),
        LONG(ExtraLong.class, long.class, String.valueOf(0L), "0L", String.valueOf(1L), "1L"),
        SHORT(ExtraShort.class, short.class, String.valueOf((short) 0), "(short) 0", String.valueOf((short) 1), "(short) 1"),
        STRING(ExtraString.class, String.class, "", "\"\"", "defaultValue", "\"defaultValue\"");
        //@formatter:on

        // TODO: make less configurable

        final Class<? extends Annotation> annotation;
        final Class<?> type;
        final String defaultValue;
        final String defaultValue2;
        final String defaultValueString;
        final String defaultValueString2;

        Type(final Class<? extends Annotation> annotation, final Class<?> type, final String defaultValue, final String defaultValueString, final String defaultValue2, final String defaultValueString2) {
            this.annotation = annotation;
            this.type = type;
            this.defaultValue = defaultValue;
            this.defaultValue2 = defaultValue2;
            this.defaultValueString = defaultValueString;
            this.defaultValueString2 = defaultValueString2;
        }

        String identifier() {
            final int hashCode = defaultValue.hashCode();
            return hashCode < 0 ? "N" + -hashCode : String.valueOf(hashCode);
        }

        String identifier2() {
            final int hashCode = defaultValue2.hashCode();
            return hashCode < 0 ? "N" + -hashCode : String.valueOf(hashCode);
        }
    }
}
