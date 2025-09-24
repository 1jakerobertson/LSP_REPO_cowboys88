package org.howard.edu.lsp.assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.stream.Stream;

public class CsvProductReader implements ProductReader {
    private final BufferedReader reader;
    private final String header;

    public CsvProductReader(Path input) throws IOException {
        if (!Files.exists(input)) {
            throw new NoSuchFileException("Input file not found: " + input.toAbsolutePath());
        }
        this.reader = Files.newBufferedReader(input, StandardCharsets.UTF_8);
        this.header = reader.readLine(); // consume header
    }

    @Override
    public Stream<String> lines() {
        // BufferedReader#lines() starts from current position (after header)
        return reader.lines();
    }

    @Override
    public String header() {
        return header != null ? header : "";
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}