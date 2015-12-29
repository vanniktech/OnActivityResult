package onactivityresult.compiler;

import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import onactivityresult.OnActivityResult;

import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public class OnActivityResultProcessor extends AbstractProcessor {
    static final String         ACTIVITY_RESULT_CLASS_SUFFIX = "$$OnActivityResult";
    private static final String FQN_ANDROID_INTENT           = "android.content.Intent";
    private static final int    MAX_ALLOWED_PARAMETERS       = 2;

    private Filer    filer;
    private Elements elementUtils;

    @Override
    public void init(final ProcessingEnvironment environment) {
        synchronized (this) {
            super.init(environment);

            elementUtils = environment.getElementUtils();
            filer = environment.getFiler();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> supportedAnnotations = new HashSet<>();

        supportedAnnotations.add(OnActivityResult.class.getCanonicalName());

        return supportedAnnotations;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> elements, final RoundEnvironment environment) {
        final Map<String, ActivityResultClass> activityResultClasses = new HashMap<>();

        this.handleOnActivityResultAnnotation(activityResultClasses, environment);

        this.writeActivityResultClasses(activityResultClasses);

        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void handleOnActivityResultAnnotation(final Map<String, ActivityResultClass> activityResultClasses, final RoundEnvironment environment) {
        final Class<OnActivityResult> annotation = OnActivityResult.class;
        final Set<? extends Element> elements = environment.getElementsAnnotatedWith(annotation);

        for (final Element element : elements) {
            if (!SuperficialValidation.validateElement(element)) {
                continue;
            }

            try {
                if (element.getKind() != ElementKind.METHOD || !(element instanceof ExecutableElement)) {
                    throw new OnActivityResultProcessingException(element, "@%s annotation must be on a method", annotation.getSimpleName());
                }

                final ExecutableElement executableElement = (ExecutableElement) element;

                this.checkFieldModifiers(executableElement, annotation, Modifier.PRIVATE, Modifier.STATIC);
                this.checkRequestCode(executableElement);

                final ParameterList parameters = this.getParameters(executableElement, annotation);
                final ActivityResultClass activityResultClass = this.getOrCreateActivityResultClassInstance(activityResultClasses, element);
                final RequestCode requestCode = new RequestCode(executableElement.getAnnotation(annotation).requestCode());
                activityResultClass.add(new MethodCall(executableElement, parameters), requestCode);
            } catch (final OnActivityResultProcessingException error) {
                error.printError(processingEnv);
            }
        }
    }

    private ParameterList getParameters(final ExecutableElement executableElement, final Class<? extends Annotation> annotation) throws OnActivityResultProcessingException {
        final List<? extends VariableElement> methodParameters = executableElement.getParameters();

        final int methodParametersSize = methodParameters.size();

        if (methodParametersSize > MAX_ALLOWED_PARAMETERS) {
            throw new OnActivityResultProcessingException(executableElement, "@%s methods can have at most %s parameter(s)", annotation.getSimpleName(), MAX_ALLOWED_PARAMETERS);
        }

        final ParameterList parameters = new ParameterList(MAX_ALLOWED_PARAMETERS);

        if (!methodParameters.isEmpty()) {
            parameters.add(this.getParameterTypeFromVariableElement(methodParameters.get(0)));

            if (methodParametersSize == MAX_ALLOWED_PARAMETERS) {
                parameters.add(this.getParameterTypeFromVariableElement(methodParameters.get(1)));
            }

            final boolean hasNotEnoughParameters = parameters.size() != methodParametersSize;

            if (hasNotEnoughParameters || !parameters.hasNumberOfDifferentParameters(methodParametersSize)) {
                final String readableParameters = StringUtils.getReadableParameters(methodParameters);
                throw new OnActivityResultProcessingException(executableElement, "@%s methods do not support the following parameter(s) - (%s)", annotation.getSimpleName(), readableParameters);
            }
        }

        return parameters;
    }

    private Parameter getParameterTypeFromVariableElement(final VariableElement variableElement) {
        final TypeMirror typeMirror = variableElement.asType();

        if (TypeVariableName.INT.equals(TypeVariableName.get(typeMirror))) {
            return Parameter.createResultCode();
        } else if (FQN_ANDROID_INTENT.equals(typeMirror.toString())) {
            return Parameter.createIntent();
        }

        return null;
    }

    private void checkRequestCode(final Element element) throws OnActivityResultProcessingException {
        final OnActivityResult annotation = element.getAnnotation(OnActivityResult.class);

        if (annotation.requestCode() < 0) {
            throw new OnActivityResultProcessingException(element, "RequestCodes must be >= 0");
        }
    }

    private ActivityResultClass getOrCreateActivityResultClassInstance(final Map<String, ActivityResultClass> activityResultClasses, final Element element) {
        final TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        final String targetType = enclosingElement.getQualifiedName().toString();
        final ActivityResultClass cachedActivityResultClass = activityResultClasses.get(targetType);

        if (cachedActivityResultClass == null) {
            final String packageName = this.getPackageName(enclosingElement);
            final String className = this.getClassName(enclosingElement, packageName);

            final ActivityResultClass activityResultClass = new ActivityResultClass(packageName, className, targetType);

            final String superActivityResultClass = this.findParent(enclosingElement, activityResultClasses);

            if (superActivityResultClass != null) {
                activityResultClass.setSuperActivityResultClass(superActivityResultClass);
            }

            activityResultClasses.put(targetType, activityResultClass);
            return activityResultClass;
        }

        return cachedActivityResultClass;
    }

    private String getClassName(final TypeElement type, final String packageName) {
        final int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$') + ACTIVITY_RESULT_CLASS_SUFFIX;
    }

    private String findParent(final TypeElement typeElement, final Map<String, ActivityResultClass> activityResultClasses) {
        TypeElement currentTypeElement = typeElement;
        TypeMirror type;

        while (true) {
            type = currentTypeElement.getSuperclass();

            if (type == null || type.getKind() == TypeKind.NONE) {
                return null;
            }

            final DeclaredType declaredType = (DeclaredType) type;
            currentTypeElement = (TypeElement) declaredType.asElement();

            if (activityResultClasses.containsKey(currentTypeElement.toString())) {
                final String packageName = this.getPackageName(currentTypeElement);
                return packageName + '.' + this.getClassName(currentTypeElement, packageName);
            }
        }
    }

    private void writeActivityResultClasses(final Map<String, ActivityResultClass> activityResultClasses) {
        for (final ActivityResultClass activityResultClass : activityResultClasses.values()) {
            final JavaFile javaFile = activityResultClass.brewJava();

            try {
                javaFile.writeTo(filer);
            } catch (final IOException e) {
                processingEnv.getMessager().printMessage(ERROR, String.format("Could not write to file %s", activityResultClass.toString()));
            }
        }
    }

    private void checkFieldModifiers(final Element element, final Class<? extends Annotation> annotation, final Modifier... modifiers) throws OnActivityResultProcessingException {
        final Set<Modifier> elementModifiers = element.getModifiers();

        for (final Modifier modifier : modifiers) {
            if (elementModifiers.contains(modifier)) {
                throw new OnActivityResultProcessingException(element, "@%s methods must not be %s", annotation.getSimpleName(), StringUtils.getList(" or ", modifiers));
            }
        }
    }

    private String getPackageName(final TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }
}
