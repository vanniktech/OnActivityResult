package onactivityresult.compiler;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

final class ActivityResultClass {
    private static final String                 TARGET_VARIABLE_NAME        = "t";
    private static final String                 TYPE_VARIABLE_NAME          = TARGET_VARIABLE_NAME.toUpperCase(Locale.US);

    private static final String                 REQUEST_CODE_PARAMETER_NAME = "requestCode";
    private static final String                 ON_RESULT_METHOD_NAME       = "onResult";

    private static final ClassName              ACTIVITY_ON_RESULT          = ClassName.get("onactivityresult.internal", "IOnActivityResult");
    private static final ClassName              INTENT                      = ClassName.get("android.content", "Intent");
    private static final ClassName              URI                         = ClassName.get("android.net", "Uri");
    private static final ClassName              INTENT_HELPER               = ClassName.get("onactivityresult", "IntentHelper");

    private static final Comparator<MethodCall> METHOD_CALL_COMPARATOR      = new Comparator<MethodCall>() {
                                                                                @Override
                                                                                public int compare(final MethodCall lhs, final MethodCall rhs) {
                                                                                    return lhs.getResultCodes().compareTo(rhs.getResultCodes());
                                                                                }
                                                                            };

    private final String                        classPackage;
    private final String                        className;
    private final String                        targetClass;
    private String                              superActivityResultClass;

    private final ActivityResultMethodCalls     activityResultCalls         = new ActivityResultMethodCalls();

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

        this.createOnResultMethodBody(result);

        return result.build();
    }

    private void createOnResultMethodBody(final MethodSpec.Builder result) {
        this.addSuperCallIfNecessary(result);
        this.addRequestCodeControlFlows(result);
    }

    private void addSuperCallIfNecessary(final MethodSpec.Builder result) {
        if (superActivityResultClass != null) {
            result.addStatement("super.$L($L, $L, $L, $L)", ON_RESULT_METHOD_NAME, TARGET_VARIABLE_NAME, REQUEST_CODE_PARAMETER_NAME, Parameter.RESULT_CODE, Parameter.INTENT);
        }
    }

    private void addRequestCodeControlFlows(final MethodSpec.Builder result) {
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
        final Set<Parameter> existingParameters = new HashSet<>();
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
                existingParameters.clear();
            }

            if (hasResultCodeFilter) {
                final String ifExpression = resultCodes.getIfExpression();

                if (isFirstResultCodeIfStatement) {
                    result.beginControlFlow("if ($L)", ifExpression);
                    isFirstResultCodeIfStatement = false;
                } else {
                    result.nextControlFlow("else if ($L)", ifExpression);
                }

                existingParameters.clear();
            }

            for (final MethodCall methodCall : methodCalls) {
                final ParameterList parameterList = methodCall.getParameterList();

                this.addNecessaryParameters(result, existingParameters, parameterList);

                result.addStatement("$L.$L($L)", TARGET_VARIABLE_NAME, methodCall.getMethodName(), parameterList.toString());
            }

            final boolean isLast = !it.hasNext();

            if (isLast && !isMethodCallWithoutResultCodeAfterMethodCallWithResultCode && !isFirstResultCodeIfStatement) {
                result.endControlFlow();
            }
        }
    }

    private void addNecessaryParameters(final MethodSpec.Builder result, final Set<Parameter> existingParameters, final ParameterList parameterList) {
        for (final Parameter parameter : parameterList) {
            final boolean notPresent = !existingParameters.contains(parameter);

            if (notPresent) {
                if (AnnotatedParameter.INTENT_DATA == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getIntentData$L($L)", URI, parameter.getName(), INTENT_HELPER, parameter.preCondition.getSuffix(), Parameter.INTENT);
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.BOOLEAN == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getBooleanExtra($L, $S, $L)", boolean.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.BYTE == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getByteExtra($L, $S, (byte) $L)", byte.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.CHAR == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getCharExtra($L, $S, (char) $L)", char.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.DOUBLE == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getDoubleExtra($L, $S, $L)", double.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.FLOAT == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getFloatExtra($L, $S, $Lf)", float.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.INT == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getIntExtra($L, $S, $L)", int.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.LONG == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getLongExtra($L, $S, $LL)", long.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.SHORT == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getShortExtra($L, $S, (short) $L)", short.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                } else if (AnnotatedParameter.STRING == parameter.annotatedParameter) {
                    result.addStatement("final $T $L = $T.getStringExtra($L, $S, $S)", String.class, parameter.getName(), INTENT_HELPER, Parameter.INTENT, parameter.getKey(), parameter.getDefaultValue());
                    existingParameters.add(parameter);
                }
            }
        }
    }

    void setSuperActivityResultClass(final String superActivityResultClass) {
        this.superActivityResultClass = superActivityResultClass;
    }
}
