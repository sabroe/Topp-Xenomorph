package com.yelstream.topp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.function.IntConsumer;

/**
 * Status code as a result of command invocation.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-29
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class Status {

    /**
     * Status code.
     */
    private final int code;

    /**
     * Verifier of expected status codes.
     * This may trigger an exception being thrown.
     */
    @lombok.Builder.Default
    private final IntConsumer statusVerifier=DEFAULT_STATUS_VERIFIER;

    /**
     * Verifier of status codes which performs no check and has no action.
     */
    @SuppressWarnings("java:S1117")
    public static final IntConsumer EMPTY_STATUS_VERIFIER=code->{
    };

    /**
     * Verifier of status codes which performs a strict check expecting to see {@code 0} for success.
     */
    @SuppressWarnings("java:S1117")
    public static final IntConsumer STRICT_CODE_VERIFIER=code->{
        if (code!=0) {
            throw new IllegalStateException(String.format("Failure to verify status code; expected status code is 0, actual status code is %d!",code));
        }
    };

    /**
     * Default verifier of status codes.
     */
    public static final IntConsumer DEFAULT_STATUS_VERIFIER=STRICT_CODE_VERIFIER;

    /**
     * Verifies the actual status code.
     */
    public void verifyStatus() {
        if (statusVerifier!=null) {
            statusVerifier.accept(code);
        }
    }
}
