package onactivityresult.compiler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import com.pushtorefresh.private_constructor_checker.PrivateConstructorChecker;

public class UtilsTest {
    @Test
    public void constructorShouldBePrivate() {
        PrivateConstructorChecker.forClass(Utils.class).expectedTypeOfException(AssertionError.class).expectedExceptionMessage("No instances.").check();
    }

    @Test
    public void isParameter() {
        assertTrue(Utils.isParameter(this.getVariableElement(ElementKind.PARAMETER)));

        assertFalse(Utils.isParameter(this.getVariableElement(ElementKind.METHOD)));
        assertFalse(Utils.isParameter(this.getVariableElement(ElementKind.FIELD)));

        assertFalse(Utils.isParameter(this.getMethodElement(ElementKind.METHOD)));
        assertFalse(Utils.isParameter(this.getMethodElement(ElementKind.FIELD)));
        assertFalse(Utils.isParameter(this.getMethodElement(ElementKind.PARAMETER)));
    }

    @Test
    public void isMethod() {
        assertTrue(Utils.isMethod(this.getMethodElement(ElementKind.METHOD)));

        assertFalse(Utils.isMethod(this.getMethodElement(ElementKind.PARAMETER)));
        assertFalse(Utils.isMethod(this.getMethodElement(ElementKind.FIELD)));

        assertFalse(Utils.isMethod(this.getVariableElement(ElementKind.METHOD)));
        assertFalse(Utils.isMethod(this.getVariableElement(ElementKind.FIELD)));
        assertFalse(Utils.isMethod(this.getVariableElement(ElementKind.PARAMETER)));
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
