package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.sun.tools.xjc.Driver;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.util.XenomorphPluginUtility;
import com.yelstream.topp.grind.gradle.api.Projects;
import com.yelstream.topp.grind.gradle.api.Tasks;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
public abstract class XJCTask extends DefaultTask {
    /**
     *
     */
    public static final String TASK_NAME="xjc";

    /**
     *
     */
    public static final String DESCRIPTION="Generates JAXB objects from XML Schema (W3C XML Schema/XML DTD/XML inside WSDL).";

    /**
     *
     */
    public static final String GROUP_NAME="build";

    public static final String TASK_OUTPUT_DIRECTORY_NAME="xjc";


    public static final String RESOURCES_DIRECTORY_NAME="src/main/resources";

    public static final String PLUGIN_OUTPUT_DIRECTORY_NAME=XenomorphPluginUtility.PLUGIN_NAME;

    /**
     *
     */
    public static final String JAKARTA_JAXB_DOMAIN_OUTPUT_DIRECTORY_NAME="jakarta-jaxb";

    /**
     *
     */
    public static final String JACKSON_DOMAIN_OUTPUT_DIRECTORY_NAME="jackson-pojo";

    /**
     *
     */
    public static final String REPORT_DOMAIN_OUTPUT_DIRECTORY_NAME="report";


    public static final String OUTPUT_DIRECTORY_NAME=
        String.format("%s/%s/%s/%s",
                      PLUGIN_OUTPUT_DIRECTORY_NAME,
                      JAKARTA_JAXB_DOMAIN_OUTPUT_DIRECTORY_NAME,
                      TASK_OUTPUT_DIRECTORY_NAME,
                      "java/src");

    private Supplier<PluginContext> pluginContextSupplier;

    @Inject
    protected abstract FileOperations getFileOperations();

    @Inject
    protected abstract ObjectFactory getObjectFactory();

    @Inject
    protected abstract ProjectLayout getProjectLayout();

    private final ConfigurableFileCollection inputSchemaFiles;
    @InputFiles
    public ConfigurableFileCollection getInputSchemaFiles() { return inputSchemaFiles; }

    private final DirectoryProperty outputDirectory;
    @OutputDirectory
    public DirectoryProperty getOutputDirectory() { return outputDirectory; }

/*
    @Classpath
    public NamedDomainObjectProvider<Configuration> getSchemaReferenceConfiguration() { return schemaReferenceConfiguration; }
    private final NamedDomainObjectProvider<Configuration> schemaReferenceConfiguration;
*/

    private final FileCollection compileClassPathMainJavaFileCollection;
    @Classpath
    public FileCollection getCompileClassPathMainJavaFileCollection() { return compileClassPathMainJavaFileCollection; }

    private ResourceLoaderContainer resourceLoaderContainer;

    @Inject
    public XJCTask(Supplier<PluginContext> pluginContextSupplier) {
        this.pluginContextSupplier=pluginContextSupplier;

        setDescription(DESCRIPTION);
        setGroup(GROUP_NAME);

        Project project=getProject();
        File buildDir=project.getBuildDir();

        inputSchemaFiles=getObjectFactory().fileCollection().from(RESOURCES_DIRECTORY_NAME);
        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(OUTPUT_DIRECTORY_NAME));

        resourceLoaderContainer=new ResourceLoaderContainer(getProject(),pluginContextSupplier.get().getPluginConfigurations());
//        schemaReferenceConfiguration=resourceLoaderContainer.getSchemaReferenceConfiguration();
        compileClassPathMainJavaFileCollection=resourceLoaderContainer.getCompileClassPathMainJavaFileCollection();


        project.getPluginManager().withPlugin("java", appliedPlugin->{
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
            SourceSetContainer javaSourceSets=javaPluginExtension.getSourceSets();
            SourceSet mainJavaSourceSet=javaSourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);

            String dir=new File(buildDir,OUTPUT_DIRECTORY_NAME).toString();
            mainJavaSourceSet.getJava().srcDir(dir);
        });
    }

    public static final Set<String> validTaskPropertyKeys=Set.of("help","version","fullversion");

    @TaskAction
    public void run() throws TaskExecutionException {
        Project project=getProject();
        File buildDir=project.getBuildDir();
        Path buildDirectoryPath=project.getBuildDir().toPath();

        Logger logger=project.getLogger();
        Tasks.logHello(this);


        Map<String,Object> projectProperties=Projects.getProjectProperties(project);
        Map<String,Object> taskProperties=Tasks.getTaskProperties(this);

        try {
            Tasks.verifyTaskPropertyKeys(this,validTaskPropertyKeys);

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


            {
//                File rootDir=project.getRootDir();
                Path projectDir=project.getProjectDir().toPath();
/*
//                Path output=projectDir.resolve(Paths.get("build","xenomorph","xxx"));
                Path output=projectDir.resolve(Paths.get("build","xenomorph","json-schema","xjc"));
//                Path output=projectDir.resolve(Paths.get("src","main","java"));
                Path d=Files.createDirectories(output);
                Path a=d.toAbsolutePath();
                System.out.println(a);

//                Path generatedFilesDirectory=projectDir.resolve(Paths.get("build/xenomorph/xxx"));
//                Path generatedFilesDirectory=projectDir.resolve(Paths.get("src/main/java"));
*/
                Path generatedFilesDirectory=buildDirectoryPath.resolve(Paths.get(OUTPUT_DIRECTORY_NAME));
                Files.createDirectories(generatedFilesDirectory);

                Path bindingFilesDirectory1=projectDir.resolve(Paths.get("src","main","resources","xjb","mapping.xjb"));
//                Path bindingFilesDirectory=projectDir.resolve(Paths.get("src","main","resources","xjb"));
                Path bindingFilesDirectory2=projectDir.resolve(Paths.get("src","main","resources","xjb","Graph-Exchange-XML/1.1/mapping.xjb"));
                Path bindingFilesDirectory3=projectDir.resolve(Paths.get("src","main","resources","xjb","Graph-Exchange-XML/1.2/mapping.xjb"));
                Path bindingFilesDirectory4=projectDir.resolve(Paths.get("src","main","resources","xjb","Graph-Exchange-XML/1.3/mapping.xjb"));
//                Files.createDirectories(generatedFilesDirectory);

                Path schemaFile1=projectDir.resolve(Paths.get("src","main","resources","xsd","pain.001.001.02.xsd"));
                Path schemaFile2=projectDir.resolve(Paths.get("src","main","resources","xsd","Graph-Exchange-XML/1.1/XSD/gexf.xsd"));
                Path schemaFile3=projectDir.resolve(Paths.get("src","main","resources","xsd","Graph-Exchange-XML/1.2/XSD/gexf.xsd"));
                Path schemaFile4=projectDir.resolve(Paths.get("src","main","resources","xsd","Graph-Exchange-XML/1.3/XSD/gexf.xsd"));
//                Files.createDirectories(generatedFilesDirectory);

                Path catalogFile=projectDir.resolve(Paths.get("src","main","resources","cat","catalog.cat"));
if (!Files.exists(catalogFile)) {
    throw new IllegalStateException();
}

/*
                Add -Dxml.catalog.verbosity=999 as a command line option to Ant/Maven.
*/

                List<String> args=
                    List.of(
                            "-d",generatedFilesDirectory.toAbsolutePath().toString(),
//                            "-catalog",catalogFile.toAbsolutePath().toString(),
//                            "-verbose",

                            schemaFile1.toAbsolutePath().toString(),
//                            schemaFile2.toAbsolutePath().toString(),
                            schemaFile3.toAbsolutePath().toString(),
//                            schemaFile4.toAbsolutePath().toString(),

                            "-b",bindingFilesDirectory1.toAbsolutePath().toString(),
//                            "-b",bindingFilesDirectory2.toAbsolutePath().toString(),
                            "-b",bindingFilesDirectory3.toAbsolutePath().toString(),
//                            "-b",bindingFilesDirectory4.toAbsolutePath().toString(),

                            "-mark-generated",
                            "-enableIntrospection"
                           );
                run(args,null);

                //project.get
            }



        } catch (Exception ex) {
            throw new TaskExecutionException(this,ex);
        }
    }

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



//     ./bin/xjc.bat -b mapping.xjb -mark-generated -enableIntrospection pain.001.001.02.xsd


}
