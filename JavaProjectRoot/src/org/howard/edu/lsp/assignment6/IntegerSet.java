package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple integer set implementation backed by an ArrayList.
 * The set does not allow duplicate values and supports common
 * set operations such as union, intersection, and difference.
 */
public class IntegerSet {
    /** Internal storage for the set elements. */
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set.
     * All elements are removed.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the number of elements in the set
     */
    public int length() {
        return set.size();
    }

    /**
     * Returns true if the 2 sets are equal, false otherwise.
     * Two sets are equal if they contain all of the same values
     * in ANY order. This overrides the equals method from Object.
     *
     * @param o the object to compare with this set
     * @return true if the sets have the same elements, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntegerSet other = (IntegerSet) o;

        // Quick length check first
        if (this.length() != other.length()) {
            return false;
        }

        // Same size and each contains all elements of the other
        return this.set.containsAll(other.set) && other.set.containsAll(this.set);
    }

    /**
     * Returns true if the set contains the specified value.
     *
     * @param value the value to check for membership
     * @return true if value is in the set, otherwise false
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set.
     *
     * @return the largest element in the set
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot get largest element from an empty set.");
        }
        return Collections.max(set);
    }

    /**
     * Returns the smallest item in the set.
     *
     * @return the smallest element in the set
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot get smallest element from an empty set.");
        }
        return Collections.min(set);
    }

    /**
     * Adds an item to the set if it is not already present.
     *
     * @param item the value to add
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set if it is present.
     * If the item is not in the set, this method does nothing.
     *
     * @param item the value to remove
     */
    public void remove(int item) {
        // Use Integer.valueOf to ensure remove(Object) is called, not remove(index)
        set.remove(Integer.valueOf(item));
    }

    /**
     * Set union: modifies this set so that it contains all unique
     * elements that are in this set or the other set.
     *
     * @param other the other set to union with
     */
    public void union(IntegerSet other) {
        // Use a copy to avoid issues if other == this
        List<Integer> otherList = new ArrayList<Integer>(other.set);
        for (int value : otherList) {
            if (!set.contains(value)) {
                set.add(value);
            }
        }
    }

    /**
     * Set intersection: modifies this set so that it contains only
     * the elements that are found in both this set and the other set.
     *
     * @param other the other set to intersect with
     */
    public void intersect(IntegerSet other) {
        // Using retainAll with a copy avoids side effects if other == this
        List<Integer> otherList = new ArrayList<Integer>(other.set);
        set.retainAll(otherList);
    }

    /**
     * Set difference (this \ other): modifies this set so that it removes
     * all elements that are also found in the other set.
     *
     * @param other the other set whose elements will be removed from this set
     */
    public void diff(IntegerSet other) {
        // Use a copy to handle the case where other == this
        List<Integer> otherList = new ArrayList<Integer>(other.set);
        set.removeAll(otherList);
    }

    /**
     * Set complement: modifies this set so that it becomes (other \ this).
     * In other words, after this operation, this set contains those elements
     * that are in {@code other} but not in the original version of this set.
     *
     * @param other the reference set for the complement
     */
    public void complement(IntegerSet other) {
        // Capture original contents of this and other before modifying
        List<Integer> originalThis = new ArrayList<Integer>(this.set);
        List<Integer> otherList = new ArrayList<Integer>(other.set);

        set.clear();
        for (int value : otherList) {
            if (!originalThis.contains(value)) {
                set.add(value);
            }
        }
    }

    /**
     * Returns true if the set is empty, false otherwise.
     *
     * @return true if this set has no elements, false otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a String representation of the set.
     * The format is a list-style representation, e.g., [1, 2, 3].
     *
     * @return a String representing the elements in the set
     */
    @Override
    public String toString() {
        return set.toString();
    }
}
