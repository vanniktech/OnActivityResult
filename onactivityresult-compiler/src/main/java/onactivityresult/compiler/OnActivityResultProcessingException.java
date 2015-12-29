package onactivityresult.compiler;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static javax.tools.Diagnostic.Kind.ERROR;

final class OnActivityResultProcessingException extends Exception {
    private final Element  element;
    private final String   message;
    private final Object[] args;

    OnActivityResultProcessingException(final Element element, final String message, final Object... args) {
        this.element = element;
        this.message = message;
        this.args = args;
    }

    void printError(final ProcessingEnvironment processingEnvironment) {
        final String location = this.getLocation();
        final String formattedMessage = (args.length > 0 ? String.format(message, args) : message) + ". " + location;
        processingEnvironment.getMessager().printMessage(ERROR, formattedMessage, element);
    }

    private String getLocation() {
        final TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        return String.format("(%s.%s)", enclosingElement.getQualifiedName(), element.getSimpleName());
    }
}
