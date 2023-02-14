package com.yelstream.topp.gradle.api;

import lombok.experimental.UtilityClass;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;
import java.util.List;

/**
 * Utility addressing instances of {@link PluginManager}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-11
 */
@UtilityClass
public class PluginManagers {


/*
    public static void addJavaSourceDirectories(Project project,
                                 SourceSet sourceSet,
                                 List<File> directories) {
        project.getPluginManager().withPlugin("java", appliedPlugin->{
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
            SourceSetContainer sourceSets=javaPluginExtension.getSourceSets();
            SourceSet sourceSet=sourceSets.getByName(sourceSetName);

//            String dir=new File(buildDir,OUTPUT_DIRECTORY_NAME).toString();
            String dir=new File(buildDir,String.format("Xenomorph/%s/java/src",this.getName())).toString();
            sourceSet.getJava().srcDir(dir);
        });

    }
*/

}
