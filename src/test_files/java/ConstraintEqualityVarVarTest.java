package test_files.java;

import main.java.ConstraintEqualityVarVar;
import main.java.Domain;
import main.java.Variable;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConstraintEqualityVarVarTest {

    private Variable variable1;
    private Variable variable2;

    @Before
    public void setUp() {
        Domain domain1 = new Domain(new int[] {1, 2, 3, 4, 5});
        Domain domain2 = new Domain(new int[] {3, 4, 5, 6, 7});
        variable1 = new Variable("X", domain1);
        variable2 = new Variable("Y", domain2);
    }

    @Test
    public void testConstructor() {
        ConstraintEqualityVarVar constraint = new ConstraintEqualityVarVar(variable1, variable2);
        assertEquals("X= {1, 2, 3, 4, 5} = Y= {3, 4, 5, 6, 7}", constraint.toString());
    }

    @Test
    public void testIsSatisfied() {
        ConstraintEqualityVarVar constraint = new ConstraintEqualityVarVar(variable1, variable2);
        assertFalse(constraint.isSatisfied());

        Domain domain = new Domain(new int[] {1, 2, 3});
        Variable var1 = new Variable("A", domain);
        Variable var2 = new Variable("B", domain);
        ConstraintEqualityVarVar satisfiedConstraint = new ConstraintEqualityVarVar(var1, var2);
        assertTrue(satisfiedConstraint.isSatisfied());
    }

    @Test
    public void testReduce() {
        ConstraintEqualityVarVar constraint = new ConstraintEqualityVarVar(variable1, variable2);
        assertTrue(constraint.reduce());
        assertEquals("{3, 4, 5}", variable1.getDomain().toString());
        assertEquals("{3, 4, 5}", variable2.getDomain().toString());
    }

    @Test
    public void testInvolves() {
        ConstraintEqualityVarVar constraint = new ConstraintEqualityVarVar(variable1, variable2);
        assertTrue(constraint.involves(variable1));
        assertTrue(constraint.involves(variable2));

        Domain domain = new Domain(new int[] {1, 2, 3});
        Variable otherVariable = new Variable("Z", domain);
        assertFalse(constraint.involves(otherVariable));
    }
}
