package onactivityresult.compiler;

import java.util.Arrays;

import javax.lang.model.element.ExecutableElement;

final class MethodCall {
    private final ParameterList     parameters;
    private final ExecutableElement method;
    private final int[]             resultCodes;

    MethodCall(final ExecutableElement method, final ParameterList parameters, final int... resultCodes) {
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

    int[] getResultCodes() {
        return Arrays.copyOf(resultCodes, resultCodes.length);
    }
}
