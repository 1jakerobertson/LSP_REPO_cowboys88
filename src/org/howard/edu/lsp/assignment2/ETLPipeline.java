package org.howard.edu.lsp.assignment2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class ETLPipeline {
    public static void main(String[] args) {
        Path input  = Paths.get("data", "products.csv");
        Path output = Paths.get("data", "transformed_products.csv");

        AtomicLong rowsRead = new AtomicLong();
        AtomicLong rowsTransformed = new AtomicLong();
        AtomicLong rowsSkipped = new AtomicLong();

        ProductTransformer transformer = new ProductTransformer();

        try (ProductReader reader = new CsvProductReader(input);
             CsvProductWriter writer = new CsvProductWriter(output)) {

            try (Stream<String> lines = reader.lines()) {
                lines.forEach(line -> {
                    rowsRead.incrementAndGet();
                    Product p = transformer.fromCsv(line);
                    if (p == null) {
                        rowsSkipped.incrementAndGet();
                        return;
                    }
                    String out = transformer.toCsv(p);
                    try {
                        writer.writeLine(out);
                        rowsTransformed.incrementAndGet();
                    } catch (IOException e) {
                        rowsSkipped.incrementAndGet();
                    }
                });
            }

            System.out.println("----- RUN SUMMARY -----");
            System.out.println("Rows read:        " + rowsRead.get());
            System.out.println("Rows transformed: " + rowsTransformed.get());
            System.out.println("Rows skipped:     " + rowsSkipped.get());
            System.out.println("Output written:   " + output.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
