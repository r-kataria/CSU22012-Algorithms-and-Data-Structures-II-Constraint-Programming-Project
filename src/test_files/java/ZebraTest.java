package test_files.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;
import org.junit.Assert;

import main.java.ConstraintSolver;

public class ZebraTest {

    @Test
    public void runTest() {

        ConstraintSolver solver = new ConstraintSolver();

        ArrayList<String> myAnswer = solver.printAnswer("data.txt");
        ArrayList<String> expectedAnswer = new ArrayList<String>();

        File inputFile = new File("sol.txt");
        try (Scanner scanner = new Scanner(inputFile)) {

                while (scanner.hasNextLine()) {
                    String currentLine = scanner.nextLine();
                    expectedAnswer.add(currentLine);
                }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 

        Boolean allFound = true;

        if (myAnswer.size() != expectedAnswer.size()) {
            allFound = false;
        } else {
            for (String str1 : expectedAnswer) {
                str1 = str1.replaceAll(" ", "");
                boolean found = false;
                for (String str2 : myAnswer) {
                    str2 = str2.replaceAll(" ", "");
                    if (str1.equals(str2)) {
                        found = true;
                        break;
                    }
                } 
                if (!found) {
                    allFound = false;
                    break;
                }
            }
        }

        Assert.assertTrue("Answers match", allFound == true);

    }
    
}
