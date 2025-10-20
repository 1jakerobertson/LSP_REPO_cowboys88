package org.howard.edu.lsp.midterm.question2;

public class AreaCalculator {
    // cricle area
    public static double area(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("radius must be > 0");
        }
        return Math.PI * radius * radius;
    }

    // rectangle area
     public static double area(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("width and height must be > 0");
        }
        return width * height;
    }
}
