package main.java;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }


    public Domain(Domain d2) {
        //TODO make a copy of the domain from what d2 contains
        vals = new int[d2.vals.length];
        for(int i = 0; i < vals.length; i++)
            this.vals[i] = d2.vals[i];
    }

    /**
     * @return
     */
    public String toString() {
        String result  = "{";
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
     * @return
     */
    private boolean isEmpty() {
        return true;
    }

    /**
     * @return
     */
    private boolean equals(Domain d2) {
        return true;
    }

    /**
     * @return
     */
    private boolean  isReducedToOnlyOneValue() {
        return true;
    }



}
