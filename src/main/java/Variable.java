package main.java;

public class Variable {

    String name;
    Domain d;

    public Variable(String name, Domain d) {
        this.name = name;
        this.d = new Domain(d);
    }

    public Variable(Variable other) {
        this.name = other.name;
        this.d = new Domain(other.d);
    }
    

    public String toString() {
        return this.name + "= " + d;
    }

    public Boolean hasThisName(String name) {
        return this.name.equals(name);
    }

    public Domain getDomain() {
        return d;
    }

    public void setDomain(Domain domain) {
        this.d = domain;
    }

}
