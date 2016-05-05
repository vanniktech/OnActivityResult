package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;
import com.squareup.javapoet.ClassName;

public class ParameterTest {
    @Test
    public void testConstructorConfirmsParameters() {
        PrivateConstructorChecker.forClass(Parameter.class).expectedWithParameters(String.class, AnnotatedParameter.class, Parameter.PreCondition.class, String.class, ClassName.class).check();
    }

    @Test
    public void testPreConditionSuffix() {
        assertEquals("Nullable", Parameter.PreCondition.NULLABLE.getSuffix());
        assertEquals("NonNull", Parameter.PreCondition.NONNULL.getSuffix());
        assertEquals("", Parameter.PreCondition.DEFAULT.getSuffix());
    }
}
