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

    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
    }

    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++)
            System.out.println(variableSet.get(i));
        System.out.println("");
        //print constraints
        for(int i = 0; i < constraintSet.size(); i++)
            System.out.println(constraintSet.get(i));
        return "";
    }

    private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();

                if(currentLine.startsWith("Domain-")) {
                    //this is our domain - i.e. a datastructure that contains values and can be updated, played with etc.
                    String s = currentLine.replace("Domain-","");
                    String[] array = s.split(","); 
                    int[] vals = new int[array.length];
                    for(int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                } else if (currentLine.startsWith("Var-")) {
                    //this is the code for every variable (a name and a domain)
                    String s = currentLine.replace("Var-","");
                    Variable var = new Variable(s, dom); 
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
                    //this is the code for the constraints


                    //ConstraintDifferenceVarVar:
                    if (currentLine.startsWith("Cons-diff")) {
                        // keep spaces in the string
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-diff(","").replaceAll(regexPattern, "");
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


                    //ConstraintEqualityVarVar:
                    if (currentLine.startsWith("Cons-eqVV")) {
                        // keep spaces in the string
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-eqVV(","").replaceAll(regexPattern, "");
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


                    //ConstraintEqualityVarCons:
                    if (currentLine.startsWith("Cons-eqVC")) {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-eqVC(","").replaceAll(regexPattern, "");
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



                    //ConstraintEqualityVarPlusCons:
                    if (currentLine.startsWith("Cons-eqVPC")) {
                        String regexPattern = "\\(|\\)|\\ ";
                        String s = currentLine.replace("Cons-eqVPC(","").replaceAll(regexPattern, "");
                        String[] values = s.split("="); 
                        String[] values2 = values[1].split("\\+");
                        String val1Name = values[0];
                        String val2Name = values2[0];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element; 
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2, Integer.parseInt(values2[1]), false);
                        constraintSet.add(eq);
                    }


                    //ConstraintEqualityVarPlusCons:
                    if (currentLine.startsWith("Cons-abs")) {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-abs(","").replaceAll(regexPattern, "");
                        String[] values = s.split(" = "); 
                        String[] values2 = values[0].split(" - ");
                    

                        String val1Name = values2[0];
                        String val2Name = values2[1];
                    
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element; 
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2, Integer.parseInt(values[1]), true);
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

    private boolean areConstraintsSatisfied(Variable variable) {
        for (Constraint constraint : constraintSet) {
            if (constraint.involves(variable) && !constraint.isSatisfied()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isSolution() {
        for (Constraint constraint : constraintSet) {
            if (!constraint.isSatisfied()) {
                return false;
            }
        }
        return true;
    }

    private Variable selectUnassignedVariable() {
        for (Variable variable : variableSet) {
            if (!variable.d.isReducedToOnlyOneValue() && !variable.d.isEmpty()) {
                return variable;
            }
        }
        return null; // If all variables are assigned, return null.
    }
    
    public void reduce() {
        // First, apply basic constraint reduction.
        for (Constraint constraint : constraintSet) {
            constraint.reduce();
        }
    
        // Then, apply domain splitting.
        if (!reduceWithDomainSplitting()) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Solution found.");
        }
    }
    
    private boolean reduceWithDomainSplitting() {
        Variable selectedVariable = selectUnassignedVariable();
        if (selectedVariable == null) {
            return isSolution(); // If no more unassigned variables, check if the current assignment is a solution.
        }
    
        Domain originalDomain = selectedVariable.getDomain();
        List<Domain> splitDomains = originalDomain.split();
    
        for (Domain subDomain : splitDomains) {
            selectedVariable.setDomain(subDomain);
            if (applyConstraints()) {
                if (reduceWithDomainSplitting()) {
                    return true; // If a solution is found, return true.
                }
            }
        }
    
        // If the loop finishes without finding a solution, backtrack and restore the original domain.
        selectedVariable.setDomain(originalDomain);
        return false;
    }
    
    private boolean applyConstraints() {
        boolean progress = false;
        for (Constraint constraint : constraintSet) {
            boolean reduced = constraint.reduce();
            if (reduced) {
                progress = true;
            }
        }
        return progress;
    }
    
    public static void main(String[] args) {
        ConstraintSolver problem = new ConstraintSolver();
        problem.parse("data.txt");
        System.out.println(problem);
        problem.reduce();
        System.out.println("@@@@@@\n@@@@@@\n@@@@@@\n@@@@@@\n@@@@@@\n@@@@@@\n@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(problem);
    }

}