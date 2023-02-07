package com.yelstream.topp.gradle.api;

import lombok.experimental.UtilityClass;
import org.gradle.api.tasks.SourceSet;

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
}
