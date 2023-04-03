package main.java;

import java.util.HashSet;

public class ConstraintEqualityVarCons extends Constraint {

    Variable v;
    int cons;

    /**
     * Constructor
     * 
     * @param v    the variable
     * @param cons the constant
     */
    public ConstraintEqualityVarCons(Variable v, int cons) {
        this.v = v;
        this.cons = cons;
    }

    /**
     * Returns a string representation of the constraint
     * 
     * @return a string representation of the constraint
     */
    public String toString() {
        String result = "";
        result += this.v;
        return result;
    }

    /**
     * Checks if the constraint is satisfied
     * 
     * @return true if the constraint is satisfied, false otherwise
     */
    public boolean isSatisfied() {

        // If the domain has more than one value, the constraint is not satisfied
        if (!this.v.d.isReducedToOnlyOneValue()) {
            return false;
        }

        // If the domain has only one value, but it is not the constant, the constraint
        // is not satisfied
        if (!this.v.d.contains(this.cons)) {
            return false;
        }

        return true;
    }

    /**
     * Reduces the domain of the variable to the constant
     * 
     * @return true if the domain is not empty, false otherwise
     */
    public boolean reduce() {

        // if already 1, return 0
        if (this.v.d.isReducedToOnlyOneValue()) {
            return false;
        }

        // Set the domain to the constant
        HashSet<Integer> set = new HashSet<Integer>();     
        set.add(this.cons);

        this.v.d.setVals(set);
        

        // If the domain is empty, return false
        if (this.v.d.size() == 0) {
            return false;
        }

        return this.v.d.isReducedToOnlyOneValue();

    }

    /**
     * Returns true if the constraint involves the given variable
     * 
     * @param variable the variable
     * @return true if the constraint involves the given variable, false otherwise
     */
    public boolean involves(Variable variable) {
        return this.v.equals(variable);
    }

}