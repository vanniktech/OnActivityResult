package onactivityresult;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class OnActivityResultTest {
    @Test
    public void testAnnotationRetention() {
        final Retention retention = OnActivityResult.class.getAnnotation(Retention.class);
        assertEquals(RetentionPolicy.CLASS, retention.value());
    }

    @Test
    public void testAnnotationTarget() {
        final Target retention = OnActivityResult.class.getAnnotation(Target.class);
        final ElementType[] elementTypes = retention.value();
        assertArrayEquals(new ElementType[] { ElementType.METHOD }, elementTypes);
    }
}
