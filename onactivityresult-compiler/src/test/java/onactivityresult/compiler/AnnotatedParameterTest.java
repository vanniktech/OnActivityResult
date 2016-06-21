package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AnnotatedParameterTest {
    @Test
    public void readableName() {
        assertEquals("Boolean", AnnotatedParameter.BOOLEAN.readableName());
        assertEquals("Byte", AnnotatedParameter.BYTE.readableName());
        assertEquals("Char", AnnotatedParameter.CHAR.readableName());
        assertEquals("Double", AnnotatedParameter.DOUBLE.readableName());
        assertEquals("Float", AnnotatedParameter.FLOAT.readableName());
        assertEquals("Int", AnnotatedParameter.INT.readableName());
        assertEquals("Long", AnnotatedParameter.LONG.readableName());
        assertEquals("Short", AnnotatedParameter.SHORT.readableName());
        assertEquals("String", AnnotatedParameter.STRING.readableName());
        assertEquals("IntentData", AnnotatedParameter.INTENT_DATA.readableName());
        assertEquals("CharSequence", AnnotatedParameter.CHAR_SEQUENCE.readableName());
        assertEquals("Bundle", AnnotatedParameter.BUNDLE.readableName());
        assertEquals("Serializable", AnnotatedParameter.SERIALIZABLE.readableName());
        assertEquals("Parcelable", AnnotatedParameter.PARCELABLE.readableName());
    }
}
