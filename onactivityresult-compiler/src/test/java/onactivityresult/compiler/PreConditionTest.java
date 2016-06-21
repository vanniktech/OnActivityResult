package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import javax.lang.model.element.AnnotationMirror;

public class PreConditionTest {
    @Test
    public void notNull() {
        assertEquals(Parameter.PreCondition.NONNULL, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("test.NotNull"))));
    }

    @Test
    public void nonNull() {
        assertEquals(Parameter.PreCondition.NONNULL, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("test.NonNull"))));
    }

    @Test
    public void nullable() {
        assertEquals(Parameter.PreCondition.NULLABLE, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("test.Nullable"))));
    }

    @Test
    public void defaultPrecondition() {
        assertEquals(Parameter.PreCondition.DEFAULT, Parameter.PreCondition.from(Collections.<AnnotationMirror>emptyList()));
        assertEquals(Parameter.PreCondition.DEFAULT, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("OnActivityResult"))));
        assertEquals(Parameter.PreCondition.DEFAULT, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("test.OnActivityResult"))));
        assertEquals(Parameter.PreCondition.DEFAULT, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("test.IntentData"))));
        assertEquals(Parameter.PreCondition.DEFAULT, Parameter.PreCondition.from(Collections.singletonList(this.createAnnotationMirror("test.Documented"))));
    }

    @Test
    public void multiple() {
        assertEquals(Parameter.PreCondition.NULLABLE, Parameter.PreCondition.from(Arrays.asList(this.createAnnotationMirror("test.Nullable"), this.createAnnotationMirror("test.NotNull"))));
    }

    private AnnotationMirror createAnnotationMirror(final String value) {
        final AnnotationMirror annotationMirror = mock(AnnotationMirror.class);
        when(annotationMirror.toString()).thenReturn(value);
        return annotationMirror;
    }
}
