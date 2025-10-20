package org.howard.edu.lsp.midterm.question2;

public class Main {
    public static void main(String[] args) {
        // Required exact outputs
        System.out.println("Circle radius 3.0 \u2192 area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 \u2192 area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 \u2192 area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 \u2192 area = " + AreaCalculator.area(4));

        // Demonstrate exception handling
        try {
            double invalid = AreaCalculator.area(0.0); // should trigger IllegalArgumentException
            System.out.println("This line will not print: " + invalid);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*
     * Explanation:
     * Method overloading allows us to use one method name, "area", for different shapes,
     * as long as the parameter types and counts differ. This keeps the API simple and
     * consistent—users only need to remember one name. Having separate methods like
     * circle */

}
