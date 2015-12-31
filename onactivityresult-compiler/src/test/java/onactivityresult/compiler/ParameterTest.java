package onactivityresult.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ParameterTest {
    @Test
    public void testCreateResultCode() {
        final Parameter parameter = Parameter.createResultCode();
        assertEquals(Parameter.RESULT_CODE, parameter.getName());
        assertEquals(false, parameter.isIntentData());
    }

    @Test
    public void testCreateIntent() {
        final Parameter parameter = Parameter.createIntent();
        assertEquals(Parameter.INTENT, parameter.getName());
        assertEquals(false, parameter.isIntentData());
    }

    @Test
    public void testCreateIntentData() {
        final Parameter parameter = Parameter.createIntentData();
        assertEquals(Parameter.INTENT_DATA, parameter.getName());
        assertEquals(true, parameter.isIntentData());
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEquals() {
        assertTrue(Parameter.createIntent().equals(Parameter.createIntent()));
        assertTrue(Parameter.createResultCode().equals(Parameter.createResultCode()));
        assertFalse(Parameter.createIntent().equals(Parameter.createResultCode()));
        assertFalse(Parameter.createIntent().equals(Parameter.INTENT));
        assertFalse(Parameter.createIntent().equals(Parameter.INTENT_DATA));
        assertFalse(Parameter.createResultCode().equals(Parameter.RESULT_CODE));
        assertFalse(Parameter.createResultCode().equals(Parameter.INTENT_DATA));
    }

    @Test
    public void testHashCode() {
        assertEquals(Parameter.createIntent().hashCode(), Parameter.createIntent().hashCode());
        assertEquals(Parameter.createResultCode().hashCode(), Parameter.createResultCode().hashCode());
        assertEquals(Parameter.createIntentData().hashCode(), Parameter.createIntentData().hashCode());

        assertNotEquals(Parameter.createIntent().hashCode(), Parameter.createResultCode().hashCode());
        assertNotEquals(Parameter.createIntentData().hashCode(), Parameter.createResultCode().hashCode());
        assertNotEquals(Parameter.createIntentData().hashCode(), Parameter.createIntent().hashCode());
    }
}
