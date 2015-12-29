package onactivityresult.compiler;

final class Parameter {
    static final String RESULT_CODE = "resultCode";
    static final String INTENT      = "intent";

    static Parameter createResultCode() {
        return new Parameter(RESULT_CODE);
    }

    static Parameter createIntent() {
        return new Parameter(INTENT);
    }

    private final String name;

    private Parameter(final String name) {
        this.name = name;
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
}
