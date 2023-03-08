package main.java;

public class ConstraintEqualityVarVar extends Constraint {
    
    Variable v1, v2;

    public ConstraintEqualityVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        //TODO
        return "";
    }

    protected boolean isSatisfied() {
        //TODO
        return true;
    }

    protected void reduce() {
        //TODO
    }

}
