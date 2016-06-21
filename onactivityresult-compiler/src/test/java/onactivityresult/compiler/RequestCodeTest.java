package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

@SuppressWarnings("checkstyle:magicnumber")
public class RequestCodeTest {
    @Test
    @SuppressWarnings("PMD.EqualsNull")
    public void equality() {
        final RequestCode same = new RequestCode(4);
        //noinspection EqualsWithItself
        assertEquals(true, same.equals(same));

        assertEquals(true, new RequestCode(4).equals(new RequestCode(4)));
        assertEquals(false, new RequestCode(3).equals(new RequestCode(5)));

        //noinspection EqualsBetweenInconvertibleTypes
        assertEquals(false, new RequestCode(5).equals(5));

        //noinspection ObjectEqualsNull
        assertEquals(false, new RequestCode(5).equals(null));
    }

    @Test
    public void hashCodes() {
        assertEquals(new RequestCode(3).hashCode(), new RequestCode(3).hashCode());
        assertNotEquals(new RequestCode(5).hashCode(), new RequestCode(3).hashCode());
    }
}
