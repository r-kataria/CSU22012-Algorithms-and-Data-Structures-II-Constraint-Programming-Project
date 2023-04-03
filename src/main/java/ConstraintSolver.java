package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class ConstraintSolver {

    private Domain dom;
    private List<Variable> variableSet;
    private List<Constraint> constraintSet;

    /**
     * Constructor for the ConstraintSolver class
     */
    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
    }

    /**
     * Returns a string representation of the variable set and the constraint set
     * 
     * @return a string representation of the variable set and the constraint set
     */
    public String toString() {
        // Print variables
        for (int i = 0; i < variableSet.size(); i++)
            System.out.println(variableSet.get(i));
        System.out.println("");

        // Print Constraints
        for (int i = 0; i < constraintSet.size(); i++)
            System.out.println(constraintSet.get(i));
        return "";
    }

    /**
     * Parses the input file and creates the variable set, the constraint set and
     * domains
     * 
     * @param fileName the name of the input file
     */
    private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if (currentLine.startsWith("Domain-")) {
                    // this is our domain - i.e. a datastructure that contains values and can be
                    // updated, played with etc.
                    String s = currentLine.replace("Domain-", "");
                    String[] array = s.split(",");
                    int[] vals = new int[array.length];
                    for (int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                } else if (currentLine.startsWith("Var-")) {
                    // this is the code for every variable (a name and a domain)
                    String s = currentLine.replace("Var-", "");
                    Variable var = new Variable(s, dom);
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
                    // this is the code for the constraints

                    // ConstraintDifferenceVarVar:
                    if (currentLine.startsWith("Cons-diff")) {
                        // keep spaces in the string
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-diff(", "").replaceAll(regexPattern, "");
                        String[] values = s.split(",");

                        String valName1 = values[0];
                        String valName2 = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(valName1)) {
                                v1 = element;
                            }
                            if (element.hasThisName(valName2)) {
                                v2 = element;
                            }
                        }

                        ConstraintDifferenceVarVar diff = new ConstraintDifferenceVarVar(v1, v2);
                        constraintSet.add(diff);
                    }

                    // ConstraintEqualityVarVar:
                    if (currentLine.startsWith("Cons-eqVV")) {
                        // keep spaces in the string
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-eqVV(", "").replaceAll(regexPattern, "");
                        String[] values = s.split(" = ");

                        String valName1 = values[0];
                        String valName2 = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(valName1)) {
                                v1 = element;
                            }
                            if (element.hasThisName(valName2)) {
                                v2 = element;
                            }
                        }

                        ConstraintEqualityVarVar eq = new ConstraintEqualityVarVar(v1, v2);
                        constraintSet.add(eq);
                    }

                    // ConstraintEqualityVarCons:
                    if (currentLine.startsWith("Cons-eqVC")) {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-eqVC(", "").replaceAll(regexPattern, "");
                        String[] values = s.split(" = ");

                        String valName = values[0];
                        Variable v = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(valName)) {
                                v = element;
                            }
                        }

                        ConstraintEqualityVarCons eq = new ConstraintEqualityVarCons(v, Integer.parseInt(values[1]));
                        constraintSet.add(eq);
                    }

                    // ConstraintEqualityVarPlusCons:
                    if (currentLine.startsWith("Cons-eqVPC")) {
                        String regexPattern = "\\(|\\)|\\ ";
                        String s = currentLine.replace("Cons-eqVPC(", "").replaceAll(regexPattern, "");
                        String[] values = s.split("=");
                        String[] values2 = values[1].split("\\+");
                        String val1Name = values[0];
                        String val2Name = values2[0];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if (element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2,
                                Integer.parseInt(values2[1]), false);
                        constraintSet.add(eq);
                    }

                    // ConstraintEqualityVarPlusCons:
                    if (currentLine.startsWith("Cons-abs")) {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-abs(", "").replaceAll(regexPattern, "");
                        String[] values = s.split(" = ");
                        String[] values2 = values[0].split(" - ");

                        String val1Name = values2[0];
                        String val2Name = values2[1];

                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if (element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2,
                                Integer.parseInt(values[1]), true);
                        constraintSet.add(eq);
                    }

                }

            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error.");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to check if the current state of the CSP is a solution.
     * 
     * @return true if the CSP is a solution, false otherwise.
     */
    private boolean isSolution() {
        // Check if all variables are reduced to only one value.
        for (Variable variable : variableSet) {
            if (variable.d.isReducedToOnlyOneValue()) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Selects the first unassigned variable from the variable set.
     * 
     * @return the first unassigned variable, or null if all variables are assigned
     */
    private Variable selectUnassignedVariable() {
        for (Variable variable : variableSet) {
            if (!variable.d.isReducedToOnlyOneValue() && !variable.d.isEmpty()) {
                return variable;
            }
        }

        // If all variables are assigned, return null.
        return null;
    }

    /**
     * Reduces the domain of variables to find a solution.
     */
    public void reduce() {
        // Apply domain splitting
        if (!reduceWithDomainSplitting()) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found.");
        }
    }

    /**
     * Recursively reduces the domain of variables using domain splitting.
     *
     * @return true if a solution is found, false otherwise
     */
    private boolean reduceWithDomainSplitting() {
        // Create a deep copy of the variableSet before applying constraints
        List<Variable> originalVariablesBeforeApplyingConstraints = deepCopyVariableSet(variableSet);

        // Apply constraints recursively; if not successful, restore the variable set
        // and return false
        if (!applyConstraints()) {
            restoreVariableSet(variableSet, originalVariablesBeforeApplyingConstraints);
            return false;
        }

        // Select the next unassigned variable
        Variable selectedVariable = selectUnassignedVariable();

        // If no more unassigned variables, check if the current assignment is a
        // solution
        if (selectedVariable == null) {
            return isSolution();
        }

        // Create a deep copy of the variableSet before splitting the domain
        List<Variable> originalVariables = deepCopyVariableSet(variableSet);

        // Split the selected variable's domain
        List<Domain> splitDomains = selectedVariable.d.split();

        // Iterate through each sub-domain resulting from the split
        for (Domain subDomain : splitDomains) {
            // Set the selected variable's domain to the current sub-domain
            selectedVariable.setDomain(subDomain);

            // Recursively call reduceWithDomainSplitting to find a solution with the
            // updated domain
            if (reduceWithDomainSplitting()) {
                return true; // If a solution is found, return true
            } else {
                // If no solution is found, restore the variable set to its original state
                restoreVariableSet(variableSet, originalVariables);
            }
        }

        // If the loop finishes without finding a solution, restore the original domain
        // of the selected variable
        restoreVariableSet(variableSet, originalVariables);

        // Return false if no solution is found after iterating through all sub-domains
        return false;
    }

    /**
     * Applies constraints iteratively to the constraint set.
     * 
     * @return true if all constraints are satisfied, false otherwise
     */
    private boolean applyConstraints() {
        for (Constraint constraint : constraintSet) {
            constraint.reduce();
            if (!constraint.isSatisfied()) {

                return false;
            }
        }

        return true;
    }

    /**
     * Creates a deep copy of the variable set.
     * 
     * @param variableSet the variable set to be copied
     * @return a deep copy of the variable set
     */
    private List<Variable> deepCopyVariableSet(List<Variable> variableSet) {
        List<Variable> copiedVariables = new ArrayList<>();
        for (Variable variable : variableSet) {
            copiedVariables.add(new Variable(variable));
        }
        return copiedVariables;
    }

    /**
     * Restores the variable set to its original state.
     * 
     * @param variableSet       the variable set to be restored
     * @param originalVariables the original variable set
     */
    private void restoreVariableSet(List<Variable> variableSet, List<Variable> originalVariables) {
        for (int i = 0; i < variableSet.size(); i++) {
            variableSet.get(i).setDomain(originalVariables.get(i).d);
        }
    }

    /**
     * Parses the input file, reduces the domain of variables, and returns the
     * solution in the form of an ArrayList of Strings.
     * 
     * @param filename the name of the file containing the data
     */
    public ArrayList<String> printAnswer(String filename) {

        // Parse the input file
        parse(filename);

        // Reduce the domain of variables
        reduce();

        ArrayList<String> answer = new ArrayList<>();
        for (Variable variable : variableSet) {
            // Each string is in the form "Sol-Zebra-5"
            answer.add("Sol-" + variable.name + "-" + variable.d.getFirstValue());
        }

        return answer;
    }

    /**
     * The main method.
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Create a new ConstraintSolver object
        ConstraintSolver problem = new ConstraintSolver();

        // Parse the input file and reduce the domain of variables
        ArrayList<String> answer = problem.printAnswer("data.txt");

        // Print the solution
        for (String string : answer) {
            System.out.println(string);
        }

    }

}