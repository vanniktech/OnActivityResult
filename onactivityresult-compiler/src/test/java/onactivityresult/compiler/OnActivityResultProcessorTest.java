package onactivityresult.compiler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Set;

import javax.lang.model.SourceVersion;

public class OnActivityResultProcessorTest {
    @Test
    public void getSupportedAnnotationTypes() {
        final OnActivityResultProcessor onActivityResultProcessor = new OnActivityResultProcessor();
        final Set<String> supportedAnnotationTypes = onActivityResultProcessor.getSupportedAnnotationTypes();
        final String[] strings = supportedAnnotationTypes.toArray(new String[supportedAnnotationTypes.size()]);

        final String[] expected = new String[] {
            "onactivityresult.ExtraByte",
            "onactivityresult.ExtraLong",
            "onactivityresult.IntentData",
            "onactivityresult.ExtraDouble",
            "onactivityresult.ExtraString",
            "onactivityresult.ExtraFloat",
            "onactivityresult.ExtraChar",
            "onactivityresult.Extra",
            "onactivityresult.ExtraBoolean",
            "onactivityresult.OnActivityResult",
            "onactivityresult.ExtraInt",
            "onactivityresult.ExtraShort",
        };

        assertArrayEquals(expected, strings);
    }

    @Test
    public void getSupportedSourceVersion() {
        final OnActivityResultProcessor onActivityResultProcessor = new OnActivityResultProcessor();
        final SourceVersion actual = onActivityResultProcessor.getSupportedSourceVersion();

        assertEquals(SourceVersion.latestSupported(), actual);
    }

    @Test
    public void activityResultClassSuffix() {
        assertEquals("$$OnActivityResult", OnActivityResultProcessor.ACTIVITY_RESULT_CLASS_SUFFIX);
    }
}
