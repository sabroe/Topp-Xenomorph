package com.yelstream.topp.gradle.api;

import com.yelstream.topp.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.gradle.api.tasks.SourceSet;

import java.util.function.BiFunction;

/**
 * Utility addressing instances of {@link SourceSet}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-07
 */
@UtilityClass
public class SourceSets {
    /**
     * Gets a "short" variation of the name of a source set.
     * This in particular maps source set "main" to the empty string "" while all other source sets like "test"
     * are returned as-is.
     * @param sourceSet Source set.
     * @return Short name
     */
    public static String toShortName(SourceSet sourceSet) {
        String name=sourceSet.getName();
        return isMain(name)?"":name;
    }

    public static boolean isMain(SourceSet sourceSet) {
        return isMain(sourceSet.getName());
    }

    public static boolean isMain(String sourceSetName) {
        return SourceSet.MAIN_SOURCE_SET_NAME.equals(sourceSetName);
    }

    /**
     * Strategy for how to name something related to the naming of a source set.
     * This may be a configuration or an extension.
     */
    @AllArgsConstructor
    @SuppressWarnings("java:S115")
    public enum NameStrategy {  //TODO: Consider this, naming and placement!
        /**
         * Configuration name is the configuration name part.
         */
        Identity((namePart,nameSuffix)->namePart),

        /**
         * Name is {@code <name-part>}{@code <source-set-name>},
         * where a source set name {@code main} is mapped to the empty string and otherwise has its first letter capitalized.
         */
        ShortConvention((namePart,nameSuffix)-> {
            return String.format("%s%s",namePart,Strings.capitalizeFirstLetter(nameSuffix));
        });

        /**
         * Mapping of (configuration name part, source set) to a name.
         */
        private final BiFunction<String,String,String> nameMapper;

        /**
         * Creates a configuration name.
         * @param namePart Part of a name, usually a prefix.
         * @param sourceSet Source set relative to which name has relevance.
         * @return Configuration name.
         */
        public String toString(String namePart,
                               SourceSet sourceSet) {
            return toString(namePart,sourceSet.getName());
        }

        /**
         * Creates a configuration name.
         * @param namePart Part of a name, usually a prefix.
         * @param nameSuffix Name suffix.
         * @return Configuration name.
         */
        public String toString(String namePart,
                               String nameSuffix) {
            return nameMapper.apply(namePart,nameSuffix);
        }
    }
}
