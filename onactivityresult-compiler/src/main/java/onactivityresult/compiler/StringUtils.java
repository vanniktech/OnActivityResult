package onactivityresult.compiler;

import java.util.List;
import java.util.Locale;

import javax.lang.model.element.VariableElement;

final class StringUtils {
    @SafeVarargs
    static <T> String getList(final String concatenator, final T... objects) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (objects != null && objects.length > 0) {
            for (final Object o : objects) {
                stringBuilder.append(o.toString().toLowerCase(Locale.US)).append(concatenator);
            }

            stringBuilder.setLength(stringBuilder.length() - concatenator.length());
        }

        return stringBuilder.toString();
    }

    static String getReadableParameters(final List<? extends VariableElement> parameters) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (parameters != null && !parameters.isEmpty()) {
            for (final VariableElement variableElement : parameters) {
                final String rawTypeName = variableElement.asType().toString(); // android.content.Intent
                final String[] parts = rawTypeName.split("\\.");
                final String readableType = parts[parts.length - 1]; // Intent
                stringBuilder.append(readableType).append(", ");
            }

            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

    private StringUtils() {
        throw new AssertionError("No instances.");
    }
}
