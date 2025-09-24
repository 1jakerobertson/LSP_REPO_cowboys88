package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.stream.Stream;

public interface ProductReader extends AutoCloseable {
    Stream<String> lines() throws IOException;  // raw CSV lines
    String header();
    @Override void close() throws IOException;
}