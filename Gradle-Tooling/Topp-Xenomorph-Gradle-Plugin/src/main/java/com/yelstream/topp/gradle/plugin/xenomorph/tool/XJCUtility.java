package com.yelstream.topp.gradle.plugin.xenomorph.tool;

import com.sun.tools.xjc.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.io.PrintStream;
import java.util.List;
import java.util.function.IntConsumer;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-25
 */
@UtilityClass
public class XJCUtility {

    @Getter
    @AllArgsConstructor
    @Builder(builderClassName="Builder",toBuilder=true)
    public static class CommandContext {

        private static final IntConsumer DEFAULT_STATUS_CONSUMER=status -> {
            if (status!=0) {
                throw new IllegalStateException(String.format("Failure to show full version; return status from XJC is %d!",status));
            }
        };

        @SuppressWarnings("java:S106")
        @lombok.Builder.Default
        private final PrintStream out=System.out;

        @SuppressWarnings("java:S106")
        @lombok.Builder.Default
        private final PrintStream err=System.err;

        @lombok.Builder.Default
        private final IntConsumer statusConsumer=DEFAULT_STATUS_CONSUMER;
    }

    public static void run(CommandContext commandContext,
                           List<String> args) throws Exception {
        run(commandContext,args,commandContext.getStatusConsumer());
    }

    public static void run(CommandContext commandContext,
                           List<String> args,
                           IntConsumer statusConsumer) throws Exception {
        PrintStream out=commandContext.getOut();
        PrintStream err=commandContext.getErr();
        int status=Driver.run(args.toArray(new String[0]),out,err);
        if (statusConsumer!=null) {
            statusConsumer.accept(status);
        }
    }
}
