package onactivityresult.compiler;

import java.lang.annotation.Annotation;
import java.util.Locale;

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
        Parameter createParameter(final Element element) {
            final ExtraBoolean annotation = element.getAnnotation(ExtraBoolean.class);
            final boolean defaultValue = annotation != null && annotation.defaultValue();
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraByte annotation = element.getAnnotation(ExtraByte.class);
            final byte defaultValue = annotation != null ? annotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraChar annotation = element.getAnnotation(ExtraChar.class);
            final char defaultValue = annotation != null ? annotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf((int) defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraDouble annotation = element.getAnnotation(ExtraDouble.class);
            final double defaultValue = annotation != null ? annotation.defaultValue() : 0d;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraFloat annotation = element.getAnnotation(ExtraFloat.class);
            final float defaultValue = annotation != null ? annotation.defaultValue() : 0.f;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraInt annotation = element.getAnnotation(ExtraInt.class);
            final int defaultValue = annotation != null ? annotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraLong annotation = element.getAnnotation(ExtraLong.class);
            final long defaultValue = annotation != null ? annotation.defaultValue() : 0L;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraShort annotation = element.getAnnotation(ExtraShort.class);
            final short defaultValue = annotation != null ? annotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }

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
        Parameter createParameter(final Element element) {
            final ExtraString annotation = element.getAnnotation(ExtraString.class);
            final String defaultValue = annotation != null ? annotation.defaultValue() : "";
            return Parameter.create(this, element.getSimpleName().toString(), defaultValue);
        }

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
        Parameter createParameter(final Element element) {
            final Parameter.PreCondition preCondition = Parameter.PreCondition.from(element.getAnnotationMirrors());
            return Parameter.createIntentData(preCondition);
        }

        @Override
        TypeName asType() {
            return ClassName.get("android.net", "Uri");
        }

        @Override
        Class<? extends Annotation> getAnnotation() {
            return IntentData.class;
        }
    };

    abstract Parameter createParameter(final Element element);

    abstract TypeName asType();

    abstract Class<? extends Annotation> getAnnotation();

    public String readableName() {
        final String name = this.name();
        return name.charAt(0) + name.substring(1).toLowerCase(Locale.US);
    }
}
