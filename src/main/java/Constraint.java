package main.java;

public abstract class Constraint {

    public abstract String toString();

    protected abstract boolean isSatisfied();

    protected abstract boolean reduce();

    public abstract boolean involves(Variable variable);

}
