package main.java;

import java.util.List;
import java.util.ArrayList;


public class ConstraintEqualityVarVar extends Constraint {
    
    Variable v1, v2;

    /**
     * Constructor
     * 
     * @param v1 the first variable
     * @param v2 the second variable
     */
    public ConstraintEqualityVarVar(Variable v1, Variable v2) {
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
            result += this.v1 + " = " + this.v2;
        return result;
    }

    /**
     * Checks if the constraint is satisfied
     * 
     * @return true if the constraint is satisfied, false otherwise
     */
    protected boolean isSatisfied() {
        // If the domains are not equal, the constraint is not satisfied
        return this.v1.d.equals(v2.d) && !this.v1.d.isEmpty() && !this.v2.d.isEmpty();
    }

    /**
     * Reduces the domains of the variables to the intersection of the two domains
     * 
     * @return true if the domains are not empty, false otherwise
     */
    protected boolean reduce() {
        // Take the intersection of the two domains
        List<Integer> intersectionList = new ArrayList<>();
        intersectionList = this.v1.d.intersection(this.v2.d);

        // Set the domains of the variables to the intersection
        this.v1.d.vals = intersectionList;
        this.v2.d.vals = intersectionList;

        // Return true if the domains are not empty
        return !this.v1.d.vals.isEmpty() && !this.v2.d.vals.isEmpty();
    }

    public boolean involves(Variable variable) {
        return this.v1.equals(variable) || this.v2.equals(variable);
    }

}
