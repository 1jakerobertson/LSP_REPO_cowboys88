package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.*;

public class ETLPipeline {
    private static final String HEADER = "ProductID,Name,Price,Category,PriceRange";

    public static void main(String[] args) {
        Path input = Paths.get("data", "products.csv");   
        Path output = Paths.get("data", "transformed_products.csv");

        if (!Files.exists(input)) {
            System.err.println("ERROR: Input file not found at " + input.toAbsolutePath());
            return;
        }

        long rowsRead = 0, rowsTransformed = 0, rowsSkipped = 0;

        try (
            BufferedReader reader = Files.newBufferedReader(input);
            BufferedWriter writer = Files.newBufferedWriter(output,
                java.nio.charset.StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
        ) {
            
            writer.write(HEADER);
            writer.newLine();

        
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                rowsRead++;
                if (line.isBlank()) {
                    rowsSkipped++;
                    continue;
                }

                String[] cols = line.split(",", -1);
                if (cols.length != 4) {
                    rowsSkipped++;
                    continue;
                }

                int productId;
                BigDecimal price;
                try {
                    productId = Integer.parseInt(cols[0].trim());
                    price = new BigDecimal(cols[2].trim());
                } catch (Exception e) {
                    rowsSkipped++;
                    continue;
                }

                String name = cols[1].trim().toUpperCase();
                String category = cols[3].trim();
                String originalCategory = category;

                
                if (originalCategory.equalsIgnoreCase("Electronics")) {
                    price = price.multiply(new BigDecimal("0.90"));
                }

                
                price = price.setScale(2, RoundingMode.HALF_UP);

                
                if (originalCategory.equalsIgnoreCase("Electronics")
                        && price.compareTo(new BigDecimal("500.00")) > 0) {
                    category = "Premium Electronics";
                }

                
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

                
                writer.write(productId + "," + name + "," + price + "," + category + "," + priceRange);
                writer.newLine();
                rowsTransformed++;
            }

            
            System.out.println("----- RUN SUMMARY -----");
            System.out.println("Rows read:        " + rowsRead);
            System.out.println("Rows transformed: " + rowsTransformed);
            System.out.println("Rows skipped:     " + rowsSkipped);
            System.out.println("Output written:   " + output.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("ERROR processing file: " + e.getMessage());
        }
    }
}