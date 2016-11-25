package onactivityresult.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

@SuppressWarnings("checkstyle:magicnumber")
public class ResultCodesTest {
    @Test
    public void comparable() {
        assertEquals(0, new ResultCodes().compareTo(new ResultCodes()));
        assertEquals(1, new ResultCodes().compareTo(new ResultCodes(1)));
        assertEquals(-1, new ResultCodes(1).compareTo(new ResultCodes()));

        assertEquals(-1, new ResultCodes(0).compareTo(new ResultCodes(1)));
        assertEquals(0, new ResultCodes(0).compareTo(new ResultCodes(0)));
        assertEquals(1, new ResultCodes(1).compareTo(new ResultCodes(0)));

        assertEquals(-1, new ResultCodes(-1).compareTo(new ResultCodes(0)));
        assertEquals(0, new ResultCodes(-1).compareTo(new ResultCodes(-1)));
        assertEquals(1, new ResultCodes(1).compareTo(new ResultCodes(-1)));

        assertEquals(0, new ResultCodes(-1, 0).compareTo(new ResultCodes(-1, 0)));
        assertEquals(-1, new ResultCodes(0).compareTo(new ResultCodes(-1, 0)));
        assertEquals(1, new ResultCodes(-1, 1).compareTo(new ResultCodes(1)));
    }

    @Test
    @SuppressWarnings({ "PMD.EqualsNull", "SelfEquals", "EqualsIncompatibleType" })
    public void equality() {
        final ResultCodes same = new ResultCodes(-1);
        //noinspection EqualsWithItself
        assertEquals(true, same.equals(same));

        assertEquals(true, new ResultCodes(-1).equals(new ResultCodes(-1)));
        assertEquals(true, new ResultCodes(0).equals(new ResultCodes(0)));
        assertEquals(true, new ResultCodes(1).equals(new ResultCodes(1)));
        assertEquals(true, new ResultCodes(-1, 0).equals(new ResultCodes(-1, 0)));

        assertEquals(false, new ResultCodes().equals(new ResultCodes(0)));
        assertEquals(false, new ResultCodes(1, 2).equals(new ResultCodes(0)));
        assertEquals(false, new ResultCodes(-1).equals(new ResultCodes(0)));
        assertEquals(false, new ResultCodes(0).equals(new ResultCodes(1)));

        //noinspection ObjectEqualsNull
        assertEquals(false, new ResultCodes(0).equals(null));

        //noinspection EqualsBetweenInconvertibleTypes
        assertEquals(false, new ResultCodes(0).equals("0"));
    }

    @Test
    public void hashCodes() {
        assertEquals(new ResultCodes(-1).hashCode(), new ResultCodes(-1).hashCode());
        assertEquals(new ResultCodes(0).hashCode(), new ResultCodes(0).hashCode());
        assertEquals(new ResultCodes(1).hashCode(), new ResultCodes(1).hashCode());
        assertEquals(new ResultCodes(-1, 0).hashCode(), new ResultCodes(-1, 0).hashCode());

        assertNotEquals(new ResultCodes().hashCode(), new ResultCodes(0).hashCode());
        assertNotEquals(new ResultCodes(1, 2).hashCode(), new ResultCodes(0).hashCode());
        assertNotEquals(new ResultCodes(-1).hashCode(), new ResultCodes(0).hashCode());
        assertNotEquals(new ResultCodes(0).hashCode(), new ResultCodes(1).hashCode());
    }
}
