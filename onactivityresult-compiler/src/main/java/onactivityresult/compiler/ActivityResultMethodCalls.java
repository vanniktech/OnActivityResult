package onactivityresult.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ActivityResultMethodCalls {
    private final Map<RequestCode, List<MethodCall>> calls = new HashMap<>();

    void add(final MethodCall method, final RequestCode requestCode) {
        final List<MethodCall> cachedMethods = calls.get(requestCode);

        if (cachedMethods != null) {
            cachedMethods.add(method);
        } else {
            final List<MethodCall> methods = new ArrayList<>(2);
            methods.add(method);

            calls.put(requestCode, methods);
        }
    }

    RequestCode[] getRequestCodes() {
        return calls.keySet().toArray(new RequestCode[calls.size()]);
    }

    List<MethodCall> getMethodCallsForRequestCode(final RequestCode requestCode) {
        return calls.get(requestCode);
    }
}
