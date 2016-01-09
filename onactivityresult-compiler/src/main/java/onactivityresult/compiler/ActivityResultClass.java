package onactivityresult.compiler;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

final class ActivityResultClass {
    private static final String TARGET_VARIABLE_NAME = "t";
    private static final String TYPE_VARIABLE_NAME   = TARGET_VARIABLE_NAME.toUpperCase(Locale.US);

    private static final String REQUEST_CODE_PARAMETER_NAME = "requestCode";
    private static final String ON_RESULT_METHOD_NAME       = "onResult";

    private static final ClassName ACTIVITY_ON_RESULT = ClassName.get("onactivityresult.internal", "IOnActivityResult");
    private static final ClassName INTENT             = ClassName.get("android.content", "Intent");
    private static final ClassName URI                = ClassName.get("android.net", "Uri");
    private static final ClassName INTENT_HELPER      = ClassName.get("onactivityresult", "IntentHelper");

    private static final Comparator<MethodCall> METHOD_CALL_COMPARATOR = new Comparator<MethodCall>() {
        @Override
        public int compare(final MethodCall lhs, final MethodCall rhs) {
            return lhs.getResultCodes().compareTo(rhs.getResultCodes());
        }
    };

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

            final Map<ResultCodes, List<MethodCall>> methodCallsForRequestCode = this.getSortedMethodCallsGroupedByResultCodesFor(requestCode);
            this.addMethodCalls(result, methodCallsForRequestCode);
        }

        result.endControlFlow();

        return result.build();
    }

    private Map<ResultCodes, List<MethodCall>> getSortedMethodCallsGroupedByResultCodesFor(final RequestCode requestCode) {
        final List<MethodCall> methodCallsForRequestCode = activityResultCalls.getMethodCallsForRequestCode(requestCode);
        Collections.sort(methodCallsForRequestCode, METHOD_CALL_COMPARATOR);

        final Map<ResultCodes, List<MethodCall>> sortedMethodCallsGroupedByResultCodes = new TreeMap<>();

        for (final MethodCall methodCall : methodCallsForRequestCode) {
            final ResultCodes resultCodes = methodCall.getResultCodes();

            final List<MethodCall> methodCalls = sortedMethodCallsGroupedByResultCodes.get(resultCodes);

            if (methodCalls != null) {
                methodCalls.add(methodCall);
            } else {
                final List<MethodCall> methodCallList = new ArrayList<>();
                methodCallList.add(methodCall);
                sortedMethodCallsGroupedByResultCodes.put(resultCodes, methodCallList);
            }
        }

        return sortedMethodCallsGroupedByResultCodes;
    }

    private void addMethodCalls(final MethodSpec.Builder result, final Map<ResultCodes, List<MethodCall>> sortedMethodCallsGroupedByResultCodes) {
        final Map<Parameter.PreCondition, Boolean> appliedIntentDatas = new HashMap<>();
        boolean isFirstResultCodeIfStatement = true;

        final Iterator<Map.Entry<ResultCodes, List<MethodCall>>> it = sortedMethodCallsGroupedByResultCodes.entrySet().iterator();

        while (it.hasNext()) {
            final Map.Entry<ResultCodes, List<MethodCall>> resultCodesListEntry = it.next();

            final ResultCodes resultCodes = resultCodesListEntry.getKey();
            final List<MethodCall> methodCalls = resultCodesListEntry.getValue();

            final boolean hasResultCodeFilter = resultCodes.size() > 0;

            final boolean isMethodCallWithoutResultCodeAfterMethodCallWithResultCode = !hasResultCodeFilter && !isFirstResultCodeIfStatement;

            if (isMethodCallWithoutResultCodeAfterMethodCallWithResultCode) {
                result.endControlFlow();
                appliedIntentDatas.clear();
            }

            if (hasResultCodeFilter) {
                final String ifExpression = resultCodes.getIfExpression();

                if (isFirstResultCodeIfStatement) {
                    result.beginControlFlow("if ($L)", ifExpression);
                    isFirstResultCodeIfStatement = false;
                } else {
                    result.nextControlFlow("else if ($L)", ifExpression);
                }

                appliedIntentDatas.clear();
            }

            for (final MethodCall methodCall : methodCalls) {
                final Parameter.PreCondition intentDataPrecondition = methodCall.getIntentDataPrecondition();
                final boolean hasPrecondition = Boolean.TRUE.equals(appliedIntentDatas.get(intentDataPrecondition));

                if (methodCall.needsIntentData() && !hasPrecondition) {
                    final String suffix = intentDataPrecondition.getSuffix();
                    result.addStatement("final $1T $2L$3L = $4T.getIntentData$3L($5L)", URI, Parameter.INTENT_DATA, suffix, INTENT_HELPER, Parameter.INTENT);
                    appliedIntentDatas.put(methodCall.getIntentDataPrecondition(), true);
                }

                final String methodName = methodCall.getMethodName();
                result.addStatement("$L.$L($L)", TARGET_VARIABLE_NAME, methodName, methodCall.getParameters());
            }

            final boolean isLast = !it.hasNext();

            if (isLast && !isMethodCallWithoutResultCodeAfterMethodCallWithResultCode && !isFirstResultCodeIfStatement) {
                result.endControlFlow();
            }
        }
    }

    void setSuperActivityResultClass(final String superActivityResultClass) {
        this.superActivityResultClass = superActivityResultClass;
    }
}
