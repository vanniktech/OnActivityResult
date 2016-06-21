package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
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
        assertEquals(true, Utils.isParameter(this.getVariableElement(ElementKind.PARAMETER)));

        assertEquals(false, Utils.isParameter(this.getVariableElement(ElementKind.METHOD)));
        assertEquals(false, Utils.isParameter(this.getVariableElement(ElementKind.FIELD)));

        assertEquals(false, Utils.isParameter(this.getMethodElement(ElementKind.METHOD)));
        assertEquals(false, Utils.isParameter(this.getMethodElement(ElementKind.FIELD)));
        assertEquals(false, Utils.isParameter(this.getMethodElement(ElementKind.PARAMETER)));
    }

    @Test
    public void isMethod() {
        assertEquals(true, Utils.isMethod(this.getMethodElement(ElementKind.METHOD)));

        assertEquals(false, Utils.isMethod(this.getMethodElement(ElementKind.PARAMETER)));
        assertEquals(false, Utils.isMethod(this.getMethodElement(ElementKind.FIELD)));

        assertEquals(false, Utils.isMethod(this.getVariableElement(ElementKind.METHOD)));
        assertEquals(false, Utils.isMethod(this.getVariableElement(ElementKind.FIELD)));
        assertEquals(false, Utils.isMethod(this.getVariableElement(ElementKind.PARAMETER)));
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
