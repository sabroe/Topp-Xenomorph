package com.yelstream.topp.gradle.plugin.xenomorph.tool;

import com.sun.tools.xjc.Driver;
import com.yelstream.topp.command.Argument;
import com.yelstream.topp.command.Command;
import com.yelstream.topp.command.CommandInitiator;
import com.yelstream.topp.command.Result;
import lombok.experimental.UtilityClass;
import org.gradle.api.logging.Logger;

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



    public static int run(List<String> args,
                          PrintStream out,
                          PrintStream err) throws Exception {
        return Driver.run(args.toArray(new String[0]),out,err);
    }

    public static CommandInitiator createCommandInitiator(List<String> arguments,
                                                          IntConsumer statusConsumer) {
        Argument argument=new Argument(arguments);
        return CommandInitiator.createCommandInitiator(argument,XJCUtility::run,statusConsumer);
    }

    @SuppressWarnings("java:S106")
    public static void executeCommandToConsole(List<String> arguments,
                                               IntConsumer statusConsumer) throws Exception {
        CommandInitiator commandInitiator=XJCUtility.createCommandInitiator(arguments,statusConsumer);
        try (Command command=commandInitiator.start()) {
            Result result=command.getFuture().get();
            result.consumeText(System.out::print,System.err::print);
        }
    }

    public static void executeCommandToLogger(List<String> arguments,
                                              IntConsumer statusConsumer,
                                              Logger logger) throws Exception {
        CommandInitiator commandInitiator=XJCUtility.createCommandInitiator(arguments,statusConsumer);
        try (Command command=commandInitiator.start()) {
            Result result=command.getFuture().get();
            result.consumeText(
                outText->{
                    if (logger.isInfoEnabled()) {
                        logger.info(String.format("Informational is:%n%s",outText));
                    }
                },
                errText->{
                    if (logger.isErrorEnabled()) {
                        logger.error(String.format("Failure to run; error is:%n%s",errText));
                    }
                }
            );
        }
    }
}
