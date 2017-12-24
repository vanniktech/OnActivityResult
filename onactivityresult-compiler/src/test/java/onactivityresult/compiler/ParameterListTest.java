package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ParameterListTest {
    @Test
    public void addShouldNotAddNonNull() {
        final ParameterList parameters = new ParameterList();
        assertEquals(0, parameters.size());

        parameters.add(null);
        assertEquals(0, parameters.size());
    }

    @Test
    public void addShouldAddNonNull() {
        final ParameterList parameters = new ParameterList();
        assertEquals(0, parameters.size());

        parameters.add(Parameter.createIntent());
        assertEquals(1, parameters.size());
    }

    @Test
    public void hasNumberOfDifferentParameters() {
        final ParameterList parameters = new ParameterList();
        assertTrue(parameters.hasNumberOfDifferentParameters(0));

        parameters.add(Parameter.createIntent());
        assertTrue(parameters.hasNumberOfDifferentParameters(1));

        parameters.add(Parameter.createIntent()); // Adding the same is not different
        assertTrue(parameters.hasNumberOfDifferentParameters(1));

        parameters.add(Parameter.createResultCode()); // Adding different is different
        assertTrue(parameters.hasNumberOfDifferentParameters(2));

        parameters.add(Parameter.createResultCode()); // Adding the same is not different
        assertTrue(parameters.hasNumberOfDifferentParameters(2));
    }
}
