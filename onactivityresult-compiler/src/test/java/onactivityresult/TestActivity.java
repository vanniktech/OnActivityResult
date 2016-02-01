package onactivityresult;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

import onactivityresult.compiler.OnActivityResultProcessor;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

final class TestActivity {
    public static Builder create() {
        return new Builder();
    }

    static String stringArrayToString(final String... code) {
        final StringBuilder sb = new StringBuilder();

        for (final String s : code) {
            sb.append(s);
        }

        return sb.toString();
    }

    private TestActivity() {}

    public static final class Builder {
        private boolean hasIntentData;
        private boolean hasNullable;
        private boolean hasNotNull;
        private boolean hasIntent;
        private boolean hasActivity;

        private boolean hasExtraBoolean;
        private boolean hasExtraByte;
        private boolean hasExtraChar;
        private boolean hasExtraDouble;
        private boolean hasExtraFloat;
        private boolean hasExtraInt;
        private boolean hasExtraLong;
        private boolean hasExtraShort;
        private boolean hasExtraString;

        private Builder() {}

        public Builder hasIntentData() {
            hasIntentData = true;
            return this;
        }

        public Builder hasActivity() {
            hasActivity = true;
            return this;
        }

        public Builder hasIntent() {
            hasIntent = true;
            return this;
        }

        public Builder hasNullable() {
            hasNullable = true;
            return this;
        }

        public Builder hasExtraBoolean() {
            hasExtraBoolean = true;
            return this;
        }

        public Builder hasExtraByte() {
            hasExtraByte = true;
            return this;
        }

        public Builder hasExtraChar() {
            hasExtraChar = true;
            return this;
        }

        public Builder hasExtraDouble() {
            hasExtraDouble = true;
            return this;
        }

        public Builder hasExtraFloat() {
            hasExtraFloat = true;
            return this;
        }

        public Builder hasExtraInt() {
            hasExtraInt = true;
            return this;
        }

        public Builder hasExtraLong() {
            hasExtraLong = true;
            return this;
        }

        public Builder hasExtraShort() {
            hasExtraShort = true;
            return this;
        }

        public Builder hasExtraString() {
            hasExtraString = true;
            return this;
        }

        public Builder hasNotNull() {
            hasNotNull = true;
            return this;
        }

        public Source build(final String... functions) {
            final List<String> code = new ArrayList<>();

            code.add("package test;");
            code.add("import onactivityresult.OnActivityResult;");

            if (hasIntentData) {
                code.add("import android.net.Uri;");
                code.add("import onactivityresult.IntentData;");
            }

            if (hasNullable) {
                code.add("import org.jetbrains.annotations.Nullable;");
            }

            if (hasNotNull) {
                code.add("import org.jetbrains.annotations.NotNull;");
            }

            if (hasActivity) {
                code.add("import android.app.Activity;");
            }

            if (hasIntent) {
                code.add("import android.content.Intent;");
            }

            if (hasExtraBoolean) {
                code.add("import onactivityresult.ExtraBoolean;");
            }

            if (hasExtraByte) {
                code.add("import onactivityresult.ExtraByte;");
            }

            if (hasExtraChar) {
                code.add("import onactivityresult.ExtraChar;");
            }

            if (hasExtraDouble) {
                code.add("import onactivityresult.ExtraDouble;");
            }

            if (hasExtraFloat) {
                code.add("import onactivityresult.ExtraFloat;");
            }

            if (hasExtraInt) {
                code.add("import onactivityresult.ExtraInt;");
            }

            if (hasExtraLong) {
                code.add("import onactivityresult.ExtraLong;");
            }

            if (hasExtraShort) {
                code.add("import onactivityresult.ExtraShort;");
            }

            if (hasExtraString) {
                code.add("import onactivityresult.ExtraString;");
                code.add("import java.lang.String;");
            }

            code.add("public class TestActivity {");

            final int headLines = code.size();

            code.add(stringArrayToString(functions));
            code.add("}");

            final boolean needsIntentHelper = hasNullable || hasNotNull || hasIntentData || hasExtraBoolean || hasExtraByte || hasExtraChar || hasExtraDouble || hasExtraFloat || hasExtraInt || hasExtraLong || hasExtraShort || hasExtraString;
            return new Source(JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(code.toArray(new String[code.size()]))), hasIntentData, needsIntentHelper, hasExtraString, headLines);
        }
    }

    public static class Source {
        private final JavaFileObject source;
        private final boolean        needsIntentHelper;
        private final boolean        hasIntentData;
        private final boolean        hasString;
        private final int            headLines;

        Source(final JavaFileObject source, final boolean hasIntentData, final boolean needsIntentHelper, final boolean hasString, final int headLines) {
            this.source = source;
            this.needsIntentHelper = needsIntentHelper;
            this.hasIntentData = hasIntentData;
            this.headLines = headLines;
            this.hasString = hasString;
        }

        public void generatesBody(final String... code) {
            //@formatter:off
            final JavaFileObject generation = JavaFileObjects.forSourceString("test/TestActivity$$OnActivityResult", Joiner.on('\n').join(
                    "// Generated code from OnActivityResult. Do not modify!",
                    "package test;",
    
                    "import android.content.Intent;",
                    hasIntentData ? "import android.net.Uri;" : "",
                    "import java.lang.Override;",
                    hasString ? "import java.lang.String;" : "",
                    hasIntentData || needsIntentHelper ? "import onactivityresult.IntentHelper;" : "",
                    "import onactivityresult.internal.IOnActivityResult;",
    
                    "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                        "@Override",
                        "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                            stringArrayToString(code),
                        "}",
                    "}")
            );
            //@formatter:on

            assertAbout(javaSource()).that(source).compilesWithoutError();
            assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).compilesWithoutError().and().generatesSources(generation);
        }

        public void succeeds() {
            assertAbout(javaSource()).that(source).compilesWithoutError();
            assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).compilesWithoutError();
        }

        public void failsWithErrorMessage(final String errorMessage, final int lineNumber) {
            assertAbout(javaSource()).that(source).compilesWithoutError();
            assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).failsToCompile().withErrorContaining(errorMessage).in(source).onLine(headLines + lineNumber);
        }
    }
}
