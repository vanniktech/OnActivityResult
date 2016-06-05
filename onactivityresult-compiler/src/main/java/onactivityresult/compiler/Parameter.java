package onactivityresult.compiler;

import com.squareup.javapoet.ClassName;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

final class Parameter {
    static final String RESULT_CODE = "resultCode";
    static final String INTENT = "intent";
    static final String INTENT_DATA = "intentData";

    static Parameter create(final AnnotatedParameter annotatedParameter, final String simpleName, final ClassName className) {
        return new Parameter(simpleName, annotatedParameter, null, "null", className);
    }

    static Parameter create(final AnnotatedParameter annotatedParameter, final String simpleName, final String defaultValue) {
        return new Parameter(simpleName, annotatedParameter, null, defaultValue, null);
    }

    static Parameter create(final AnnotatedParameter annotatedParameter, final String simpleName) {
        return new Parameter(simpleName, annotatedParameter, null, "null", null);
    }

    static Parameter createResultCode() {
        return new Parameter(RESULT_CODE, null, null, null, null);
    }

    static Parameter createIntent() {
        return new Parameter(INTENT, null, null, null, null);
    }

    static Parameter createIntentData(final PreCondition preCondition) {
        return new Parameter(INTENT_DATA, AnnotatedParameter.INTENT_DATA, preCondition, null, null);
    }

    private final String name;
    private final String defaultValue;
    public final AnnotatedParameter annotatedParameter;
    public final PreCondition preCondition;
    public final ClassName className;

    private Parameter(final String name, final AnnotatedParameter annotatedParameter, final PreCondition preCondition, final String defaultValue, final ClassName className) {
        this.name = name;
        this.annotatedParameter = annotatedParameter;
        this.preCondition = preCondition == null ? PreCondition.DEFAULT : preCondition;
        this.defaultValue = defaultValue;
        this.className = className;
    }

    String getName() {
        if (annotatedParameter != null) {
            if (annotatedParameter == AnnotatedParameter.INTENT_DATA) {
                return name + preCondition.getSuffix();
            }

            final String parameterName = className != null ? className.simpleName() : annotatedParameter.readableName();
            return name + "Extra" + parameterName;
        }

        return name;
    }

    private int getDefaultValueHashCode() {
        return defaultValue != null ? defaultValue.hashCode() : Integer.MAX_VALUE; // Need for distinguishing String("") & String(null)
    }

    String getKey() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Parameter parameter = (Parameter) o;
        return name != null ? name.equals(parameter.name) : parameter.name == null && (defaultValue != null ? defaultValue.equals(parameter.defaultValue) : parameter.defaultValue == null && annotatedParameter == parameter.annotatedParameter && preCondition == parameter.preCondition && (className != null ? className.equals(parameter.className) : parameter.className == null));
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + getDefaultValueHashCode();
        result = 31 * result + (annotatedParameter != null ? annotatedParameter.hashCode() : 0);
        result = 31 * result + preCondition.hashCode();
        result = 31 * result + (className != null ? className.hashCode() : 0);
        return result;
    }

    enum PreCondition {
        DEFAULT(""), NONNULL("NonNull"), NULLABLE("Nullable");

        static PreCondition from(final List<? extends AnnotationMirror> annotationMirrors) {
            for (final AnnotationMirror annotationMirror : annotationMirrors) {
                final String[] parts = annotationMirror.toString().split("\\.");

                if (parts.length > 0) {
                    final String last = parts[parts.length - 1];

                    if ("NotNull".equals(last) || "NonNull".equals(last)) {
                        return NONNULL;
                    }

                    if ("Nullable".equals(last)) {
                        return NULLABLE;
                    }
                }
            }

            return DEFAULT;
        }

        private final String suffix;

        PreCondition(final String suffix) {
            this.suffix = suffix;
        }

        String getSuffix() {
            return suffix;
        }
    }
}
