package onactivityresult;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

import onactivityresult.compiler.OnActivityResultProcessor;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

public final class TestActivity {
    public static Builder create() {
        return new Builder();
    }

    private static String stringArrayToString(final String... code) {
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

        private Builder() {}

        public Builder hasIntentData() {
            this.hasIntentData = true;
            return this;
        }

        public Builder hasActivity() {
            this.hasActivity = true;
            return this;
        }

        public Builder hasIntent() {
            this.hasIntent = true;
            return this;
        }

        public Builder hasNullable() {
            this.hasNullable = true;
            return this;
        }

        public Builder hasNotNull() {
            this.hasNotNull = true;
            return this;
        }

        public Source build(final String... functions) {
            final List<String> code = new ArrayList<>();

            code.add("package test;");
            code.add("import onactivityresult.OnActivityResult;");

            if (this.hasIntentData) {
                code.add("import android.net.Uri;");
                code.add("import onactivityresult.IntentData;");
            }

            if (this.hasNullable) {
                code.add("import org.jetbrains.annotations.Nullable;");
            }

            if (this.hasNotNull) {
                code.add("import org.jetbrains.annotations.NotNull;");
            }

            if (this.hasActivity) {
                code.add("import android.app.Activity;");
            }

            if (this.hasIntent) {
                code.add("import android.content.Intent;");
            }

            code.add("public class TestActivity {");

            final int headLines = code.size();

            code.add(stringArrayToString(functions));
            code.add("}");

            return new Source(JavaFileObjects.forSourceString("test/TestActivity", Joiner.on('\n').join(code.toArray(new String[code.size()]))), this.hasIntentData, this.hasNullable || this.hasNotNull || this.hasIntentData, headLines);
        }
    }

    public static class Source {
        private final JavaFileObject source;
        private final boolean        needsIntentHelper;
        private final boolean        hasIntentData;
        private final int            headLines;

        public Source(final JavaFileObject source, final boolean hasIntentData, final boolean needsIntentHelper, final int headLines) {
            this.source = source;
            this.needsIntentHelper = needsIntentHelper;
            this.hasIntentData = hasIntentData;
            this.headLines = headLines;
        }

        public void generatesBody(final String... code) {
            //@formatter:off
            final JavaFileObject generation = JavaFileObjects.forSourceString("test/TestActivity$$OnActivityResult", Joiner.on('\n').join(
                    "// Generated code from OnActivityResult. Do not modify!",
                    "package test;",
    
                    "import android.content.Intent;",
                    this.hasIntentData ? "import android.net.Uri;" : "",
    
                    "import java.lang.Override;",
                    this.hasIntentData || this.needsIntentHelper ? "import onactivityresult.IntentHelper;" : "",
                    "import onactivityresult.internal.IOnActivityResult;",
    
                    "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                        "@Override",
                        "public void onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                            stringArrayToString(code),
                        "}",
                    "}")
            );
            //@formatter:on

            assertAbout(javaSource()).that(this.source).compilesWithoutError();
            assertAbout(javaSource()).that(this.source).processedWith(new OnActivityResultProcessor()).compilesWithoutError().and().generatesSources(generation);
        }

        public void succeeds() {
            assertAbout(javaSource()).that(this.source).compilesWithoutError();
            assertAbout(javaSource()).that(this.source).processedWith(new OnActivityResultProcessor()).compilesWithoutError();
        }

        public void failsWithErrorMessage(final String errorMessage, final int lineNumber) {
            assertAbout(javaSource()).that(this.source).compilesWithoutError();
            assertAbout(javaSource()).that(this.source).processedWith(new OnActivityResultProcessor()).failsToCompile().withErrorContaining(errorMessage).in(this.source).onLine(this.headLines + lineNumber);
        }
    }
}
