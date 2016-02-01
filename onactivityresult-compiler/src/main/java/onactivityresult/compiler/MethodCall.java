package onactivityresult.compiler;

import javax.lang.model.element.ExecutableElement;

final class MethodCall {
    private final ParameterList     parameters;
    private final ExecutableElement method;
    private final ResultCodes       resultCodes;

    MethodCall(final ExecutableElement method, final ParameterList parameters, final ResultCodes resultCodes) {
        this.method = method;
        this.parameters = parameters;
        this.resultCodes = resultCodes;
    }

    String getMethodName() {
        return method.getSimpleName().toString();
    }

    ResultCodes getResultCodes() {
        return resultCodes;
    }

    public ParameterList getParameterList() {
        return parameters;
    }
}
