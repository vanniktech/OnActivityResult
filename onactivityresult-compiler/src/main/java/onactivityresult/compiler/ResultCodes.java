package onactivityresult.compiler;

import java.util.Arrays;

class ResultCodes implements Comparable<ResultCodes> {
    private final int[] resultCodes;

    ResultCodes(final int... resultCodes) {
        this.resultCodes = Arrays.copyOf(resultCodes, resultCodes.length);

        Arrays.sort(resultCodes);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ResultCodes that = (ResultCodes) o;
        return Arrays.equals(resultCodes, that.resultCodes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(resultCodes);
    }

    @Override
    public int compareTo(final ResultCodes o) {
        if (o.resultCodes.length == 0 && this.resultCodes.length == 0) {
            return 0;
        }

        if (o.resultCodes.length == 0) {
            return -1;
        }

        if (this.resultCodes.length == 0) {
            return 1;
        }

        final boolean sameSize = o.resultCodes.length == this.resultCodes.length;

        if (sameSize) {
            for (int i = 0; i < this.resultCodes.length; i++) {
                final int oResultCode = o.resultCodes[i];
                final int resultCode = this.resultCodes[i];

                if (oResultCode != resultCode) {
                    return oResultCode > resultCode ? -1 : 1;
                }
            }

            return 0;
        }

        if (o.resultCodes.length > this.resultCodes.length) {
            return -1;
        }

        return 1;
    }

    int size() {
        return resultCodes.length;
    }

    String getIfExpression() {
        final StringBuilder sb = new StringBuilder();

        if (resultCodes.length > 0) {
            final String equals = " == ";
            final String or = " || ";

            for (final int resultCode : resultCodes) {
                sb.append(Parameter.RESULT_CODE).append(equals).append(resultCode).append(or);
            }

            sb.setLength(sb.length() - or.length());
        }

        return sb.toString();
    }
}
