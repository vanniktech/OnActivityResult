package onactivityresult.util;

import javax.tools.JavaFileObject;

import onactivityresult.compiler.OnActivityResultProcessor;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public final class JavaFileObjectUtils {
    public static void assertEquals(final JavaFileObject actualSource, final JavaFileObject firstExpectedSource, final JavaFileObject... restExpectedSource) {
        assertAbout(javaSource()).that(actualSource).compilesWithoutError();
        assertAbout(javaSource()).that(actualSource).processedWith(new OnActivityResultProcessor()).compilesWithoutError().and().generatesSources(firstExpectedSource, restExpectedSource);
    }

    public static void assertThatFailsWithErrorMessage(final JavaFileObject source, final String errorMessage, final int lineNumber) {
        assertAbout(javaSource()).that(source).compilesWithoutError();
        assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).failsToCompile().withErrorContaining(errorMessage).in(source).onLine(lineNumber);
    }

    public static void assertThatSucceeds(final JavaFileObject source) {
        assertAbout(javaSource()).that(source).compilesWithoutError();
        assertAbout(javaSource()).that(source).processedWith(new OnActivityResultProcessor()).compilesWithoutError();
    }

    private JavaFileObjectUtils() {
        throw new AssertionError("No instances.");
    }
}
