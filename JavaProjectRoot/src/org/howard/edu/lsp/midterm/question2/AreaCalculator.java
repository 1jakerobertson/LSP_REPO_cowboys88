package org.howard.edu.lsp.midterm.question2;

public class AreaCalculator {
    // cricle area
    public static double area(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be > 0");
        }
        return Math.PI * radius * radius;
    }
}
