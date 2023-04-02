package main.java;

import java.util.ArrayList;
import java.util.List;

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
    protected boolean isSatisfied() {
        if(v1.d.isReducedToOnlyOneValue()) {
            int val = v1.d.vals.get(0);
            return !v2.d.vals.contains(val);
        } else if(v2.d.isReducedToOnlyOneValue()) {
            int val = v2.d.vals.get(0);
            return !v1.d.vals.contains(val);
        } else {
            return true;
        }
    }

    /**
     * Reduces the domains of the variables by removing the values that are certain to be in the other domain
     * 
     * @return true if the domains are not empty, false otherwise
     */
    protected boolean reduce() {
 
        boolean changed = false;

        if(v1.d.isReducedToOnlyOneValue()) {
            int val = v1.d.vals.get(0); 
            if(v2.d.vals.contains(val)) {
                v2.d.delete(val);
                changed = true;
            }
           }

        if(v2.d.isReducedToOnlyOneValue()) {
           if(v1.d.vals.contains(v2.d.vals.get(0))) {
               v1.d.delete(v2.d.vals.get(0));
               changed = true;
           }
        }

        return !this.v1.d.vals.isEmpty() && !this.v2.d.vals.isEmpty() && changed;
        
    }

    public boolean involves(Variable variable) {
        return (this.v1 == variable || this.v2 == variable);
    }

}
