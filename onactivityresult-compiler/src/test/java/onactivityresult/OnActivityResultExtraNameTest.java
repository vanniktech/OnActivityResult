package onactivityresult;

import org.junit.Test;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class OnActivityResultExtraNameTest {
    @Test
    public void extraName() {
        //@formatter:off
        TestActivity.create().hasExtra()
            .addImport("onactivityresult.ExtraBoolean")
            .addImport("onactivityresult.ExtraByte")
            .addImport("onactivityresult.ExtraChar")
            .addImport("onactivityresult.ExtraDouble")
            .addImport("onactivityresult.ExtraFloat")
            .addImport("onactivityresult.ExtraInt")
            .addImport("onactivityresult.ExtraLong")
            .addImport("onactivityresult.ExtraShort")
            .addImport("onactivityresult.ExtraString")
            .addImport("java.io.Serializable")
            .addImport("android.os.Parcelable")
            .addImport("android.os.Bundle")
            .build(
                "@OnActivityResult(requestCode = 3) public void extraBoolean(@ExtraBoolean(name = \"fancyBoolean\") final boolean booleanParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyBoolean\") final boolean param) {}",
                "@OnActivityResult(requestCode = 3) public void extraByte(@ExtraByte(name = \"fancyByte\") final byte byteParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyByte\") final byte param) {}",
                "@OnActivityResult(requestCode = 3) public void extraChar(@ExtraChar(name = \"fancyChar\") final char charParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyChar\") final char param) {}",
                "@OnActivityResult(requestCode = 3) public void extraDouble(@ExtraDouble(name = \"fancyDouble\") final double doubleParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyDouble\") final double param) {}",
                "@OnActivityResult(requestCode = 3) public void extraFloat(@ExtraFloat(name = \"fancyFloat\") final float floatParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyFloat\") final float param) {}",
                "@OnActivityResult(requestCode = 3) public void extraInt(@ExtraInt(name = \"fancyInt\") final int intParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyInt\") final int param) {}",
                "@OnActivityResult(requestCode = 3) public void extraLong(@ExtraLong(name = \"fancyLong\") final long longParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyLong\") final long param) {}",
                "@OnActivityResult(requestCode = 3) public void extraShort(@ExtraShort(name = \"fancyShort\") final short shortParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyShort\") final short param) {}",
                "@OnActivityResult(requestCode = 3) public void extraString(@ExtraString(name = \"fancyString\") final String stringParam) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyString\") final String param) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancySerializable\") final Serializable param) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyParcelable\") final Parcelable param) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyBundle\") final Bundle foo) {}",
                "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyCharSequence\") final CharSequence param) {}"
        ).needsBundle().needsParcelable().needsSerializable().generatesBody(
            "if (requestCode == 3) {",
                "final boolean fancyBooleanExtraBoolean = IntentHelper.getExtraBoolean(intent, \"fancyBoolean\", false);",
                "t.extraBoolean(fancyBooleanExtraBoolean);",
                "t.extra(fancyBooleanExtraBoolean);",
                "final byte fancyByteExtraByte = IntentHelper.getExtraByte(intent, \"fancyByte\", (byte) 0);",
                "t.extraByte(fancyByteExtraByte);",
                "t.extra(fancyByteExtraByte);",
                "final char fancyCharExtraChar = IntentHelper.getExtraChar(intent, \"fancyChar\", (char) 0);",
                "t.extraChar(fancyCharExtraChar);",
                "t.extra(fancyCharExtraChar);",
                "final double fancyDoubleExtraDouble = IntentHelper.getExtraDouble(intent, \"fancyDouble\", 0.0);",
                "t.extraDouble(fancyDoubleExtraDouble);",
                "t.extra(fancyDoubleExtraDouble);",
                "final float fancyFloatExtraFloat = IntentHelper.getExtraFloat(intent, \"fancyFloat\", 0.0f);",
                "t.extraFloat(fancyFloatExtraFloat);",
                "t.extra(fancyFloatExtraFloat);",
                "final int fancyIntExtraInt = IntentHelper.getExtraInt(intent, \"fancyInt\", 0);",
                "t.extraInt(fancyIntExtraInt);",
                "t.extra(fancyIntExtraInt);",
                "final long fancyLongExtraLong = IntentHelper.getExtraLong(intent, \"fancyLong\", 0L);",
                "t.extraLong(fancyLongExtraLong);",
                "t.extra(fancyLongExtraLong);",
                "final short fancyShortExtraShort = IntentHelper.getExtraShort(intent, \"fancyShort\", (short) 0);",
                "t.extraShort(fancyShortExtraShort);",
                "t.extra(fancyShortExtraShort);",
                "final String fancyStringExtraString = IntentHelper.getExtraString(intent, \"fancyString\", \"\");",
                "t.extraString(fancyStringExtraString);",
                "final String fancyStringExtraString_ = IntentHelper.getExtraString(intent, \"fancyString\", null);",
                "t.extra(fancyStringExtraString_);",
                "final Serializable fancySerializableExtraSerializable = IntentHelper.getExtraSerializable(intent, \"fancySerializable\", null);",
                "t.extra(fancySerializableExtraSerializable);",
                "final Parcelable fancyParcelableExtraParcelable = IntentHelper.getExtraParcelable(intent, \"fancyParcelable\", null);",
                "t.extra(fancyParcelableExtraParcelable);",
                "final Bundle fancyBundleExtraBundle = IntentHelper.getExtraBundle(intent, \"fancyBundle\", null);",
                "t.extra(fancyBundleExtraBundle);",
                "final CharSequence fancyCharSequenceExtraCharSequence = IntentHelper.getExtraCharSequence(intent, \"fancyCharSequence\", null);",
                "t.extra(fancyCharSequenceExtraCharSequence);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void extraNumericName() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"2134\") final boolean param) {}"
        ).generatesBody(
            "if (requestCode == 3) {",
                "final boolean _2134ExtraBoolean = IntentHelper.getExtraBoolean(intent, \"2134\", false);",
                "t.extra(_2134ExtraBoolean);",
                "didHandle = true;",
            "}"
        );
        //@formatter:on
    }

    @Test
    public void duplicateIntentNames() {
        //@formatter:off
        TestActivity.create().hasExtra().build(
            "@OnActivityResult(requestCode = 3) public void extra(@Extra(name = \"fancyBoolean\") final boolean param, @Extra final boolean fancyBoolean) {}"
        ).failsWithErrorMessage("@OnActivityResult methods must only have non duplicated intent names. Found duplicate fancyBoolean. (test.TestActivity.extra)", 1);
        //@formatter:on
    }
}
