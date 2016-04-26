package onactivityresult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
@RunWith(Parameterized.class)
public class OnActivityResultParameters {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { Type.NONE }, { Type.INTENT }, { Type.RESULT_CODE }, { Type.RESULT_CODE_INTENT }, { Type.INTENT_RESULT_CODE }, });
    }

    @Parameterized.Parameter public Type type;

    @Test
    public void onActivityResult() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 3) public void test(" + type.declaration + ") {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "t.test(" + type.call + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void onActivityResultSameRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(" + type.declaration + ") {}",
            "@OnActivityResult(requestCode = 10) public void foo(" + type.declaration + ") {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(" + type.call + ");",
                "t.foo(" + type.call + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void onActivityResultDifferentRequestCode() {
        //@formatter:off
        TestActivity.create().hasIntent().build(
            "@OnActivityResult(requestCode = 10) public void bar(" + type.declaration + ") {}",
            "@OnActivityResult(requestCode = 11) public void foo(" + type.declaration + ") {}",
            "@OnActivityResult(requestCode = 12) public void abc(" + type.declaration + ") {}"
        ).generatesBody(
            "if (requestCode == 10) {",
                "t.bar(" + type.call + ");",

                "didHandle = true;",
            "} else if (requestCode == 11) {",
                "t.foo(" + type.call + ");",

                "didHandle = true;",
            "} else if (requestCode == 12) {",
                "t.abc(" + type.call + ");",

                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    enum Type {
        //@formatter:off
        NONE("", ""),
        INTENT("Intent intent", "intent"),
        RESULT_CODE("int resultCode", "resultCode"),
        RESULT_CODE_INTENT("int resultCode, Intent intent", "resultCode, intent"),
        INTENT_RESULT_CODE("Intent intent, int resultCode", "intent, resultCode");
        //@formatter:on

        final String declaration;
        final String call;

        Type(final String declaration, final String call) {
            this.declaration = declaration;
            this.call = call;
        }
    }
}
