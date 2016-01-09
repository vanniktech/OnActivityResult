package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

public class ParameterTest {
    @Test
    public void testConstructorConfirmsParameters() {
        PrivateConstructorChecker.forClass(Parameter.class).expectedWithParameters(String.class, boolean.class).check();
    }

    @Test
    public void testCreateResultCode() {
        final Parameter parameter = Parameter.createResultCode();
        assertEquals(Parameter.RESULT_CODE, parameter.getName());
        assertEquals(false, parameter.isIntentData());
        assertEquals(Parameter.PreCondition.DEFAULT, parameter.getPreCondition());
    }

    @Test
    public void testCreateIntent() {
        final Parameter parameter = Parameter.createIntent();
        assertEquals(Parameter.INTENT, parameter.getName());
        assertEquals(false, parameter.isIntentData());
        assertEquals(Parameter.PreCondition.DEFAULT, parameter.getPreCondition());
    }

    @Test
    public void testCreateIntentData() {
        final Parameter parameter = Parameter.createIntentData();
        assertEquals(Parameter.INTENT_DATA, parameter.getName());
        assertEquals(true, parameter.isIntentData());
        assertEquals(Parameter.PreCondition.DEFAULT, parameter.getPreCondition());
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

    @Test
    public void testSetPrecondition() {
        final Parameter parameter = Parameter.createIntentData();
        parameter.setPreCondition(Parameter.PreCondition.NONNULL);

        assertEquals(Parameter.PreCondition.NONNULL, parameter.getPreCondition());

        parameter.setPreCondition(Parameter.PreCondition.NULLABLE);

        assertEquals(Parameter.PreCondition.NULLABLE, parameter.getPreCondition());
    }

    @Test
    public void testPreConditionSuffix() {
        assertEquals("Nullable", Parameter.PreCondition.NULLABLE.getSuffix());
        assertEquals("NonNull", Parameter.PreCondition.NONNULL.getSuffix());
        assertEquals("", Parameter.PreCondition.DEFAULT.getSuffix());
    }
}
