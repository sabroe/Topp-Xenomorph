package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.grind.gradle.api.Tasks;
import lombok.Getter;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import javax.inject.Inject;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
public abstract class SchemaGenTask extends DefaultTask {
    /**
     *
     */
    public static final String TASK_NAME="schemaGen";

    /**
     *
     */
    public static final String DESCRIPTION="Collects JSON Schemas and overlay mappings.";

    /**
     *
     */
    public static final String GROUP_NAME="build";

    public static final String TASK_OUTPUT_DIRECTORY_NAME="schemaGen";

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

    @Getter(onMethod_={@Classpath})
    private final NamedDomainObjectProvider<Configuration> jxcDependencies;


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
    public SchemaGenTask(Supplier<PluginContext> pluginContextSupplier) {
        this.pluginContextSupplier=pluginContextSupplier;

        setDescription(DESCRIPTION);
        setGroup(GROUP_NAME);

        jxcDependencies=pluginContextSupplier.get().getPluginConfigurations().getJxcConfigurationProvider();

/*
        inputSchemaFiles=getObjectFactory().fileCollection().from(Tasks.RESOURCES_DIRECTORY_NAME);
        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(OUTPUT_DIRECTORY_NAME));

        resourceLoaderContainer=new ResourceLoaderContainer(getProject());
        schemaReferenceConfiguration=resourceLoaderContainer.getSchemaReferenceConfiguration();
        compileClassPathMainJavaFileCollection=resourceLoaderContainer.getCompileClassPathMainJavaFileCollection();
*/
    }

    @TaskAction
    public void run() throws TaskExecutionException {
        Project project=getProject();
        Logger logger=project.getLogger();

        Tasks.logHello(this);

/*
        try {
            FileOperations fileOperations=getFileOperations();
            fileOperations.delete(this.getOutputDirectory());
            File outputDirectoryFile=fileOperations.mkdir(this.getOutputDirectory());

            Foundation foundation=Tasks.createFoundation(project,resourceLoaderContainer);

//TODO: Create method for this "create report" action!

            File reportFile=new File(outputDirectoryFile,"report.txt");
            try (OutputStream out=new FileOutputStream(reportFile);
                 PrintStream pout=new PrintStream(out,false, StandardCharsets.UTF_8)) {

                CollectWalker walker = new CollectWalker(foundation);
                CollectWalker.Context context = CollectWalker.Context.builder().destinationDir(outputDirectoryFile).reportPrinter(pout).build();
                walker.walk(context);

                pout.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new TaskExecutionException(this, ex);
        }
*/

/*
        project.getTasks().create("schemagen", Exec.class, task -> {
//            task.setCommandLine("schemagen", project.getProjectDir() + "/src/main/java/com/example/model/*.java");

//            List<String> args=List.of("-version");
            List<String> args=List.of("-fullversion");
            try {
                int status=Driver.run(args.toArray(new String[0]),System.out,null*/
        /*System.err*//*
);
                task.setIgnoreExitValue(true);
                task.setDidWork(true);
            } catch (Throwable e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
*/

    }
}
