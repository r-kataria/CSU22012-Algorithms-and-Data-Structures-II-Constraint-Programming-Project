package main.java;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Domain {

    private Set<Integer> vals = new HashSet<>();

    /**
     * This is the constructor for the Domain class.
     * It takes an array of integers and creates a domain object.
     *
     * @param vals the array of integers
     */
    public Domain(int[] vals) {
        for (int val : vals) {
            this.vals.add(val);
        }
    }

    /**
     * This is the constructor for the Domain class.
     * It takes a Domain object and copies it.
     *
     * @param d2 the domain to be copied
     */
    public Domain(Domain d2) {
        this.vals.addAll(d2.vals);
    }

    /**
     * Returns a string representation of the domain.
     * The string representation consists of a list of integers enclosed in braces
     * ({}) and separated by commas.
     *
     * @return a string representation of the domain
     */
    public String toString() {
        String result = "{";
        int i = 0;
        for (Integer val : vals) {
            result += val;
            if (i != vals.size() - 1) {
                result += ", ";
            }
            i++;
        }
        result += "}";

        return result;
    }

    /**
     * Method to split the domain into two halves.
     *
     * @return a list containing two domains that are the result of splitting the
     *         domain in half
     */
    public List<Domain> split() {
        List<Domain> splitDomains = new ArrayList<>();

        if (vals.size() == 1) {
            splitDomains.add(this);
        } else {
            int mid = vals.size() / 2;
            int[] firstDomain = new int[mid];
            int[] secondDomain = new int[vals.size() - mid];

            int i = 0;
            for (Integer val : vals) {
                if (i < mid) {
                    firstDomain[i] = val;
                } else {
                    secondDomain[i - mid] = val;
                }
                i++;
            }

            splitDomains.add(new Domain(firstDomain));
            splitDomains.add(new Domain(secondDomain));
        }

        return splitDomains;
    }

    /**
     * Method to check if the domain is empty.
     *
     * @return true if the domain is empty
     */
    public boolean isEmpty() {
        return vals.isEmpty();
    }

    /**
     * Method to check if the domain is equal to another domain.
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
     * @return true if the domain is reduced to only one value
     */
    public boolean isReducedToOnlyOneValue() {
        return vals.size() == 1;
    }

    /**
     * Method to delete a value from the domain.
     *
     * @param val the value to be deleted
     */
    public void delete(int val) {
        vals.remove(val);
    }

    /**
     * The method returns the intersection of this domain with another domain.
     *
     * @param d2 the domain to find the intersection with
     * @return the intersection of this domain with another domain as a Set of
     *         integers
     */
    public Set<Integer> intersection(Domain d2) {
        Set<Integer> intersectionSet = new HashSet<>(this.vals);
        intersectionSet.retainAll(d2.vals);
        return intersectionSet;
    }

    /**
     * Method to check if the domain contains a value.
     *
     * @param val the value to check
     * @return true if the domain contains the value
     */
    public boolean contains(int val) {
        return vals.contains(val);
    }

    /**
     * Method to get the size of the domain.
     *
     * @return the size of the domain
     */
    public int size() {
        return vals.size();
    }
    
    /**
     * Method to set the values of the domain.
     *
     * @return the values of the domain
     */
    public void setVals(Set<Integer> vals) {
        this.vals = vals;
    }

    /**
     * Method to get the values of the domain.
     * 
     * @return the values of the domain
     */
    public Set<Integer> getVals() {
        return vals;
    }


    /**
     * Method to get the first value of the domain.
     *
     * @return the values of the domain
     */
    public int getFirstValue() {
        return vals.iterator().next();
    }
}