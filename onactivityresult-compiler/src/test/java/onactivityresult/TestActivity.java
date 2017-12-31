package onactivityresult;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.tools.JavaFileObject;

import onactivityresult.compiler.OnActivityResultProcessor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

@SuppressWarnings("PMD.CyclomaticComplexity") final class TestActivity {
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

        private boolean hasExtra;

        private final List<String> imports = new ArrayList<>();
        private boolean needsIntentHelper;

        private List<String> extraCode;
        private String packageName;

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
            needsIntentHelper = true;
            return this;
        }

        public Builder hasExtra() {
            hasExtra = true;
            needsIntentHelper = true;
            return this;
        }

        public Builder hasNotNull() {
            hasNotNull = true;
            needsIntentHelper = true;
            return this;
        }

        public Builder withExtraCode(final String... code) {
            this.extraCode = new ArrayList<>();
            Collections.addAll(this.extraCode, code);
            return this;
        }

        @SuppressWarnings("PMD.NPathComplexity") public Source build(final String... functions) {
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

            if (hasExtra) {
                code.add("import onactivityresult.Extra;");
            }

            for (final String name : imports) {
                if (name.length() > 0) {
                    code.add("import " + name + ";");
                }
            }

            code.add("public class TestActivity {");

            final int headLines = code.size();

            code.add(stringArrayToString(functions));
            code.add("}");

            if (extraCode != null) {
                for (final String s : extraCode) {
                    code.add(s);
                }
            }

            final String packName = packageName != null ? packageName : "test";
            return new Source(JavaFileObjects.forSourceString(packName + ".TestActivity", Joiner.on('\n').join(code.toArray(new String[0]))), hasIntentData, needsIntentHelper, headLines, packName);
        }

        Builder addImport(final String name) {
            if (name.startsWith("onactivityresult.Extra")) {
                needsIntentHelper = true;
            }

            imports.add(name);
            return this;
        }

        public Builder withPackageName(final String name) {
            this.packageName = name;
            return this;
        }
    }

    public static class Source {
        private final JavaFileObject source;
        private final boolean needsIntentHelper;
        private final boolean hasIntentData;
        private final int headLines;
        private final String packageName;
        private boolean needsBundle;
        private boolean needsSerializable;
        private boolean needsParcelable;

        Source(final JavaFileObject source, final boolean hasIntentData, final boolean needsIntentHelper, final int headLines, final String packageName) {
            this.source = source;
            this.needsIntentHelper = needsIntentHelper;
            this.hasIntentData = hasIntentData;
            this.headLines = headLines;
            this.packageName = packageName;
        }

        Source needsBundle() {
            needsBundle = true;
            return this;
        }

        Source needsSerializable() {
            needsSerializable = true;
            return this;
        }

        Source needsParcelable() {
            needsParcelable = true;
            return this;
        }

        public void generatesBody(final String... code) {
            //@formatter:off
            final JavaFileObject generation = JavaFileObjects.forSourceString(packageName + ".TestActivity$$OnActivityResult", Joiner.on('\n').join(
                    "// Generated code from OnActivityResult. Do not modify!",
                    "package test;",
                    "import android.content.Intent;",

                    needsBundle ? "import android.os.Bundle;" : "",
                    needsParcelable ? "import android.os.Parcelable;" : "",
                    hasIntentData ? "import android.net.Uri;" : "",
                    needsSerializable ? "import java.io.Serializable;" : "",
                    "import javax.annotation.Generated;",
                    hasIntentData || needsIntentHelper ? "import onactivityresult.IntentHelper;" : "",
                    "import onactivityresult.internal.IOnActivityResult;",

                    "@Generated(\"onactivityresult.compiler.OnActivityResultProcessor\")",
                    "public class TestActivity$$OnActivityResult<T extends TestActivity> implements IOnActivityResult<T> {",
                        "@Override",
                        "public boolean onResult(final T t, final int requestCode, final int resultCode, final Intent intent) {",
                            "boolean didHandle = false;",
                            stringArrayToString(code),
                            "return didHandle;",
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
