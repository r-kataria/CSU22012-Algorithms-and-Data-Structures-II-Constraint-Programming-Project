package test_files.java;


import main.java.ConstraintEqualityVarCons;
import main.java.Domain;
import main.java.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import java.util.HashSet;
import java.util.Set;


public class ConstraintEqualityVarConsTest {

    private Variable variable;
    private Domain domain;
    private ConstraintEqualityVarCons constraint;

    @Before
    public void setUp() {
        int[] values = {1, 2, 3};
        domain = new Domain(values);
        variable = new Variable("x", domain);
        constraint = new ConstraintEqualityVarCons(variable, 2);
    }

    @Test
    public void testToString() {
        constraint.reduce();
        assertEquals("x= {2}", constraint.toString());
    }

    @Test
    public void testIsSatisfied() {
        assertFalse(constraint.isSatisfied());

        variable.getDomain().setVals(new HashSet<>(Set.of(2)));
        assertTrue(constraint.isSatisfied());

        variable.getDomain().setVals(new HashSet<>(Set.of(1)));
        assertFalse(constraint.isSatisfied());
    }

    @Test
    public void testReduce() {
        assertTrue(constraint.reduce());
        assertEquals(1, variable.getDomain().size());
        assertTrue(variable.getDomain().contains(2));
        assertTrue(constraint.isSatisfied());

        constraint = new ConstraintEqualityVarCons(variable, 4);
        assertFalse(constraint.reduce());
    }

    @Test
    public void testInvolves() {
        Variable otherVariable = new Variable("y", domain);
        assertTrue(constraint.involves(variable));
        assertFalse(constraint.involves(otherVariable));
    }
}

