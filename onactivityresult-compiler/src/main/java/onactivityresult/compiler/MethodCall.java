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

    String getParameters() {
        return parameters.toString();
    }

    boolean needsIntentData() {
        return parameters.needsIntentData();
    }

    Parameter.PreCondition getIntentDataPrecondition() {
        return parameters.getIntentDataPrecondition();
    }

    ResultCodes getResultCodes() {
        return resultCodes;
    }
}
