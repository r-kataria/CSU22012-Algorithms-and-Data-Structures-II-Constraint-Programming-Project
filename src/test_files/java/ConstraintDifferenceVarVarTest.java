package test_files.java;

import main.java.ConstraintDifferenceVarVar;
import main.java.Domain;
import main.java.Variable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class ConstraintDifferenceVarVarTest {

    private Variable variable1;
    private Variable variable2;
    private Domain domain;
    private ConstraintDifferenceVarVar constraint;

    @Before
    public void setUp() {
        int[] values = {1, 2, 3};
        domain = new Domain(values);
        variable1 = new Variable("x", domain);
        variable2 = new Variable("y", domain);
        constraint = new ConstraintDifferenceVarVar(variable1, variable2);
    }

    @Test
    public void testToString() {
        assertEquals("x= {1, 2, 3} != y= {1, 2, 3}", constraint.toString());
    }

    @Test
    public void testIsSatisfied() {
        assertTrue(constraint.isSatisfied());

        variable1.getDomain().setVals(new HashSet<>(Set.of(1)));
        variable2.getDomain().setVals(new HashSet<>(Set.of(2)));
        assertTrue(constraint.isSatisfied());

        variable1.getDomain().setVals(new HashSet<>(Set.of(2)));
        assertFalse(constraint.isSatisfied());
    }

    @Test
    public void testReduce() {
        variable1.getDomain().setVals(new HashSet<>(Set.of(1)));
        assertTrue(constraint.reduce());
        assertEquals(2, variable2.getDomain().size());
        assertFalse(variable2.getDomain().contains(1));

        variable1.getDomain().setVals(new HashSet<>(Set.of(3)));
        assertTrue(constraint.reduce());
        assertEquals(1, variable2.getDomain().size());
        assertFalse(variable2.getDomain().contains(3));

        variable2.getDomain().setVals(new HashSet<>());
        assertFalse(constraint.reduce());
    }

    @Test
    public void testInvolves() {
        Variable otherVariable = new Variable("z", domain);
        assertTrue(constraint.involves(variable1));
        assertTrue(constraint.involves(variable2));
        assertFalse(constraint.involves(otherVariable));
    }
}
