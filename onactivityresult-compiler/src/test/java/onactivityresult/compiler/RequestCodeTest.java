package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

@SuppressWarnings("checkstyle:magicnumber")
public class RequestCodeTest {
    @Test
    @SuppressWarnings({ "PMD.EqualsNull", "SelfEquals", "EqualsIncompatibleType" })
    public void equality() {
        final RequestCode same = new RequestCode(4);
        //noinspection EqualsWithItself
        assertEquals(same, same);

        assertEquals(new RequestCode(4), new RequestCode(4));
        assertNotEquals(new RequestCode(3), new RequestCode(5));

        //noinspection EqualsBetweenInconvertibleTypes
        assertNotEquals(new RequestCode(5), 5);

        //noinspection ObjectEqualsNull
        assertNotEquals(new RequestCode(5), null);
    }

    @Test
    public void hashCodes() {
        assertEquals(new RequestCode(3).hashCode(), new RequestCode(3).hashCode());
        assertNotEquals(new RequestCode(5).hashCode(), new RequestCode(3).hashCode());
    }
}
