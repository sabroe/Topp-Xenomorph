package com.yelstream.topp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Future;

/**
 * Command to be invoked using specific arguments and with outputs to specific destination.
 * <p>
 * This is not intended for process invocation -- in which case a usage of {@link ProcessBuilder} is preferred --
 * but it could possibly hide a process invocation.
 * It is intended for the invocation of highly abstract commandlines.
 * </p>
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-29
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class Command implements Closeable {

    /**
     * Command arguments.
     */
    private Argument argument;

    /**
     * Command output, comparable to the standard output of a process.
     */
    private final OutputStream outputStream;

    /**
     * Command error output, comparable to the standard error output of a process.
     */
    private final OutputStream errorStream;

    /**
     * Handle to get a result.
     * This is likely to trigger actual command invocation too.
     */
    private final Future<Result> future;

    @Override
    public void close() throws IOException {
        if (outputStream!=null) {
            outputStream.close();
        }
        if (errorStream!=null) {
            errorStream.close();
        }
    }
}
