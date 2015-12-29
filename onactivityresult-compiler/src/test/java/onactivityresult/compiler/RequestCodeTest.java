package onactivityresult.compiler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings({ "checkstyle:magicnumber", "PMD.AvoidDuplicateLiterals" })
public class RequestCodeTest {
    @SuppressWarnings({ "ObjectEqualsNull", "EqualsBetweenInconvertibleTypes" })
    @Test
    public void testEquals() {
        assertTrue(new RequestCode(4).equals(new RequestCode(4)));
        assertFalse(new RequestCode(3).equals(new RequestCode(5)));
        assertFalse(new RequestCode(5).equals(5));
    }

    @Test
    public void testHashCode() {
        assertEquals(new RequestCode(3).hashCode(), new RequestCode(3).hashCode());
        assertNotEquals(new RequestCode(5).hashCode(), new RequestCode(3).hashCode());
    }
}
