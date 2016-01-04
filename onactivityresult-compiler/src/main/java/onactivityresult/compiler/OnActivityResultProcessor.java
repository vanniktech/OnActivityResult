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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import onactivityresult.IntentData;
import onactivityresult.OnActivityResult;

import static javax.tools.Diagnostic.Kind.ERROR;

@AutoService(Processor.class)
public class OnActivityResultProcessor extends AbstractProcessor {
    static final String ACTIVITY_RESULT_CLASS_SUFFIX = "$$OnActivityResult";

    private static final String FQN_ANDROID_INTENT = "android.content.Intent";
    private static final String FQN_ANDROID_URI    = "android.net.Uri";

    private static final int RESULT_CANCELED   = 0;
    private static final int RESULT_OK         = -1;
    private static final int RESULT_FIRST_USER = 1;

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
        supportedAnnotations.add(IntentData.class.getCanonicalName());

        return supportedAnnotations;
    }

    @Override
    public boolean process(final Set<? extends TypeElement> elements, final RoundEnvironment environment) {
        final Map<String, ActivityResultClass> activityResultClasses = new HashMap<>();

        final AnnotatedMethodParameters annotatedParameters = this.getAnnotatedMethodParameters(environment);

        this.handleOnActivityResultAnnotation(activityResultClasses, environment, annotatedParameters);

        this.writeActivityResultClasses(activityResultClasses);

        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private AnnotatedMethodParameters getAnnotatedMethodParameters(final RoundEnvironment environment) {
        final AnnotatedMethodParameters annotatedParameters = new AnnotatedMethodParameters();

        this.getIntentDataMethodParameters(environment, annotatedParameters);

        return annotatedParameters;
    }

    private void getIntentDataMethodParameters(final RoundEnvironment environment, final AnnotatedMethodParameters annotatedParameters) {
        final Class<IntentData> annotation = IntentData.class;
        final Set<? extends Element> intentDatas = environment.getElementsAnnotatedWith(annotation);

        for (final Element intentData : intentDatas) {
            if (!SuperficialValidation.validateElement(intentData)) {
                continue;
            }

            try {
                final Element enclosingElement = intentData.getEnclosingElement();

                if (!Utils.isParameter(intentData)) {
                    throw new OnActivityResultProcessingException(enclosingElement, "@%s annotation must be on a method parameter", annotation.getSimpleName());
                } else if (!intentData.asType().toString().equals(FQN_ANDROID_URI)) {
                    throw new OnActivityResultProcessingException(enclosingElement, "@%s parameters must be of type %s", annotation.getSimpleName(), FQN_ANDROID_URI);
                } else {
                    final ExecutableElement executableElement = (ExecutableElement) enclosingElement;
                    annotatedParameters.put(executableElement, annotation, Parameter.createIntentData());
                }
            } catch (final OnActivityResultProcessingException e) {
                e.printError(processingEnv);
            }
        }
    }

    private void handleOnActivityResultAnnotation(final Map<String, ActivityResultClass> activityResultClasses, final RoundEnvironment environment, final AnnotatedMethodParameters annotatedParameters) {
        final Class<OnActivityResult> annotation = OnActivityResult.class;
        final Set<? extends Element> elements = environment.getElementsAnnotatedWith(annotation);

        for (final Element element : elements) {
            if (!SuperficialValidation.validateElement(element)) {
                continue;
            }

            try {
                if (!Utils.isMethod(element)) {
                    throw new OnActivityResultProcessingException(element, "@%s annotation must be on a method", annotation.getSimpleName());
                }

                final ExecutableElement executableElement = (ExecutableElement) element;

                this.checkFieldModifiers(executableElement, annotation, Modifier.PRIVATE, Modifier.STATIC);
                this.checkAnnotationMethods(executableElement);

                final ParameterList parameters = this.getParametersForMethod(executableElement, annotation, annotatedParameters);
                final ActivityResultClass activityResultClass = this.getOrCreateActivityResultClassInstance(activityResultClasses, element);
                final OnActivityResult onActivityResult = executableElement.getAnnotation(annotation);
                final RequestCode requestCode = new RequestCode(onActivityResult.requestCode());
                final int[] resultCodes = onActivityResult.resultCodes();
                activityResultClass.add(new MethodCall(executableElement, parameters, resultCodes), requestCode);
            } catch (final OnActivityResultProcessingException error) {
                error.printError(processingEnv);
            }
        }
    }

    private ParameterList getParametersForMethod(final ExecutableElement executableElement, final Class<? extends Annotation> annotation, final AnnotatedMethodParameters annotatedParameters) throws OnActivityResultProcessingException {
        final List<? extends VariableElement> methodParameters = executableElement.getParameters();
        final ParameterList parameters = new ParameterList();

        final int methodParametersSize = methodParameters.size();

        if (methodParametersSize > 0) {
            for (final VariableElement methodParameter : methodParameters) {
                final IntentData intentDataAnnotation = methodParameter.getAnnotation(IntentData.class);

                if (intentDataAnnotation != null) {
                    parameters.add(annotatedParameters.get(executableElement, IntentData.class));
                } else {
                    parameters.add(this.getParameterTypeFromVariableElement(methodParameter));
                }
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

    private void checkAnnotationMethods(final ExecutableElement element) throws OnActivityResultProcessingException {
        final OnActivityResult annotation = element.getAnnotation(OnActivityResult.class);

        if (annotation.requestCode() < 0) {
            throw new OnActivityResultProcessingException(element, "RequestCodes must be >= 0");
        }

        final int[] filterResultCodes = annotation.resultCodes();
        final Set<Integer> set = new HashSet<>(filterResultCodes.length);

        for (final int filterResultCode : filterResultCodes) {
            if (filterResultCode != RESULT_CANCELED && filterResultCode != RESULT_FIRST_USER && filterResultCode != RESULT_OK) {
                throw new OnActivityResultProcessingException(element, "Invalid resultCode %s", filterResultCode);
            }

            if (set.contains(filterResultCode)) {
                throw new OnActivityResultProcessingException(element, "Duplicate resultCode %s", filterResultCode);
            } else {
                set.add(filterResultCode);
            }
        }
    }

    private ActivityResultClass getOrCreateActivityResultClassInstance(final Map<String, ActivityResultClass> activityResultClasses, final Element element) {
        final TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        final String targetType = enclosingElement.getQualifiedName().toString();
        final ActivityResultClass cachedActivityResultClass = activityResultClasses.get(targetType);

        if (cachedActivityResultClass == null) {
            final String packageName = this.getPackageName(enclosingElement);
            final String className = Utils.getClassName(enclosingElement, packageName) + ACTIVITY_RESULT_CLASS_SUFFIX;

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
                return packageName + '.' + Utils.getClassName(currentTypeElement, packageName) + ACTIVITY_RESULT_CLASS_SUFFIX;
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
