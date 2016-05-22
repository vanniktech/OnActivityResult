package onactivityresult.compiler;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Locale;

import javax.lang.model.element.Element;

import onactivityresult.Extra;
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
    BOOLEAN(ExtraBoolean.class, TypeVariableName.BOOLEAN) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraBoolean extraAnnotation = element.getAnnotation(ExtraBoolean.class);
            final boolean defaultValue = extraAnnotation != null && extraAnnotation.defaultValue();
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    BYTE(ExtraByte.class, TypeVariableName.BYTE) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraByte extraAnnotation = element.getAnnotation(ExtraByte.class);
            final byte defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    CHAR(ExtraChar.class, TypeVariableName.CHAR) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraChar extraAnnotation = element.getAnnotation(ExtraChar.class);
            final char defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf((int) defaultValue));
        }
    },
    DOUBLE(ExtraDouble.class, TypeVariableName.DOUBLE) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraDouble extraAnnotation = element.getAnnotation(ExtraDouble.class);
            final double defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0d;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    FLOAT(ExtraFloat.class, TypeVariableName.FLOAT) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraFloat extraAnnotation = element.getAnnotation(ExtraFloat.class);
            final float defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0.f;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    INT(ExtraInt.class, TypeVariableName.INT) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraInt extraAnnotation = element.getAnnotation(ExtraInt.class);
            final int defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    LONG(ExtraLong.class, TypeVariableName.LONG) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraLong extraAnnotation = element.getAnnotation(ExtraLong.class);
            final long defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0L;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    SHORT(ExtraShort.class, TypeVariableName.SHORT) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraShort extraAnnotation = element.getAnnotation(ExtraShort.class);
            final short defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            return Parameter.create(this, element.getSimpleName().toString(), String.valueOf(defaultValue));
        }
    },
    STRING(ExtraString.class, ClassName.get(String.class)) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraString extraAnnotation = element.getAnnotation(ExtraString.class);
            final String defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : "";
            return Parameter.create(this, element.getSimpleName().toString(), defaultValue);
        }
    },
    CHAR_SEQUENCE(Extra.class, ClassName.get(CharSequence.class)) {
        @Override
        Parameter createParameter(final Element element) {
            return Parameter.create(this, element.getSimpleName().toString(), "null");
        }
    },
    BUNDLE(Extra.class, ClassName.get("android.os", "Bundle")) {
        @Override
        Parameter createParameter(final Element element) {
            return Parameter.create(this, element.getSimpleName().toString(), "null");
        }
    },
    SERIALIZABLE(Extra.class, ClassName.get(Serializable.class)) {
        @Override
        Parameter createParameter(final Element element) {
            final ClassName className = (ClassName) TypeVariableName.get(element.asType());
            return Parameter.create(this, element.getSimpleName().toString(), "null", className);
        }
    },
    PARCELABLE(Extra.class, ClassName.get("android.os", "Parcelable")) {
        @Override
        Parameter createParameter(final Element element) {
            final ClassName className = (ClassName) TypeVariableName.get(element.asType());
            return Parameter.create(this, element.getSimpleName().toString(), "null", className);
        }
    },
    INTENT_DATA(IntentData.class, ClassName.get("android.net", "Uri")) {
        @Override
        Parameter createParameter(final Element element) {
            final Parameter.PreCondition preCondition = Parameter.PreCondition.from(element.getAnnotationMirrors());
            return Parameter.createIntentData(preCondition);
        }
    };

    private final Class<? extends Annotation> annotation;
    private final TypeName type;

    AnnotatedParameter(final Class<? extends Annotation> annotation, final TypeName type) {
        this.annotation = annotation;
        this.type = type;
    }

    abstract Parameter createParameter(final Element element);

    public final TypeName asType() {
        return type;
    }

    public final Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public String readableName() {
        final String[] split = this.name().split("_");
        final StringBuilder sb = new StringBuilder();

        for (final String string : split) {
            sb.append(string.charAt(0)).append(string.substring(1).toLowerCase(Locale.US));
        }

        return sb.toString();
    }
}
