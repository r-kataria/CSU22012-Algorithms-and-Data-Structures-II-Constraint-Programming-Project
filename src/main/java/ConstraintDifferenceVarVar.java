package main.java;

public class ConstraintDifferenceVarVar extends Constraint {

    Variable v1, v2;

    /**
     * Constructor
     * 
     * @param v1 the first variable
     * @param v2 the second variable
     */
    public ConstraintDifferenceVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    /**
     * Returns a string representation of the constraint
     * 
     * @return a string representation of the constraint
     */
    public String toString() {
        String result = "";
        result += this.v1 + " != " + this.v2;
        return result;
    }

    /**
     * Checks if the constraint is satisfied
     * 
     * @return true if the constraint is satisfied, false otherwise
     */
    public boolean isSatisfied() {
        if (v1.d.isReducedToOnlyOneValue()) {

            return !v2.d.contains(v1.d.getFirstValue());
       
        } else if (v2.d.isReducedToOnlyOneValue()) {
            
            return !v1.d.contains(v2.d.getFirstValue());
        
        } else {
        
            return true;
       
        }
    }

/**
 * Reduces the domains of the variables by removing the values that are certain
 * to be in the other domain
 *
 * @return true if the domains are not empty, false otherwise
 */
public boolean reduce() {
    boolean changed = false;

    if (v1.d.isReducedToOnlyOneValue()) {
        
        Integer val1 = v1.d.getFirstValue();
        
        if (v2.d.contains(val1)) {
            v2.d.delete(val1);
            changed = true;
        }
    }

    if (v2.d.isReducedToOnlyOneValue()) {
        
        Integer val2 = v2.d.getFirstValue();

        if (v1.d.contains(val2)) {
            v1.d.delete(val2);
            changed = true;
        }
    }

    return !v1.d.isEmpty() && !v2.d.isEmpty() && changed;
}

    /**
     * Returns true if the constraint involves the given variable
     * 
     * @param variable the variable
     * @return true if the constraint involves the given variable, false otherwise
     */
    public boolean involves(Variable variable) {
        return (this.v1 == variable || this.v2 == variable);
    }

}
