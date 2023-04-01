package main.java;

public class ConstraintEqualityVarPlusCons extends Constraint {
    
    Variable v1, v2;
    int cons;
    Boolean abs;

    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, int cons, Boolean abs){
        this.v1 = v1;
        this.v2 = v2;
        this.cons = cons;
        this.abs = abs;
    }

    public String toString() {
        String result = "";
        if(!abs)    
            result += this.v1 + " = " + this.v2 + " + " + this.cons;
        return result;
    }

    protected boolean isSatisfied() {
        //TODO
        return true;
    }

    protected void reduce() {

        //from d1
        for(int i = 0; i < this.v1.d.vals.length; i++) {
            Boolean flag = false;
            if(!abs) {
                for(int j = 0; j < this.v2.d.vals.length; j++) { 
                    if(this.v1.d.vals[i] == this.v2.d.vals[j] + this.cons)
                        flag = true;
                }
                if (!flag) {
                    v1.d.delete(i); 
                }
            } else {
                //TODO
            }
        }

        //from d2
        for(int i = 0; i < this.v2.d.vals.length; i++) {
            Boolean flag = false;
            if(!abs) {
                for(int j = 0; j < this.v1.d.vals.length; j++) { 
                    if(this.v2.d.vals[i] == this.v1.d.vals[j] - this.cons)
                        flag = true;
                }
                if (!flag) {
                    v2.d.delete(i); 
                }
            } else {
                //TODO
            }
        }
    }

}