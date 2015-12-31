package onactivityresult.compiler;

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
        return parameters.toString();
    }

    boolean needsIntentData() {
        return parameters.needsIntentData();
    }
}
