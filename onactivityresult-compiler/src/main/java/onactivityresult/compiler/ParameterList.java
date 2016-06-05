package onactivityresult.compiler;

import com.squareup.javapoet.NameAllocator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class ParameterList implements Iterable<Parameter> {

    private final Collection<Parameter> list;

    ParameterList() {
        list = new ArrayList<>();
    }

    void add(final Parameter parameter) {
        if (parameter != null) {
            list.add(parameter);
        }
    }

    int size() {
        return list.size();
    }

    boolean hasNumberOfDifferentParameters(final int number) {
        final Set<Parameter> set = new HashSet<>(list);
        return set.size() == number;
    }

    public String toString(final NameAllocator nameAllocator) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (list.size() > 0) {
            for (final Parameter parameter : list) {
                stringBuilder.append(nameAllocator.get(parameter.hashCode())).append(", ");
            }

            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

    @Override
    public Iterator<Parameter> iterator() {
        return list.iterator();
    }
}
