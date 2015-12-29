package onactivityresult.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class ParameterList implements Iterable<Parameter> {
    private final Collection<Parameter> mList;

    ParameterList(final int capacity) {
        mList = new ArrayList<>(capacity);
    }

    void add(final Parameter parameter) {
        if (parameter != null) {
            mList.add(parameter);
        }
    }

    @Override
    public Iterator<Parameter> iterator() {
        return mList.iterator();
    }

    int size() {
        return mList.size();
    }

    boolean hasNumberOfDifferentParameters(final int number) {
        final Set<Parameter> set = new HashSet<>(mList);
        return set.size() == number;
    }
}
