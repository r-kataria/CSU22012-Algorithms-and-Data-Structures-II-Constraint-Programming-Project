package main.java;

/**
 * The Variable class represents a named variable with an associated domain.
 * Each variable has a unique name and a domain of possible values.
 */
public class Variable {

    String name;
    Domain d;

    /**
     * Constructs a new variable with the specified name and domain.
     *
     * @param name the name of the variable
     * @param d    the domain of the variable
     */
    public Variable(String name, Domain d) {
        this.name = name;
        this.d = new Domain(d);
    }

    /**
     * Copy constructor for the Variable class.
     * Creates a new variable with the same name and domain as the given variable.
     *
     * @param other the variable to be copied
     */
    public Variable(Variable other) {
        this.name = other.name;
        this.d = new Domain(other.d);
    }

    /**
     * Returns a string representation of the variable,
     * including its name and domain.
     *
     * @return a string representation of the variable
     */
    public String toString() {
        return this.name + "= " + d;
    }

    /**
     * Checks if the variable has the given name.
     *
     * @param name the name to check
     * @return true if the variable has the given name, false otherwise
     */
    public Boolean hasThisName(String name) {
        return this.name.equals(name);
    }

    /**
     * Returns the domain of the variable.
     *
     * @return the domain of the variable
     */
    public Domain getDomain() {
        return d;
    }

    /**
     * Sets the domain of the variable.
     *
     * @param domain the new domain for the variable
     */
    public void setDomain(Domain domain) {
        this.d = domain;
    }

}
