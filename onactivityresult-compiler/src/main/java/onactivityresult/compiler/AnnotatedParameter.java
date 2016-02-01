package onactivityresult.compiler;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

import onactivityresult.ExtraBoolean;
import onactivityresult.ExtraByte;
import onactivityresult.ExtraChar;
import onactivityresult.ExtraDouble;
import onactivityresult.ExtraFloat;
import onactivityresult.ExtraInt;
import onactivityresult.ExtraLong;
import onactivityresult.ExtraShort;
import onactivityresult.ExtraString;
import onactivityresult.IntentData;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

public enum AnnotatedParameter {
    BOOLEAN {
        @Override
        TypeName asType() {
            return TypeVariableName.BOOLEAN;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraBoolean.class;
        }
    },
    BYTE {
        @Override
        TypeName asType() {
            return TypeVariableName.BYTE;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraByte.class;
        }
    },
    CHAR {
        @Override
        TypeName asType() {
            return TypeVariableName.CHAR;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraChar.class;
        }
    },
    DOUBLE {
        @Override
        TypeName asType() {
            return TypeVariableName.DOUBLE;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraDouble.class;
        }
    },
    FLOAT {
        @Override
        TypeName asType() {
            return TypeVariableName.FLOAT;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraFloat.class;
        }
    },
    INT {
        @Override
        TypeName asType() {
            return TypeVariableName.INT;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraInt.class;
        }
    },
    LONG {
        @Override
        TypeName asType() {
            return TypeVariableName.LONG;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraLong.class;
        }
    },
    SHORT {
        @Override
        TypeName asType() {
            return TypeVariableName.SHORT;
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraShort.class;
        }
    },
    STRING {
        @Override
        TypeName asType() {
            return ClassName.get(String.class);
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return ExtraString.class;
        }
    },
    INTENT_DATA {
        @Override
        TypeName asType() {
            return ClassName.get("android.net", "Uri");
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return IntentData.class;
        }

        @Override
        Parameter createParameter(final Element element) {
            final Parameter.PreCondition preCondition = Parameter.PreCondition.from(element.getAnnotationMirrors());
            return Parameter.createIntentData(preCondition);
        }
    };

    Parameter createParameter(final Element element) {
        return Parameter.create(this, element.getSimpleName().toString());
    }

    abstract TypeName asType();

    abstract Class<? extends Annotation> getAnnotation();
}
