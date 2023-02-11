package com.yelstream.topp.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.function.Consumer;

/**
 * Gobbles up individual lines from a stream of raw data.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-29
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class StreamGobbler implements Runnable, AutoCloseable {
    /**
     * Stream to read data from.
     */
    private final InputStream inputStream;

    /**
     * Character encoding of data.
     */
    @lombok.Builder.Default
    private final Charset charset=Charset.defaultCharset();

    /**
     * Destination of each line read.
     */
    private final Consumer<String> consumer;

    /**
     * Consumes all lines
     * @throws IOException Thrown in case of I/O error.
     */
    public void consumeAll() throws IOException {
        try (Reader reader=new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader=new BufferedReader(reader)) {
            bufferedReader.lines().forEach(consumer);
        }
    }

    @Override
    public void run() {
        try {
            consumeAll();
        } catch (IOException ex) {
            throw new IllegalStateException("Failure to gobble lines from input stream!",ex);
        }
    }

    @Override
    public void close() throws Exception {
        if (inputStream!=null) {
            inputStream.close();
        }
    }
}
