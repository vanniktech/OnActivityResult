package onactivityresult.compiler;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

final class Utils {
    static boolean isParameter(final Element element) {
        return element.getKind() == ElementKind.PARAMETER && (element instanceof VariableElement);
    }

    static boolean isMethod(final Element element) {
        return element.getKind() == ElementKind.METHOD && (element instanceof ExecutableElement);
    }

    static String getClassName(final TypeElement type, final String packageName) {
        final int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private Utils() {
        throw new AssertionError("No instances.");
    }
}
