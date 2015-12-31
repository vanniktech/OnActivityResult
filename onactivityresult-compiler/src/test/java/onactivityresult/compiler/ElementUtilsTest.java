package onactivityresult.compiler;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

import org.junit.Test;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ElementUtilsTest {
    @Test
    public void testConstructorShouldBePrivate() {
        PrivateConstructorChecker.forClass(ElementUtils.class).expectedTypeOfException(AssertionError.class).expectedExceptionMessage("No instances.").check();
    }

    @Test
    public void testIsParameter() {
        assertEquals(true, ElementUtils.isParameter(this.getVariableElement(ElementKind.PARAMETER)));

        assertEquals(false, ElementUtils.isParameter(this.getVariableElement(ElementKind.METHOD)));
        assertEquals(false, ElementUtils.isParameter(this.getVariableElement(ElementKind.FIELD)));

        assertEquals(false, ElementUtils.isParameter(this.getMethodElement(ElementKind.METHOD)));
        assertEquals(false, ElementUtils.isParameter(this.getMethodElement(ElementKind.FIELD)));
        assertEquals(false, ElementUtils.isParameter(this.getMethodElement(ElementKind.PARAMETER)));
    }

    @Test
    public void testIsMethod() {
        assertEquals(true, ElementUtils.isMethod(this.getMethodElement(ElementKind.METHOD)));

        assertEquals(false, ElementUtils.isMethod(this.getMethodElement(ElementKind.PARAMETER)));
        assertEquals(false, ElementUtils.isMethod(this.getMethodElement(ElementKind.FIELD)));

        assertEquals(false, ElementUtils.isMethod(this.getVariableElement(ElementKind.METHOD)));
        assertEquals(false, ElementUtils.isMethod(this.getVariableElement(ElementKind.FIELD)));
        assertEquals(false, ElementUtils.isMethod(this.getVariableElement(ElementKind.PARAMETER)));
    }

    private ExecutableElement getMethodElement(final ElementKind elementKind) {
        final ExecutableElement mock = mock(ExecutableElement.class);
        doReturn(elementKind).when(mock).getKind();
        return mock;
    }

    private VariableElement getVariableElement(final ElementKind elementKind) {
        final VariableElement mock = mock(VariableElement.class);
        doReturn(elementKind).when(mock).getKind();
        return mock;
    }
}
