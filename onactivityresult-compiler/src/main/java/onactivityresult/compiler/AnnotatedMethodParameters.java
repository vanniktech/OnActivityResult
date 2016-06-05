package onactivityresult.compiler;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import onactivityresult.OnActivityResult;

final class AnnotatedMethodParameters {
    private final Map<ExecutableElement, Map<VariableElement, Parameter>> map;

    AnnotatedMethodParameters() {
        map = new HashMap<>();
    }

    void put(final ExecutableElement executableElement, final VariableElement variableElement, final Parameter parameter) throws OnActivityResultProcessingException {
        final Map<VariableElement, Parameter> cachedClassParameterMap = map.get(executableElement);

        if (cachedClassParameterMap == null) {
            final Map<VariableElement, Parameter> classParameterMap = new HashMap<>(1);
            classParameterMap.put(variableElement, parameter);
            map.put(executableElement, classParameterMap);
        } else {
            cachedClassParameterMap.put(variableElement, parameter);
        }

        checkDuplicateParameterNames(executableElement);
    }

    private void checkDuplicateParameterNames(final ExecutableElement executableElement) throws OnActivityResultProcessingException {
        final Collection<Parameter> parameters = map.get(executableElement).values();
        final Set<String> parameterKeys = new HashSet<>(parameters.size());

        for (final Parameter parameter : parameters) {
            final String parameterName = parameter.getKey();
            if (parameterKeys.contains(parameterName)) {
                throw new OnActivityResultProcessingException(executableElement, "@%s methods must only have non duplicated intent names. Found duplicate %s", OnActivityResult.class.getSimpleName(), parameterName);
            }

            parameterKeys.add(parameterName);
        }
    }

    Parameter get(final ExecutableElement executableElement, final VariableElement variableElement) {
        final Map<VariableElement, Parameter> classParameterMap = map.get(executableElement);
        return classParameterMap != null ? classParameterMap.get(variableElement) : null;
    }
}
