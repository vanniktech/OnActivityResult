package onactivityresult.compiler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.lang.model.SourceVersion;

import org.junit.Test;

public class OnActivityResultProcessorTest {
    @Test
    public void testGetSupportedAnnotationTypes() {
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
            "onactivityresult.ExtraBoolean",
            "onactivityresult.OnActivityResult",
            "onactivityresult.ExtraInt",
            "onactivityresult.ExtraShort",
        };

        assertArrayEquals(expected, strings);
    }

    @Test
    public void testGetSupportedSourceVersion() {
        final OnActivityResultProcessor onActivityResultProcessor = new OnActivityResultProcessor();
        final SourceVersion actual = onActivityResultProcessor.getSupportedSourceVersion();

        assertEquals(SourceVersion.latestSupported(), actual);
    }

    @Test
    public void testActivityResultClassSuffix() {
        assertEquals("$$OnActivityResult", OnActivityResultProcessor.ACTIVITY_RESULT_CLASS_SUFFIX);
    }
}
