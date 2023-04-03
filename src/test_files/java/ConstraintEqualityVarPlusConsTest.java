package test_files.java;

import main.java.ConstraintEqualityVarPlusCons;
import main.java.Domain;
import main.java.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstraintEqualityVarPlusConsTest {

    Domain d1, d2;
    Variable v1, v2;
    ConstraintEqualityVarPlusCons constraint, constraintAbs;

    @Before
    public void setUp() {
        int[] values1 = {1, 2, 3, 4, 5};
        int[] values2 = {4, 5, 6, 7, 8};
        d1 = new Domain(values1);
        d2 = new Domain(values2);
        v1 = new Variable("X", d1);
        v2 = new Variable("Y", d2);
        constraint = new ConstraintEqualityVarPlusCons(v1, v2, 1, false);
        constraintAbs = new ConstraintEqualityVarPlusCons(v1, v2, 2, true);
    }

    @Test
    public void testToString() {
        assertEquals("X= {1, 2, 3, 4, 5} = Y= {4, 5, 6, 7, 8} + 1", constraint.toString());
        assertEquals("X= {1, 2, 3, 4, 5} = Y= {4, 5, 6, 7, 8} + 2 or X= {1, 2, 3, 4, 5} = Y= {4, 5, 6, 7, 8} - 2", constraintAbs.toString());
    }

    @Test
    public void testIsSatisfied() {
        assertTrue(constraint.isSatisfied());
        assertTrue(constraintAbs.isSatisfied());

        v1.setDomain(new Domain(new int[]{2}));
        v2.setDomain(new Domain(new int[]{3}));
        assertFalse(constraint.isSatisfied());
        assertFalse(constraintAbs.isSatisfied());
    }

    @Test
    public void testReduce() {
        assertTrue(constraint.reduce());

        v1.setDomain(d1);
        v2.setDomain(d2);

        assertTrue(constraintAbs.reduce());
        
        v1.setDomain(new Domain(new int[]{1, 2, 3}));
        v2.setDomain(new Domain(new int[]{8, 9, 10}));
        assertFalse(constraint.reduce());
        assertFalse(constraintAbs.reduce());
    }

    @Test
    public void testInvolves() {
        assertTrue(constraint.involves(v1));
        assertTrue(constraint.involves(v2));
        assertFalse(constraint.involves(new Variable("Z", new Domain(new int[]{1, 2, 3}))));
    }
}
