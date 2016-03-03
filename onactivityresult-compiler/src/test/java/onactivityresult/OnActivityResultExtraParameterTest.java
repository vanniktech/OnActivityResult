package onactivityresult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraParameterTest {
    @Parameterized.Parameters(name = "Type {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //@formatter:off
                { "boolean", "_97196323", "false" },
                { "byte", "_48", "(byte) 0" },
                { "char", "_48", "(char) 0" },
                { "double", "_47602", "0d" },
                { "float", "_47602", "0.f" },
                { "int", "_48", "0" },
                { "long", "_48", "0L" },
                { "short", "_48", "(short) 0" },
                { "String", "_0", "\"\"" },
                //@formatter:on
        });
    }

    @Parameterized.Parameter(0) public String type;
    @Parameterized.Parameter(1) public String defaultIdentifier;
    @Parameterized.Parameter(2) public String defaultValue;

    @Test
    public void testExtra() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final " + type + " test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                getExtractVariableStatement("test"),
                callFunction("test", "test"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraAndExtraSpecificParametersUseSameParameter() {
        //@formatter:off
        TestActivity.create().hasExtra().addImport("onactivityresult.Extra" + camelCaseType()).build(
                "@OnActivityResult(requestCode = 3) public void extra(@Extra final " + type + " test) {}",
                "@OnActivityResult(requestCode = 3) public void specificExtra(@Extra" + camelCaseType() + " final " + type + " test) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                getExtractVariableStatement("test"),
                callFunction("extra", "test"),
                callFunction("specificExtra", "test"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void test(@Extra final " + type + " foo, @Extra final " + type + " bar) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                getExtractVariableStatement("foo"),
                getExtractVariableStatement("bar"),
                callFunction("test", "foo", "bar"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final " + type + " test) {}",
            "@OnActivityResult(requestCode = 5) public void bar(@Extra final " + type + " test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                getExtractVariableStatement("test"),
                callFunction("foo","test"),
                callFunction("bar","test"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final " + type + " test) {}",
            "@OnActivityResult(requestCode = 6) public void bar(@Extra final " + type + " test) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                getExtractVariableStatement("test"),
                callFunction("foo","test"),

                "didHandle = true;",
            "} else if (requestCode == 6) {",
                getExtractVariableStatement("test"),
                callFunction("bar","test"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSameRequestCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5) public void foo(@Extra final " + type + " test) {}",
            "@OnActivityResult(requestCode = 5) public void test(@Extra final " + type + " test, @Extra final " + type + " foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                getExtractVariableStatement("test"),
                callFunction("foo","test"),

                getExtractVariableStatement("foo"),
                callFunction("test", "test", "foo"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSameRequestCodeSameResultCodeDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5, resultCodes = { 1 }) public void foo(@Extra final " + type + " test) {}",
            "@OnActivityResult(requestCode = 5, resultCodes = { 1 }) public void test(@Extra final " + type + " test, @Extra final " + type + " foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "if (resultCode == 1) {",
                    getExtractVariableStatement("test"),
                    callFunction("foo","test"),

                    getExtractVariableStatement("foo"),
                    callFunction("test", "test", "foo"),

                    "didHandle = true;",
                "}",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void testExtraSameRequestCodeDifferentResultCodesDifferentExtras() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 5, resultCodes = { 1 }) public void foo(@Extra final " + type + " test) {}",
            "@OnActivityResult(requestCode = 5, resultCodes = { 0 }) public void test(@Extra final " + type + " test, @Extra final " + type + " foo) {}",
            "@OnActivityResult(requestCode = 5) public void test(@Extra final " + type + " foo) {}"
        ).generatesBody(
            "if (requestCode == 5) {",
                "if (resultCode == 0) {",
                    getExtractVariableStatement("test"),
                    getExtractVariableStatement("foo"),
                    callFunction("test", "test", "foo"),

                    "didHandle = true;",
                "} else if (resultCode == 1) {",
                    getExtractVariableStatement("test"),
                    callFunction("foo","test"),

                    "didHandle = true;",
                "}",
                getExtractVariableStatement("foo"),
                callFunction("test", "foo"),

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    private String callFunction(final String functionName, final String variableName) {
        return "t." + functionName + "(" + getVariableName(variableName) + ");";
    }

    private String callFunction(final String functionName, final String variableName, final String variableName1) {
        return "t." + functionName + "(" + getVariableName(variableName) + ", " + getVariableName(variableName1) + ");";
    }

    private String getExtractVariableStatement(final String name) {
        return "final " + type + " " + getVariableName(name) + " = IntentHelper.get" + camelCaseType() + "Extra(intent, \"" + name + "\", " + defaultValue + ");";
    }

    private String getVariableName(final String origin) {
        return origin + camelCaseType() + "Extra" + defaultIdentifier;
    }

    private String camelCaseType() {
        return Character.toUpperCase(type.charAt(0)) + type.substring(1);
    }
}
