package onactivityresult.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

final class ElementUtils {
    static boolean isParameter(final Element element) {
        return element.getKind() == ElementKind.PARAMETER && (element instanceof VariableElement);
    }

    static boolean isMethod(final Element element) {
        return element.getKind() == ElementKind.METHOD && (element instanceof ExecutableElement);
    }

    private ElementUtils() {
        throw new AssertionError("No instances.");
    }
}
