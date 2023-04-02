package main.java;

import java.util.List;
import java.util.ArrayList;

public class ConstraintEqualityVarPlusCons extends Constraint {
    
    Variable v1, v2;
    int cons;
    Boolean abs;

    /**
     * Constructor
     * 
     * @param v1 the first variable
     * @param v2 the second variable
     * @param cons the constant
     * @param abs whether the constraint is absolute or not
     */
    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, int cons, Boolean abs){
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
        if(!abs)    
            result += this.v1 + " = " + this.v2 + " + " + this.cons;
        else
            result += this.v1 + " = " + this.v2 + " + " + this.cons + " or " + this.v1 + " = " + this.v2 + " - " + this.cons;
        return result;
    }


    /**
     * Checks if the domain values +- constant are in the other domain
     * 
     * @param v1 the first variable
     * @param v2 the second variable
     * @return true if the domain values +- constant are in the other domain, false otherwise
     */
    private boolean domainCheck(int v1, int v2) {
        if(!this.abs) {
            if(v1 == v2 + this.cons)
                return true;
        } else {
            if(v1 == v2 + this.cons || v1 == v2 - this.cons)
                return true;
        }
        return false;
    }



    /**
     * Checks if the constraint is satisfied
     * 
     * @return true if the constraint is satisfied, false otherwise
     */
    protected boolean isSatisfied() {

        Boolean flag = true;

        for(int i = 0; i < v1.d.vals.size(); i++) {

            flag = false;

            for(int j = 0; j < v2.d.vals.size(); j++) {
                if(domainCheck(v1.d.vals.get(i), v2.d.vals.get(j))){
                    flag = true;
                }
            }

            if(flag)
                break;
        }

        return flag && !this.v1.d.isEmpty() && !this.v2.d.isEmpty();

    }


    /**
     * Reduces the domains by checking if the domain values +- constant are in the other domain
     * 
     * @return true if the domains are not empty, false otherwise
     */
    protected boolean reduce() {

        boolean changed = false;

        List<Integer> d1_vals = new ArrayList<Integer>(this.v1.d.vals);
        List<Integer> d2_vals = new ArrayList<Integer>(this.v2.d.vals);

        for(int i = 0; i < d1_vals.size(); i++) {

            Boolean flag = false;

            for(int j = 0; j < d2_vals.size(); j++) {
                if(domainCheck(d1_vals.get(i), d2_vals.get(j)))
                    flag = true;
            }

            if(!flag) {
                v1.d.delete(d1_vals.get(i));
                changed = true;
            }
        }

        for(int i = 0; i < d2_vals.size(); i++) {

            Boolean flag = false;

            for(int j = 0; j < d1_vals.size(); j++) {
                if(domainCheck(d1_vals.get(j), d2_vals.get(i)))
                    flag = true;
            }

            if(!flag) {
                v2.d.delete(d2_vals.get(i));
                changed = true;
            }
        }


        if(v1.d.vals.size() == 0 || v2.d.vals.size() == 0 || !changed)
            return false;

        return true;


    }


    public boolean involves(Variable variable) {
        return (this.v1 == variable || this.v2 == variable);
    }
}