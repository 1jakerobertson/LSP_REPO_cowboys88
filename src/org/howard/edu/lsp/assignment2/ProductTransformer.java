package org.howard.edu.lsp.assignment2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductTransformer {
    // Business rules extracted from your original main
    public Product fromCsv(String csvLine) {
        if (csvLine == null || csvLine.isBlank()) return null;

        String[] cols = csvLine.split(",", -1);
        if (cols.length != 4) return null;

        try {
            int id = Integer.parseInt(cols[0].trim());
            BigDecimal price = new BigDecimal(cols[2].trim());
            String name = cols[1].trim().toUpperCase();
            String category = cols[3].trim();

            // Discount for Electronics
            boolean isElectronics = category.equalsIgnoreCase("Electronics");
            if (isElectronics) {
                price = price.multiply(new BigDecimal("0.90"));
            }

            // Round to 2 dp
            price = price.setScale(2, RoundingMode.HALF_UP);

            // Reclassify premium electronics
            if (isElectronics && price.compareTo(new BigDecimal("500.00")) > 0) {
                category = "Premium Electronics";
            }

            // Price range
            String priceRange;
            if (price.compareTo(new BigDecimal("10.00")) <= 0) {
                priceRange = "Low";
            } else if (price.compareTo(new BigDecimal("100.00")) <= 0) {
                priceRange = "Medium";
            } else if (price.compareTo(new BigDecimal("500.00")) <= 0) {
                priceRange = "High";
            } else {
                priceRange = "Premium";
            }

            return new Product(id, name, price, category, priceRange);
        } catch (Exception e) {
            return null; // treat as a skipped row
        }
    }

    public String toCsv(Product p) {
        return p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getCategory() + "," + p.getPriceRange();
    }
}