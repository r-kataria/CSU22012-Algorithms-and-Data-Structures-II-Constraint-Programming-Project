package test_files.java;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import main.java.Domain;

public class DomainTest {

    private Domain domain;

    @Before
    public void setUp() {
        int[] values = {1, 2, 3, 4, 5};
        domain = new Domain(values);
    }

    @Test
    public void testConstructorFromArray() {
        assertEquals("{1, 2, 3, 4, 5}", domain.toString());
    }

    @Test
    public void testCopyConstructor() {
        Domain copyDomain = new Domain(domain);
        assertEquals("{1, 2, 3, 4, 5}", copyDomain.toString());
    }

    @Test
    public void testToString() {
        assertEquals("{1, 2, 3, 4, 5}", domain.toString());
    }

    @Test
    public void testSplit() {
        List<Domain> splitDomains = domain.split();
        assertEquals("{1, 2}", splitDomains.get(0).toString());
        assertEquals("{3, 4, 5}", splitDomains.get(1).toString());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(domain.isEmpty());
        Domain emptyDomain = new Domain(new int[] {});
        assertTrue(emptyDomain.isEmpty());
    }

    @Test
    public void testEquals() {
        Domain sameDomain = new Domain(new int[] {1, 2, 3, 4, 5});
        Domain differentDomain = new Domain(new int[] {1, 2, 3, 4, 6});

        assertTrue(domain.equals(sameDomain));
        assertFalse(domain.equals(differentDomain));
    }

    @Test
    public void testIsReducedToOnlyOneValue() {
        assertFalse(domain.isReducedToOnlyOneValue());
        Domain singleValueDomain = new Domain(new int[] {1});
        assertTrue(singleValueDomain.isReducedToOnlyOneValue());
    }

    @Test
    public void testDelete() {
        domain.delete(3);
        assertEquals("{1, 2, 4, 5}", domain.toString());
        domain.delete(6); // Non-existent value should not affect the domain
        assertEquals("{1, 2, 4, 5}", domain.toString());
    }

    @Test
    public void testIntersection() {
        Domain otherDomain = new Domain(new int[] {3, 4, 5, 6, 7});
        Set<Integer> intersectionList = domain.intersection(otherDomain);

        Set<Integer> expectedIntersection = new HashSet<Integer>();
        expectedIntersection.add(3);
        expectedIntersection.add(4);
        expectedIntersection.add(5);

        assertEquals(expectedIntersection, intersectionList);
    }
}
