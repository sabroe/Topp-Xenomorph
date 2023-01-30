package com.yelstream.topp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * Creator of commands to be invoked.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-29
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class CommandInitiator {

    /**
     * Command arguments.
     */
    private final Argument argument;

    /**
     * Source of destinations for output data.
     */
    private final Supplier<OutputStream> outputStreamSupplier;

    /**
     * Source of destinations for error output data.
     */
    private final Supplier<OutputStream> errorStreamSupplier;

    /**
     * Command driver.
     */
    private final Driver<OutputStream, Exception> driver;

    /**
     * Status code verifier.
     */
    private final IntConsumer statusVerifier;

    /**
     * Command driver, low-level interface.
     */
    @FunctionalInterface
    public interface Driver<S extends OutputStream, E extends Exception> {
        int execute(List<String> arguments,
                    S outputStream,
                    S errorStream) throws E;
    }

    public Command start() {
        OutputStream outputStream=outputStreamSupplier.get();
        OutputStream errorStream=errorStreamSupplier.get();

        Command.Builder builder=Command.builder();
        builder.argument(argument).outputStream(outputStream).errorStream(errorStream);

        Future<Result> future=new RunnableFuture<>() {
            private Exception ex;
            private AtomicReference<Result> result=new AtomicReference<>();

            @Override
            public void run() {
                try {
                    int code= driver.execute(argument.getArguments(),outputStream,errorStream);

                    Result.Builder builder=Result.builder();

                    if (outputStream instanceof ByteArrayOutputStream byteArrayOutputStream) {
                        String outText=byteArrayOutputStream.toString(Charset.defaultCharset());
                        builder.outText(outText);
                    }
                    if (errorStream instanceof ByteArrayOutputStream byteArrayErrorStream) {
                        String errText=byteArrayErrorStream.toString(Charset.defaultCharset());
                        builder.errText(errText);
                    }

                    Status status=Status.builder().code(code).statusVerifier(statusVerifier).build();
                    builder.status(status);

                    Result result=builder.build();
                    this.result.set(result);

                    result.getStatus().verifyStatus();

                } catch (Exception ex) {
                    this.ex=ex;
                }
            }

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                result=null;
                return true;
            }

            @Override
            public boolean isCancelled() {
                return result==null;
            }

            @Override
            public boolean isDone() {
                return result.get()!=null;
            }

            @Override
            public Result get() throws InterruptedException, ExecutionException {
                if (result.get()==null) {
                    run();
                }
                if (ex!=null) {
                    throw new ExecutionException(ex);
                }
                return result.get();
            }

            @Override
            public Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };

        builder.future(future);
        return builder.build();
    }

    /**
     * Creates a command-initiator using a {@link PrintStream}-based driver as its offset.
     * @param argument Command arguments.
     * @param driver Command driver
     * @param statusVerifier  Status verifier.
     * @return Command-initiator.
     */
    public static CommandInitiator createCommandInitiator(Argument argument,
                                                          Driver<PrintStream,Exception> driver,
                                                          IntConsumer statusVerifier) {
        CommandInitiator.Builder builder=CommandInitiator.builder();
        builder.argument(argument);

        builder.outputStreamSupplier(ByteArrayOutputStream::new);
        builder.errorStreamSupplier(ByteArrayOutputStream::new);

        builder.driver((arg,outputStream,errorStream)->{
            try (PrintStream out=new PrintStream(outputStream,true,Charset.defaultCharset());
                 PrintStream err=new PrintStream(errorStream,true,Charset.defaultCharset())) {
                int status=driver.execute(arg,out,err);
                out.flush();
                err.flush();
                return status;
            }
        });

        builder.statusVerifier(statusVerifier);
        return builder.build();
    }
}
