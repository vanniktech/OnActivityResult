package onactivityresult.compiler;

import java.util.Iterator;

import javax.lang.model.element.ExecutableElement;

final class MethodCall {
    private final ParameterList     parameters;
    private final ExecutableElement method;

    MethodCall(final ExecutableElement method, final ParameterList parameters) {
        this.method = method;
        this.parameters = parameters;
    }

    String getMethodName() {
        return method.getSimpleName().toString();
    }

    String getParameters() {
        final StringBuilder stringBuilder = new StringBuilder();

        final Iterator<Parameter> iterator = parameters.iterator();

        if (iterator.hasNext()) {
            do {
                stringBuilder.append(iterator.next().getName()).append(", ");
            } while (iterator.hasNext());

            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }
}
