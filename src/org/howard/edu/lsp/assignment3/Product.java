package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;

public class Product {
    private final int id;
    private final String name;      // normalized upper-case
    private final BigDecimal price; // final price (after any discounts)
    private final String category;  // possibly reclassified
    private final String priceRange;

    public Product(int id, String name, BigDecimal price, String category, String priceRange) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.priceRange = priceRange;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getPriceRange() { return priceRange; }
}