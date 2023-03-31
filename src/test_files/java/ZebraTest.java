package test.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;
import org.junit.Assert;

import main.java.ConstraintSolver;

public class ZebraTest {

    @Test
    public void runTest() {
        ConstraintSolver solver = new ConstraintSolver();

        // Actual answer
        String formattedAnswer = solver.printAnswer("data.txt");

        // Expected answer
        File inputFile = new File("sol.txt");
        try (Scanner scanner = new Scanner(inputFile)) {
            String expectedAnswer = "";

                while (scanner.hasNextLine()) {
                    String currentLine = scanner.nextLine();
                    expectedAnswer += currentLine += "\n";
                }

            // Compare
            Assert.assertEquals(formattedAnswer, expectedAnswer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    
}
