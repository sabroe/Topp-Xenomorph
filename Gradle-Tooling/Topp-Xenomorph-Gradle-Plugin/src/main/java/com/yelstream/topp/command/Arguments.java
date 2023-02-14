package com.yelstream.topp.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

/**
 * Command arguments.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-29
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class Arguments {

    /**
     * Arguments for command invocation.
     */
    @Singular
    private final List<String> arguments;
}
