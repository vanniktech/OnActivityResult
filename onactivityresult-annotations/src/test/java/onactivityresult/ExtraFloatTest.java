package onactivityresult;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Test;

public class ExtraFloatTest {
    @Test
    public void testAnnotationRetention() {
        final Retention retention = ExtraFloat.class.getAnnotation(Retention.class);
        assertEquals(RetentionPolicy.CLASS, retention.value());
    }

    @Test
    public void testAnnotationTarget() {
        final Target retention = ExtraFloat.class.getAnnotation(Target.class);
        final ElementType[] elementTypes = retention.value();
        assertArrayEquals(new ElementType[] { ElementType.PARAMETER }, elementTypes);
    }
}
