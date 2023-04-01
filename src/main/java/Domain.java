package main.java;

import java.util.Arrays;

public class Domain {

    int[] vals;

    /**
     * This is the constructor for the Domain class.
     * It takes an array of integers and creates a domain object.
     * 
     * Time complexity: O(1)
     * 
     * @param vals the array of integers
     */
    public Domain(int[] vals) {
        this.vals = vals;
    }

    /**
     * This is the constructor for the Domain class.
     * It takes a Domain object and copies it.
     * 
     * Time complexity: O(n)
     * 
     * @param d2 the domain to be copied
     */
    public Domain(Domain d2) {
        vals = new int[d2.vals.length];

        for (int i = 0; i < vals.length; i++) {
            this.vals[i] = d2.vals[i];
        }
    }

    /**
     * Returns a string representation of the domain.
     * The string representation consists of a list of integers enclosed in braces
     * ({}) and separated by commas.
     *
     * Time complexity: O(n)
     * 
     * @return a string representation of the domain
     */
    public String toString() {
        String result = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    /**
     * @return
     */
    private Domain[] split() {
        return (new Domain[2]);
    }

    /**
     * Method to check if the domain is empty.
     * 
     * Time complexity: O(1)
     * 
     * @return true if the domain is empty
     */
    private boolean isEmpty() {
        return vals.length == 0;
    }

    /**
     * Checks if this domain is equal to the specified domain.
     * Two domains are considered equal if they have the same set of values,
     * regardless of the order of the values.
     *
     * Time complexity: O(n log n)
     * 
     * @param d2 the domain to compare with this domain
     * @return true if the domains are equal, false otherwise
     */
    private boolean equals(Domain d2) {
        // If the domains have different sizes, they are not equal
        if (this.vals.length != d2.vals.length) {
            return false;
        }

        // Make a copy of the input domain's values and sort both arrays
        int[] d2ValsSorted = Arrays.copyOf(d2.vals, d2.vals.length);
        Arrays.sort(this.vals);
        Arrays.sort(d2ValsSorted);

        // Compare the sorted arrays for equality
        return Arrays.equals(this.vals, d2ValsSorted);
    }

    /**
     * Method to check if the domain is reduced to only one value.
     * 
     * Time complexity: O(1)
     * 
     * @return true if the domain is reduced to only one value
     */
    private boolean isReducedToOnlyOneValue() {
        return vals.length == 1;
    }

}
