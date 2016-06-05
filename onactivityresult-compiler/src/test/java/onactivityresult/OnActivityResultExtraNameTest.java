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
                "final boolean fancyBooleanExtraBoolean_97196323 = IntentHelper.getExtraBoolean(intent, \"fancyBoolean\", false);",
                "t.extraBoolean(fancyBooleanExtraBoolean_97196323);",
                "t.extra(fancyBooleanExtraBoolean_97196323);",
                "final byte fancyByteExtraByte_48 = IntentHelper.getExtraByte(intent, \"fancyByte\", (byte) 0);",
                "t.extraByte(fancyByteExtraByte_48);",
                "t.extra(fancyByteExtraByte_48);",
                "final char fancyCharExtraChar_48 = IntentHelper.getExtraChar(intent, \"fancyChar\", (char) 0);",
                "t.extraChar(fancyCharExtraChar_48);",
                "t.extra(fancyCharExtraChar_48);",
                "final double fancyDoubleExtraDouble_47602 = IntentHelper.getExtraDouble(intent, \"fancyDouble\", 0.0);",
                "t.extraDouble(fancyDoubleExtraDouble_47602);",
                "t.extra(fancyDoubleExtraDouble_47602);",
                "final float fancyFloatExtraFloat_47602 = IntentHelper.getExtraFloat(intent, \"fancyFloat\", 0.0f);",
                "t.extraFloat(fancyFloatExtraFloat_47602);",
                "t.extra(fancyFloatExtraFloat_47602);",
                "final int fancyIntExtraInt_48 = IntentHelper.getExtraInt(intent, \"fancyInt\", 0);",
                "t.extraInt(fancyIntExtraInt_48);",
                "t.extra(fancyIntExtraInt_48);",
                "final long fancyLongExtraLong_48 = IntentHelper.getExtraLong(intent, \"fancyLong\", 0L);",
                "t.extraLong(fancyLongExtraLong_48);",
                "t.extra(fancyLongExtraLong_48);",
                "final short fancyShortExtraShort_48 = IntentHelper.getExtraShort(intent, \"fancyShort\", (short) 0);",
                "t.extraShort(fancyShortExtraShort_48);",
                "t.extra(fancyShortExtraShort_48);",
                "final String fancyStringExtraString_0 = IntentHelper.getExtraString(intent, \"fancyString\", \"\");",
                "t.extraString(fancyStringExtraString_0);",
                "final String fancyStringExtraString_2147483647 = IntentHelper.getExtraString(intent, \"fancyString\", null);",
                "t.extra(fancyStringExtraString_2147483647);",
                "final Serializable fancySerializableExtraSerializable_3392903 = IntentHelper.getExtraSerializable(intent, \"fancySerializable\", null);",
                "t.extra(fancySerializableExtraSerializable_3392903);",
                "final Parcelable fancyParcelableExtraParcelable_3392903 = IntentHelper.getExtraParcelable(intent, \"fancyParcelable\", null);",
                "t.extra(fancyParcelableExtraParcelable_3392903);",
                "final Bundle fancyBundleExtraBundle_3392903 = IntentHelper.getExtraBundle(intent, \"fancyBundle\", null);",
                "t.extra(fancyBundleExtraBundle_3392903);",
                "final CharSequence fancyCharSequenceExtraCharSequence_3392903 = IntentHelper.getExtraCharSequence(intent, \"fancyCharSequence\", null);",
                "t.extra(fancyCharSequenceExtraCharSequence_3392903);",
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
