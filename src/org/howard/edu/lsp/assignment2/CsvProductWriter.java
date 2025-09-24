package org.howard.edu.lsp.assignment2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class CsvProductWriter implements AutoCloseable {
    public static final String HEADER = "ProductID,Name,Price,Category,PriceRange";
    private final BufferedWriter writer;

    public CsvProductWriter(Path output) throws IOException {
        Files.createDirectories(output.getParent());
        this.writer = Files.newBufferedWriter(
            output, StandardCharsets.UTF_8,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING
        );
        writer.write(HEADER);
        writer.newLine();
    }

    public void writeLine(String csv) throws IOException {
        writer.write(csv);
        writer.newLine();
    }

    public void writeAll(List<String> csvLines) throws IOException {
        for (String line : csvLines) {
            writeLine(line);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}