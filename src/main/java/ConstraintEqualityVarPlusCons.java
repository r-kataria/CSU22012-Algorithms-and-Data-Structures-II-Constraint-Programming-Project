package main.java;

import java.util.Set;
import java.util.HashSet;

public class ConstraintEqualityVarPlusCons extends Constraint {

    Variable v1, v2;
    int cons;
    Boolean abs;

    /**
     * Constructor
     * 
     * @param v1   the first variable
     * @param v2   the second variable
     * @param cons the constant
     * @param abs  whether the constraint is absolute or not
     */
    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, int cons, Boolean abs) {
        this.v1 = v1;
        this.v2 = v2;
        this.cons = cons;
        this.abs = abs;
    }

    /**
     * Returns a string representation of the constraint
     * 
     * @return a string representation of the constraint
     */
    public String toString() {
        String result = "";
        if (!abs)
            result += this.v1 + " = " + this.v2 + " + " + this.cons;
        else
            result += this.v1 + " = " + this.v2 + " + " + this.cons + " or " + this.v1 + " = " + this.v2 + " - "
                    + this.cons;
        return result;
    }

    /**
     * Checks if the domain values +- constant are in the other domain
     * 
     * @param v1 the first variable
     * @param v2 the second variable
     * @return true if the domain values +- constant are in the other domain, false
     *         otherwise
     */
    private boolean domainCheck(int v1, int v2) {
        if (!this.abs) {
            if (v1 == v2 + this.cons)
                return true;
        } else {
            if (v1 == v2 + this.cons || v1 == v2 - this.cons)
                return true;
        }
        return false;
    }

    /**
     * Checks if the constraint is satisfied
     * 
     * @return true if the constraint is satisfied, false otherwise
     */
    public boolean isSatisfied() {

        Boolean flag = true;


        for (Integer value1 : this.v1.d.getVals()) {
            flag = false;

            for (Integer value2 : this.v2.d.getVals()) {
                if (domainCheck(value1, value2)) {
                    flag = true;
                }
            }
            
            if (flag)
                break;
        }


        return flag && !this.v1.d.isEmpty() && !this.v2.d.isEmpty();

    }

/**
 * Reduces the domains by checking if the domain values +- constant are in the
 * other domain
 *
 * @return true if the domains are not empty, false otherwise
 */
/**
 * Reduces the domains by checking if the domain values +- constant are in the
 * other domain
 *
 * @return true if the domains are not empty, false otherwise
 */
public boolean reduce() {
    boolean changed = false;

    Set<Integer> d1_vals = new HashSet<>(this.v1.d.getVals());
    Set<Integer> d2_vals = new HashSet<>(this.v2.d.getVals());
    Set<Integer> d1_valsToRemove = new HashSet<>();
    Set<Integer> d2_valsToRemove = new HashSet<>();

    for (Integer val1 : d1_vals) {
        boolean flag = false;

        for (Integer val2 : d2_vals) {
            if (domainCheck(val1, val2))
                flag = true;
        }

        if (!flag) {
            d1_valsToRemove.add(val1);
            changed = true;
        }
    }

    for (Integer val2 : d2_vals) {
        boolean flag = false;

        for (Integer val1 : d1_vals) {
            if (domainCheck(val1, val2))
                flag = true;
        }

        if (!flag) {
            d2_valsToRemove.add(val2);
            changed = true;
        }
    }

    // Remove values from the domains after iteration is complete
    for (Integer val : d1_valsToRemove) {
        v1.d.delete(val);
    }
    for (Integer val : d2_valsToRemove) {
        v2.d.delete(val);
    }

    if (v1.d.isEmpty() || v2.d.isEmpty() || !changed)
        return false;

    return true;
}

 
    /**
     * Returns true if the constraint involves the given variable.
     * 
     * @param variable the variable
     * @return true if the constraint involves the given variable, false otherwise
     */
    public boolean involves(Variable variable) {
        return (this.v1 == variable || this.v2 == variable);
    }
}