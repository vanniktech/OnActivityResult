package onactivityresult.compiler;

final class Parameter {
    static final String RESULT_CODE = "resultCode";
    static final String INTENT      = "intent";
    static final String INTENT_DATA = "intentData";

    static Parameter createResultCode() {
        return new Parameter(RESULT_CODE, false);
    }

    static Parameter createIntent() {
        return new Parameter(INTENT, false);
    }

    static Parameter createIntentData() {
        return new Parameter(INTENT_DATA, true);
    }

    private final String name;

    private final boolean isIntentData;

    private Parameter(final String name, final boolean isIntentData) {
        this.name = name;
        this.isIntentData = isIntentData;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Parameter parameter = (Parameter) o;

        return !(name != null ? !name.equals(parameter.name) : parameter.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    boolean isIntentData() {
        return isIntentData;
    }
}
