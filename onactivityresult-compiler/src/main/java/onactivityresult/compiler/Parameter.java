package onactivityresult.compiler;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;

final class Parameter {
    static final String RESULT_CODE = "resultCode";
    static final String INTENT      = "intent";
    static final String INTENT_DATA = "intentData";

    static Parameter create(final AnnotatedParameter annotatedParameter, final String simpleName) {
        return new Parameter(simpleName, annotatedParameter, null);
    }

    static Parameter createResultCode() {
        return new Parameter(RESULT_CODE, null, null);
    }

    static Parameter createIntent() {
        return new Parameter(INTENT, null, null);
    }

    static Parameter createIntentData(final PreCondition preCondition) {
        return new Parameter(INTENT_DATA, AnnotatedParameter.INTENT_DATA, preCondition);
    }

    private final String            name;
    public final AnnotatedParameter annotatedParameter;
    public final PreCondition       preCondition;

    private Parameter(final String name, final AnnotatedParameter annotatedParameter, final PreCondition preCondition) {
        this.name = name;
        this.annotatedParameter = annotatedParameter;
        this.preCondition = preCondition == null ? PreCondition.DEFAULT : preCondition;
    }

    String getName() {
        if (annotatedParameter != null) {
            if (annotatedParameter == AnnotatedParameter.INTENT_DATA) {
                return name + preCondition.getSuffix();
            }

            return name + "Extra";
        }

        return name;
    }

    String getKey() {
        return name;
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
        return name != null ? name.equals(parameter.name) : parameter.name == null && annotatedParameter == parameter.annotatedParameter && preCondition == parameter.preCondition;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (annotatedParameter != null ? annotatedParameter.hashCode() : 0);
        result = 31 * result + preCondition.hashCode();
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
