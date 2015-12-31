package onactivityresult.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterListTest {
    @Test
    public void testAddShouldNotAddNonNull() {
        final ParameterList parameters = new ParameterList();
        assertEquals(0, parameters.size());

        parameters.add(null);
        assertEquals(0, parameters.size());
    }

    @Test
    public void testAddShouldAddNonNull() {
        final ParameterList parameters = new ParameterList();
        assertEquals(0, parameters.size());

        parameters.add(Parameter.createIntent());
        assertEquals(1, parameters.size());
    }

    @Test
    public void testHasNumberOfDifferentParameters() {
        final ParameterList parameters = new ParameterList();
        assertEquals(true, parameters.hasNumberOfDifferentParameters(0));

        parameters.add(Parameter.createIntent());
        assertEquals(true, parameters.hasNumberOfDifferentParameters(1));

        parameters.add(Parameter.createIntent()); // Adding the same is not different
        assertEquals(true, parameters.hasNumberOfDifferentParameters(1));

        parameters.add(Parameter.createResultCode()); // Adding different is different
        assertEquals(true, parameters.hasNumberOfDifferentParameters(2));

        parameters.add(Parameter.createResultCode()); // Adding the same is not different
        assertEquals(true, parameters.hasNumberOfDifferentParameters(2));
    }

    @Test
    public void testNeedsIntentData() {
        final ParameterList parameters = new ParameterList();

        parameters.add(Parameter.createIntent());
        assertEquals(false, parameters.needsIntentData());

        parameters.add(Parameter.createIntentData());
        assertEquals(true, parameters.needsIntentData());

        parameters.add(Parameter.createResultCode());
        assertEquals(true, parameters.needsIntentData());
    }
}
