package onactivityresult.compiler;

final class RequestCode {
    public final int requestCode;

    RequestCode(final int requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RequestCode that = (RequestCode) o;
        return requestCode == that.requestCode;
    }

    @Override
    public int hashCode() {
        return requestCode;
    }
}
