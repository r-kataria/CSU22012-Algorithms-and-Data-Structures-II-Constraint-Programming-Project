package test_files.java;

import main.java.Domain;
import main.java.Variable;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class VariableTest {

    private Variable variable;

    @Before
    public void setUp() {
        Domain domain = new Domain(new int[] {1, 2, 3, 4, 5});
        variable = new Variable("X", domain);
    }

    @Test
    public void testConstructor() {
        assertEquals("X= {1, 2, 3, 4, 5}", variable.toString());
    }

    @Test
    public void testCopyConstructor() {
        Variable copyVariable = new Variable(variable);
        assertEquals("X= {1, 2, 3, 4, 5}", copyVariable.toString());
    }

    @Test
    public void testToString() {
        assertEquals("X= {1, 2, 3, 4, 5}", variable.toString());
    }

    @Test
    public void testHasThisName() {
        assertTrue(variable.hasThisName("X"));
        assertFalse(variable.hasThisName("Y"));
    }

    @Test
    public void testGetDomain() {
        Domain expectedDomain = new Domain(new int[] {1, 2, 3, 4, 5});
        assertTrue(expectedDomain.equals(variable.getDomain()));
    }

    @Test
    public void testSetDomain() {
        Domain newDomain = new Domain(new int[] {1, 2, 3});
        variable.setDomain(newDomain);
        assertTrue(newDomain.equals(variable.getDomain()));
        assertEquals("X= {1, 2, 3}", variable.toString());
    }
}
