package main.java;

public abstract class Constraint {

    /**
     * Returns a string representation of the constraint.
     */
    public abstract String toString();

    /**
     * Returns true if the constraint is satisfied.
     */
    public abstract boolean isSatisfied();

    /**
     * Returns true if the constraint is satisfied.
     */
    public abstract boolean reduce();

    /**
     * Returns true if the constraint involves the given variable.
     */
    public abstract boolean involves(Variable variable);

}
