package com.yelstream.topp.gradle.plugin.xenomorph;

/*
import com.yelstream.topp.gradle.oggy.task.DecorateTask;
import com.yelstream.topp.gradle.oggy.util.OggyPluginUtility;
import com.yelstream.topp.gradle.oggy.task.extension.JsonSchemaPreprocessorExtension;
import com.yelstream.topp.gradle.oggy.task.CollectTask;
import com.yelstream.topp.gradle.oggy.task.GenerateTask;
import com.yelstream.topp.gradle.oggy.task.PrepareTask;
import com.yelstream.topp.gradle.oggy.task.StripTask;
import com.yelstream.topp.gradle.oggy.task.VisualizeTask;
import com.yelstream.topp.gradle.util.jsonschema2pojo.JsonSchema2PojoPluginUtility;
*/
import com.sun.tools.xjc.Driver;
import com.sun.tools.xjc.XJCFacade;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;

import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

/**
 * The "Xenomorph" Gradle plugin addressing Jakarta JAXB.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-11
 */
public class XenomorphPlugin implements Plugin<Project> {
    /*
    private TaskProvider<CollectTask> collectTaskProvider;
    private TaskProvider<DecorateTask> decorateTaskProvider;
    private TaskProvider<StripTask> stripTaskProvider;
    private TaskProvider<PrepareTask> prepareTaskProvider;
    private TaskProvider<GenerateTask> generateTaskProvider;
    private TaskProvider<VisualizeTask> visualizeTaskProvider;
    */

    @Override
    public void apply(Project project) {
        /*
        ExtensionContainer extensions=project.getExtensions();
        extensions.create(JsonSchemaPreprocessorExtension.EXTENSION_NAME,JsonSchemaPreprocessorExtension.class);

        TaskContainer tasks=project.getTasks();

        collectTaskProvider=tasks.register(CollectTask.TASK_NAME,CollectTask.class);
        decorateTaskProvider=tasks.register(DecorateTask.TASK_NAME,DecorateTask.class);
        stripTaskProvider=tasks.register(StripTask.TASK_NAME,StripTask.class);
        prepareTaskProvider=tasks.register(PrepareTask.TASK_NAME,PrepareTask.class);
        generateTaskProvider=tasks.register(GenerateTask.TASK_NAME,GenerateTask.class);
        visualizeTaskProvider=tasks.register(VisualizeTask.TASK_NAME,VisualizeTask.class);

        project.getPluginManager().withPlugin(JsonSchema2PojoPluginUtility.PLUGIN_NAME,appliedPlugin->{
            Task generateTask=tasks.getByName(JsonSchema2PojoPluginUtility.TASK_NAME);
            generateTask.dependsOn(prepareTaskProvider);
        });

        OggyPluginUtility.registerConfigurations(project);

        project.getPluginManager().withPlugin("java", appliedPlugin->{
            Jar jarTask=(Jar)project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);
//            x.exclude((FileTreeElement el) -> el.getFile().toPath().startsWith("${project.buildDir}/resources/main"));
//            x.from("${project.buildDir}/oggy/json-schema/normalized");
            File buildDir=project.getBuildDir();
            jarTask.exclude((FileTreeElement el)->el.getFile().toPath().startsWith(new File(buildDir,"resources/main").toPath()));
            jarTask.from(new File(buildDir,"oggy/json-schema/normalized"));
        });
        */


        project.getTasks().create("xjc", Exec.class, task -> {
//            task.setCommandLine("xjc", "-d", project.getProjectDir() + "/generated-sources", project.getProjectDir() + "/src/main/resources/schema.xsd");
//            List<String> args=List.of("-version");
//            List<String> args=List.of("-fullversion");
            List<String> args=List.of("-help");
            try {
                int status=Driver.run(args.toArray(new String[0]),System.out,null/*System.err*/);
                task.setIgnoreExitValue(true);
                task.setDidWork(true);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        project.getTasks().create("schemagen", Exec.class, task -> {
//            task.setCommandLine("schemagen", project.getProjectDir() + "/src/main/java/com/example/model/*.java");

//            List<String> args=List.of("-version");
            List<String> args=List.of("-fullversion");
            try {
                int status=Driver.run(args.toArray(new String[0]),System.out,null/*System.err*/);
                task.setIgnoreExitValue(true);
                task.setDidWork(true);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });


    }
}
