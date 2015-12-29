package onactivityresult.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;
import java.util.Locale;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

final class ActivityResultClass {
    private static final String TARGET_VARIABLE_NAME = "t";
    private static final String TYPE_VARIABLE_NAME   = TARGET_VARIABLE_NAME.toUpperCase(Locale.US);

    private static final String REQUEST_CODE_PARAMETER_NAME = "requestCode";
    private static final String ON_RESULT_METHOD_NAME       = "onResult";

    private static final ClassName ACTIVITY_ON_RESULT = ClassName.get("onactivityresult.internal", "IOnActivityResult");
    private static final ClassName INTENT             = ClassName.get("android.content", "Intent");

    private final String classPackage;
    private final String className;
    private final String targetClass;
    private String       superActivityResultClass;

    private final ActivityResultMethodCalls activityResultCalls = new ActivityResultMethodCalls();

    ActivityResultClass(final String classPackage, final String className, final String targetClass) {
        this.classPackage = classPackage;
        this.className = className;
        this.targetClass = targetClass;
    }

    JavaFile brewJava() {
        final TypeSpec.Builder result = TypeSpec.classBuilder(className).addModifiers(PUBLIC);
        result.addTypeVariable(TypeVariableName.get(TYPE_VARIABLE_NAME, ClassName.bestGuess(targetClass)));

        final TypeVariableName typeVariableName = TypeVariableName.get(TYPE_VARIABLE_NAME);

        if (superActivityResultClass != null) {
            result.superclass(ParameterizedTypeName.get(ClassName.bestGuess(superActivityResultClass), typeVariableName));
        } else {
            result.addSuperinterface(ParameterizedTypeName.get(ACTIVITY_ON_RESULT, typeVariableName));
        }

        result.addMethod(this.createOnResultMethod());

        return JavaFile.builder(classPackage, result.build()).addFileComment("Generated code from OnActivityResult. Do not modify!").build();
    }

    void add(final MethodCall element, final RequestCode requestCode) {
        activityResultCalls.add(element, requestCode);
    }

    private MethodSpec createOnResultMethod() {
        final MethodSpec.Builder result = MethodSpec.methodBuilder(ON_RESULT_METHOD_NAME).addAnnotation(Override.class).addModifiers(PUBLIC);
        result.addParameter(TypeVariableName.get(TYPE_VARIABLE_NAME), TARGET_VARIABLE_NAME, FINAL);
        result.addParameter(int.class, REQUEST_CODE_PARAMETER_NAME, FINAL);
        result.addParameter(int.class, Parameter.RESULT_CODE, FINAL).addParameter(INTENT, Parameter.INTENT, FINAL);

        if (superActivityResultClass != null) {
            result.addStatement("super.$L($L, $L, $L, $L)", ON_RESULT_METHOD_NAME, TARGET_VARIABLE_NAME, REQUEST_CODE_PARAMETER_NAME, Parameter.RESULT_CODE, Parameter.INTENT);
        }

        final RequestCode[] requestCodes = activityResultCalls.getRequestCodes();

        for (int i = 0; i < requestCodes.length; i++) {
            final boolean isFirst = i == 0;
            final RequestCode requestCode = requestCodes[i];

            if (isFirst) {
                result.beginControlFlow("if ($L == $L)", REQUEST_CODE_PARAMETER_NAME, requestCode.requestCode);
            } else {
                result.nextControlFlow("else if ($L == $L)", REQUEST_CODE_PARAMETER_NAME, requestCode.requestCode);
            }

            final List<MethodCall> methodCallsForRequestCode = activityResultCalls.getMethodCallsForRequestCode(requestCode);

            for (final MethodCall methodCall : methodCallsForRequestCode) {
                final String methodName = methodCall.getMethodName();
                result.addStatement("$L.$L($L)", TARGET_VARIABLE_NAME, methodName, methodCall.getParameters());
            }
        }

        result.endControlFlow();

        return result.build();

    }

    void setSuperActivityResultClass(final String superActivityResultClass) {
        this.superActivityResultClass = superActivityResultClass;
    }
}
