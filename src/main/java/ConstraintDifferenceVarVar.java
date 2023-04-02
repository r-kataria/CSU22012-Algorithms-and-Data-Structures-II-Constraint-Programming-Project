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
        // Take the intersection of the two domains
        List<Integer> intersectionList = new ArrayList<>();
        intersectionList = this.v1.d.intersection(this.v2.d);

        // Check if the intersection is empty
        if(intersectionList.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Reduces the domains of the variables by removing the values that are certain to be in the other domain
     * 
     * @return true if the domains are not empty, false otherwise
     */
    protected boolean reduce() {
 
        if(v1.d.isReducedToOnlyOneValue()) {
            int val = v1.d.vals.get(0);
            v2.d.delete(val);
        }

        if(v2.d.isReducedToOnlyOneValue()) {
            int val = v2.d.vals.get(0);
            v1.d.delete(val);
        }

        return !this.v1.d.vals.isEmpty() && !this.v2.d.vals.isEmpty();
        
    }

    public boolean involves(Variable variable) {
        return (this.v1 == variable || this.v2 == variable);
    }

}
