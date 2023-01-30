package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.grind.gradle.api.Projects;
import com.yelstream.topp.grind.gradle.api.Tasks;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
public abstract class XenTask extends DefaultTask {
    /**
     *
     */
    public static final String TASK_NAME="xen";

    /**
     *
     */
    public static final String DESCRIPTION="Collects JSON Schemas and overlay mappings.";

    /**
     *
     */
    public static final String GROUP_NAME="build";

    public static final String TASK_OUTPUT_DIRECTORY_NAME="xjc";

/*
    public static final String OUTPUT_DIRECTORY_NAME=String.format("%s/%s/%s",Tasks.PLUGIN_OUTPUT_DIRECTORY_NAME,Tasks.JSON_SCHEMA_DOMAIN_OUTPUT_DIRECTORY_NAME,TASK_OUTPUT_DIRECTORY_NAME);
*/

    private final Supplier<PluginContext> pluginContextSupplier;

    @Inject
    protected abstract FileOperations getFileOperations();

    @Inject
    protected abstract ObjectFactory getObjectFactory();

    @Inject
    protected abstract ProjectLayout getProjectLayout();

/*
    private final ConfigurableFileCollection inputSchemaFiles;
    @InputFiles
    public ConfigurableFileCollection getInputSchemaFiles() { return inputSchemaFiles; }
*/

/*
    private final DirectoryProperty outputDirectory;
    @OutputDirectory
    public DirectoryProperty getOutputDirectory() { return outputDirectory; }
*/

/*
    @Classpath
    public NamedDomainObjectProvider<Configuration> getSchemaReferenceConfiguration() { return schemaReferenceConfiguration; }
    private final NamedDomainObjectProvider<Configuration> schemaReferenceConfiguration;
*/

/*
    private final FileCollection compileClassPathMainJavaFileCollection;
    @Classpath
    public FileCollection getCompileClassPathMainJavaFileCollection() { return compileClassPathMainJavaFileCollection; }
*/

/*
    private ResourceLoaderContainer resourceLoaderContainer;
*/

    @Inject
    public XenTask(Supplier<PluginContext> pluginContextSupplier) {
        this.pluginContextSupplier=pluginContextSupplier;

        setDescription(DESCRIPTION);
        setGroup(GROUP_NAME);

/*
        inputSchemaFiles=getObjectFactory().fileCollection().from(Tasks.RESOURCES_DIRECTORY_NAME);
        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(OUTPUT_DIRECTORY_NAME));

        resourceLoaderContainer=new ResourceLoaderContainer(getProject());
        schemaReferenceConfiguration=resourceLoaderContainer.getSchemaReferenceConfiguration();
        compileClassPathMainJavaFileCollection=resourceLoaderContainer.getCompileClassPathMainJavaFileCollection();
*/
    }

    public static final Set<String> validTaskPropertyKeys=Set.of("help","version","fullversion");

    @TaskAction
    public void run() throws TaskExecutionException {
        Project project=getProject();
        Logger logger=project.getLogger();

        Tasks.logHello(this);

        Map<String,Object> projectProperties=Projects.getProjectProperties(project);
        Map<String,Object> taskProperties=Tasks.getTaskProperties(this);

        try {
            Tasks.verifyTaskPropertyKeys(this,validTaskPropertyKeys);

/*
            if (taskProperties.containsKey("help")) {
                List<String> args=List.of("-help");
                run(args,null);
            }

            if (taskProperties.containsKey("version")) {
                List<String> args=List.of("-version");
                run(args,null);
            }

            if (taskProperties.containsKey("fullversion")) {
                List<String> args=List.of("-fullversion");
                run(args,null);
            }
*/
        } catch (Exception ex) {
            throw new TaskExecutionException(this,ex);
        }
    }

/*
    private void run(List<String> args) throws Exception {
        IntConsumer statusConsumer= status -> {
            if (status!=0) {
                throw new IllegalStateException(String.format("Failure to show full version; return status from XJC is %d!",status));
            }
        };
        run(args,statusConsumer);
    }

    private void run(List<String> args,
                     IntConsumer statusConsumer) throws Exception {
        int status=Driver.run(args.toArray(new String[0]),System.out,System.err);
        if (statusConsumer!=null) {
            statusConsumer.accept(status);
        }
    }
*/
}
