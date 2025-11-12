package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test cases for the IntegerSet class.
 * Each public method in IntegerSet is tested with
 * typical cases and basic edge cases.
 */
public class IntegerSetTest {

    @Test
    public void testClearAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        assertFalse(set.isEmpty());

        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    @Test
    public void testLength() {
        IntegerSet set = new IntegerSet();
        assertEquals(0, set.length());

        set.add(10);
        set.add(20);
        assertEquals(2, set.length());

        // adding duplicate should not change length
        set.add(10);
        assertEquals(2, set.length());
    }

    @Test
    public void testEqualsSameContentsDifferentOrder() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(3);
        set2.add(1);
        set2.add(2);

        assertTrue(set1.equals(set2));
        assertTrue(set2.equals(set1));
        assertTrue(set1.equals(set1)); // reflexive

        IntegerSet set3 = new IntegerSet();
        set3.add(1);
        set3.add(2);
        assertFalse(set1.equals(set3));   // different size/content

        assertFalse(set1.equals(null));   // compare with null
        assertFalse(set1.equals("test")); // compare with different type
    }

    @Test
    public void testContains() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(10);

        assertTrue(set.contains(5));
        assertTrue(set.contains(10));
        assertFalse(set.contains(7));
    }

    @Test
    public void testLargestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(10);
        set.add(3);

        assertEquals(10, set.largest());
    }

    @Test
    public void testLargestThrowsOnEmpty() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, set::largest);
    }

    @Test
    public void testSmallestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(10);
        set.add(3);

        assertEquals(3, set.smallest());
    }

    @Test
    public void testSmallestThrowsOnEmpty() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, set::smallest);
    }

    @Test
    public void testAddNoDuplicates() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(1);
        set.add(2);

        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
    }

    @Test
    public void testRemoveExistingAndNonExisting() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);

        set.remove(2);
        assertFalse(set.contains(2));
        assertEquals(2, set.length());

        // Removing a value not in the set should do nothing
        set.remove(99);
        assertEquals(2, set.length());

        // Removing from an empty set does not throw
        set.clear();
        set.remove(1);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testUnionWithAnotherSet() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        set1.union(set2);
        // set1 should now contain 1, 2, 3
        assertEquals(3, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));

        // set2 should remain unchanged
        assertEquals(2, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
    }

    @Test
    public void testUnionWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        // Should not create duplicates or throw
        set.union(set);
        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
    }

    @Test
    public void testIntersect() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);
        set2.add(4);

        set1.intersect(set2);

        assertEquals(2, set1.length());
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(4));

        // set2 unchanged
        assertEquals(3, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
        assertTrue(set2.contains(4));
    }

    @Test
    public void testDiff() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(4);

        set1.diff(set2);

        // 2 should be removed; 1 and 3 remain
        assertEquals(2, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(2));
    }

    @Test
    public void testDiffWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        set.diff(set);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testComplement() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);
        set2.add(4);

        // After complement, set1 should become (set2 \ original set1)
        set1.complement(set2);

        assertEquals(2, set1.length());
        assertTrue(set1.contains(3));
        assertTrue(set1.contains(4));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(2));

        // set2 unchanged
        assertEquals(3, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
        assertTrue(set2.contains(4));
    }

    @Test
    public void testComplementWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        set.complement(set);
        // (set \ set) should be empty
        assertTrue(set.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        IntegerSet set = new IntegerSet();
        assertTrue(set.isEmpty());

        set.add(1);
        assertFalse(set.isEmpty());

        set.clear();
        assertTrue(set.isEmpty());
    }

    @Test
    public void testToStringFormat() {
        IntegerSet set = new IntegerSet();
        assertEquals("[]", set.toString());

        set.add(1);
        set.add(2);
        set.add(3);

        // Order matches insertion order in our implementation
        assertEquals("[1, 2, 3]", set.toString());
    }
}
