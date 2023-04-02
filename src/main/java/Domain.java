package main.java;

import java.util.List;
import java.util.ArrayList;

public class Domain {

    List<Integer> vals=new ArrayList<Integer>();


    /**
     * This is the constructor for the Domain class.
     * It takes an array of integers and creates a domain object.
     * 
     * Time complexity: O(n)
     * 
     * @param vals the array of integers
     */
    public Domain(int[] vals) {
        for (int i = 0; i < vals.length; i++) {
            this.vals.add(vals[i]);
        }
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
        for (int i = 0; i < d2.vals.size(); i++) {
            this.vals.add(d2.vals.get(i));
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
        for (int i = 0; i < vals.size(); i++) {
            result += vals.get(i);
            if (i != vals.size() - 1) {
                result += ", ";
            }
        }
        result += "}";

        return result;
    }

    /**
     * Method to split the domain in two halves.
     * 
     * @return a list containing two domains that are the result of splitting the domain in half
     */
    public List<Domain> split() {
        List<Domain> splitDomains = new ArrayList<>();

        if(vals.size() == 1) {
            splitDomains.add(this);
        } else {
            int mid = vals.size() / 2;

            int[] firstDomain = new int[mid];
            int[] secondDomain = new int[vals.size() - mid];

            for (int i = 0; i < mid; i++) {
                firstDomain[i] = vals.get(i);
            }
            for (int i = mid; i < vals.size(); i++) {
                secondDomain[i - mid] = vals.get(i);
            }

            splitDomains.add(new Domain(firstDomain));
            splitDomains.add(new Domain(secondDomain));
        }
        
        return splitDomains;
        
    }

    /**
     * Method to check if the domain is empty.
     * 
     * Time complexity: O(1)
     * 
     * @return true if the domain is empty
     */
    public boolean isEmpty() {
        return vals.size() == 0;
    }

    /**
     * Method to check if the domain is equal to another domain.
     * 
     * Time complexity: O(n)
     * 
     * @param d2 the domain to compare with this domain
     * @return true if the domains are equal, false otherwise
     */
    public boolean equals(Domain d2) {
        return vals.equals(d2.vals);
    }

    /**
     * Method to check if the domain is reduced to only one value.
     * 
     * Time complexity: O(1)
     * 
     * @return true if the domain is reduced to only one value
     */
    public boolean isReducedToOnlyOneValue() {
        return vals.size() == 1;
    }

    /**
     * Method to delete a value from the domain.
     * 
     * Time complexity: O(n)
     * 
     * @param val the value to be deleted
     */
    public void delete(int val) {

        // Find the index of the value to be deleted
        int index = vals.indexOf(val);

        // If the value is in the domain, delete it
        if (index != -1) {
            vals.remove(index);
        }

    }


    /**
     * The method returns the intersection of this domain with another domain.
     * 
     * Time complexity: O(n)
     * 
     * @param d2
     * @return the intersection of this domain with another domain
     */
    public List<Integer> intersection(Domain d2) {

        List<Integer> intersectionList = new ArrayList<>();

        for (int val : this.vals) {
            if (d2.vals.contains(val)) {
                intersectionList.add(val);
            }
        }

        return intersectionList;
    }

}
