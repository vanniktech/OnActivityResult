package onactivityresult.compiler;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;

final class AnnotatedMethodParameters {
    private final Map<ExecutableElement, Map<Class<? extends Annotation>, Parameter>> mMap;

    AnnotatedMethodParameters() {
        mMap = new HashMap<>();
    }

    void put(final ExecutableElement executableElement, final Class<? extends Annotation> annotation, final Parameter parameter) {
        final Map<Class<? extends Annotation>, Parameter> cachedClassParameterMap = mMap.get(executableElement);

        if (cachedClassParameterMap == null) {
            final Map<Class<? extends Annotation>, Parameter> classParameterMap = new HashMap<>(1);
            classParameterMap.put(annotation, parameter);
            mMap.put(executableElement, classParameterMap);
        } else {
            cachedClassParameterMap.put(annotation, parameter);
        }
    }

    int size() {
        final Collection<Map<Class<? extends Annotation>, Parameter>> values = mMap.values();

        int size = 0;

        for (final Map<Class<? extends Annotation>, Parameter> value : values) {
            size += value.size();
        }

        return size;
    }

    Parameter get(final ExecutableElement executableElement, final Class<? extends Annotation> annotation) {
        final Map<Class<? extends Annotation>, Parameter> classParameterMap = mMap.get(executableElement);
        return classParameterMap != null ? classParameterMap.get(annotation) : null;
    }
}
