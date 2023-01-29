package com.yelstream.topp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * Result of command invocation.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-29
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class Result {

    /**
     * Output text from command invocation.
     */
    private final String outText;

    /**
     * Error output text from command invocation.
     */
    private final String errText;

    /**
     * Status code from command invocation.
     */
    private final Status status;

    /**
     * Exception as a result of command invocation.
     */
    private final Throwable exception;

    /**
     * Consumes a text message.
     * @param text Text from output or error output.
     * @param textConsumer Consumer of text message.
     */
    public static void consumeText(String text,
                                   Consumer<String> textConsumer) {
        if (text!=null && !text.isEmpty()) {
            textConsumer.accept(text);
        }
    }

    /**
     * Consumes the two output text messages.
     * @param outTextConsumer Consumer of output texts.
     * @param errTextConsumer Consumer of error output texts.
     */
    public void consumeText(Consumer<String> outTextConsumer,
                            Consumer<String> errTextConsumer) {
        consumeText(outText,outTextConsumer);
        consumeText(errText,errTextConsumer);
    }
}
