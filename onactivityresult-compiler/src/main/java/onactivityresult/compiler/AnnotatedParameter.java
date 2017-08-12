package onactivityresult.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

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

public enum AnnotatedParameter {
    BOOLEAN(ExtraBoolean.class, TypeVariableName.BOOLEAN) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraBoolean extraAnnotation = element.getAnnotation(ExtraBoolean.class);
            final boolean defaultValue = extraAnnotation != null && extraAnnotation.defaultValue();
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    BYTE(ExtraByte.class, TypeVariableName.BYTE) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraByte extraAnnotation = element.getAnnotation(ExtraByte.class);
            final byte defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    CHAR(ExtraChar.class, TypeVariableName.CHAR) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraChar extraAnnotation = element.getAnnotation(ExtraChar.class);
            final char defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf((int) defaultValue));
        }
    },
    DOUBLE(ExtraDouble.class, TypeVariableName.DOUBLE) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraDouble extraAnnotation = element.getAnnotation(ExtraDouble.class);
            final double defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0d;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    FLOAT(ExtraFloat.class, TypeVariableName.FLOAT) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraFloat extraAnnotation = element.getAnnotation(ExtraFloat.class);
            final float defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0.f;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    INT(ExtraInt.class, TypeVariableName.INT) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraInt extraAnnotation = element.getAnnotation(ExtraInt.class);
            final int defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    LONG(ExtraLong.class, TypeVariableName.LONG) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraLong extraAnnotation = element.getAnnotation(ExtraLong.class);
            final long defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0L;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    SHORT(ExtraShort.class, TypeVariableName.SHORT) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraShort extraAnnotation = element.getAnnotation(ExtraShort.class);
            final short defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : 0;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, String.valueOf(defaultValue));
        }
    },
    STRING(ExtraString.class, ClassName.get(String.class)) {
        @Override
        Parameter createParameter(final Element element) {
            final ExtraString extraAnnotation = element.getAnnotation(ExtraString.class);
            final String defaultValue = extraAnnotation != null ? extraAnnotation.defaultValue() : null;
            final String parameterName = getParameterName(element, extraAnnotation != null && extraAnnotation.name().length() > 0 ? extraAnnotation.name() : null);
            return Parameter.create(this, parameterName, defaultValue);
        }
    },
    CHAR_SEQUENCE(Extra.class, ClassName.get(CharSequence.class)) {
        @Override
        Parameter createParameter(final Element element) {
            final String parameterName = getParameterName(element, null);
            return Parameter.create(this, parameterName);
        }
    },
    BUNDLE(Extra.class, ClassName.get("android.os", "Bundle")) {
        @Override
        Parameter createParameter(final Element element) {
            final String parameterName = getParameterName(element, null);
            return Parameter.create(this, parameterName);
        }
    },
    SERIALIZABLE(Extra.class, ClassName.get(Serializable.class)) {
        @Override
        Parameter createParameter(final Element element) {
            final ClassName className = (ClassName) TypeVariableName.get(element.asType());
            final String parameterName = getParameterName(element, null);
            return Parameter.create(this, parameterName, className);
        }
    },
    PARCELABLE(Extra.class, ClassName.get("android.os", "Parcelable")) {
        @Override
        Parameter createParameter(final Element element) {
            final ClassName className = (ClassName) TypeVariableName.get(element.asType());
            final String parameterName = getParameterName(element, null);
            return Parameter.create(this, parameterName, className);
        }
    },
    INTENT_DATA(IntentData.class, ClassName.get("android.net", "Uri")) {
        @Override
        Parameter createParameter(final Element element) {
            final Parameter.PreCondition preCondition = Parameter.PreCondition.from(element.getAnnotationMirrors());
            return Parameter.createIntentData(preCondition);
        }
    };

    static String getParameterName(final Element element, final String other) {
        final Extra extra = element.getAnnotation(Extra.class);
        final String name = extra != null ? extra.name() : null;

        if (name != null && name.length() > 0) {
            return name;
        } else if (other != null && other.length() > 0) {
            return other;
        }

        return element.getSimpleName().toString();
    }

    public final Class<? extends Annotation> annotation;
    public final TypeName type;

    AnnotatedParameter(final Class<? extends Annotation> annotation, final TypeName type) {
        this.annotation = annotation;
        this.type = type;
    }

    abstract Parameter createParameter(Element element);

    public String readableName() {
        final String[] split = this.name().split("_");
        final StringBuilder sb = new StringBuilder();

        for (final String string : split) {
            sb.append(string.charAt(0)).append(string.substring(1).toLowerCase(Locale.US));
        }

        return sb.toString();
    }
}
