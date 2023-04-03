package main.java;

import java.util.Set;
import java.util.HashSet;

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
    public boolean isSatisfied() {
        // If the domains are not equal, the constraint is not satisfied
        return this.v1.d.equals(v2.d) && !this.v1.d.isEmpty() && !this.v2.d.isEmpty();
    }

    /**
     * Reduces the domains of the variables to the intersection of the two domains
     * 
     * @return true if the domains are not empty, false otherwise
     */
    public boolean reduce() {
        // Take the intersection of the two domains
        Set<Integer> intersection = new HashSet<>();
        intersection = this.v1.d.intersection(this.v2.d);

        // Set the domains of the variables to the intersection
        this.v1.d.setVals(intersection);
        this.v2.d.setVals(intersection);

        // Return true if the domains are not empty
        return (!this.v1.d.isEmpty() && !this.v2.d.isEmpty()) && intersection.size() > 0;

    }

    /**
     * Returns true if the constraint involves the given variable.
     * 
     * @param variable the variable
     * @return true if the constraint involves the given variable, false otherwise
     */
    public boolean involves(Variable variable) {
        return this.v1.equals(variable) || this.v2.equals(variable);
    }

}
