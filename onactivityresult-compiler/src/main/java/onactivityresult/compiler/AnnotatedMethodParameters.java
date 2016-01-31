package onactivityresult.compiler;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

final class AnnotatedMethodParameters {
    private final Map<ExecutableElement, Map<VariableElement, Parameter>> mMap;

    AnnotatedMethodParameters() {
        mMap = new HashMap<>();
    }

    void put(final ExecutableElement executableElement, final VariableElement variableElement, final Parameter parameter) {
        final Map<VariableElement, Parameter> cachedClassParameterMap = mMap.get(executableElement);

        if (cachedClassParameterMap == null) {
            final Map<VariableElement, Parameter> classParameterMap = new HashMap<>(1);
            classParameterMap.put(variableElement, parameter);
            mMap.put(executableElement, classParameterMap);
        } else {
            cachedClassParameterMap.put(variableElement, parameter);
        }
    }

    Parameter get(final ExecutableElement executableElement, final VariableElement variableElement) {
        final Map<VariableElement, Parameter> classParameterMap = mMap.get(executableElement);
        return classParameterMap != null ? classParameterMap.get(variableElement) : null;
    }
}
