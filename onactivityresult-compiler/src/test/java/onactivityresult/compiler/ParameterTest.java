package onactivityresult.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ParameterTest {
    @Test
    public void testCreateResultCode() {
        assertEquals(Parameter.RESULT_CODE, Parameter.createResultCode().getName());
    }

    @Test
    public void testCreateIntent() {
        assertEquals(Parameter.INTENT, Parameter.createIntent().getName());
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void testEquals() {
        assertTrue(Parameter.createIntent().equals(Parameter.createIntent()));
        assertTrue(Parameter.createResultCode().equals(Parameter.createResultCode()));
        assertFalse(Parameter.createIntent().equals(Parameter.createResultCode()));
        assertFalse(Parameter.createIntent().equals(Parameter.INTENT));
        assertFalse(Parameter.createResultCode().equals(Parameter.RESULT_CODE));
    }

    @Test
    public void testHashCode() {
        assertEquals(Parameter.createIntent().hashCode(), Parameter.createIntent().hashCode());
        assertEquals(Parameter.createResultCode().hashCode(), Parameter.createResultCode().hashCode());
        assertNotEquals(Parameter.createIntent().hashCode(), Parameter.createResultCode().hashCode());
    }
}
