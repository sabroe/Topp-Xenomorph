package com.yelstream.topp.gradle.api;

import com.yelstream.topp.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.gradle.api.tasks.SourceSet;

import java.util.function.BiFunction;

/**
 * Utility addressing instances of {@link org.gradle.api.artifacts.Configuration}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-07
 */
@UtilityClass
public class Configurations {
    /**
     * Strategy for how to name configurations.
     */
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum NameStrategy {
        /**
         * Configuration name is the configuration name part.
         */
        Identity((configurationNamePart,sourceSet)->configurationNamePart),

        /**
         * Configuration name is {@code <configuration-name-part>}{@code <source-set-name>},
         * where a source set name {@code main} is mapped to the empty string and otherwise has its first letter capitalized.
         */
        ShortConvention((configurationNamePart,sourceSet)-> {
            String mappedSourceSetName=SourceSets.toShortName(sourceSet);
            return String.format("%s%s",configurationNamePart,Strings.capitalizeFirstLetter(mappedSourceSetName));
        });

        /**
         * Mapping of (configuration name part, source set) to a name.
         */
        private final BiFunction<String,SourceSet,String> nameMapper;

        /**
         * Creates a configuration name.
         * @param configurationNamePart Part of a configuration name, usually a prefix.
         * @param sourceSet Source set relative to which configurations are used and hence named.
         * @return Configuration name.
         */
        String toString(String configurationNamePart,
                        SourceSet sourceSet) {
            return nameMapper.apply(configurationNamePart,sourceSet);
        }
    }
}
