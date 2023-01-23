package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.sun.tools.xjc.Driver;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.SchemaGenExtension;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.XJCExtension;
import com.yelstream.topp.gradle.plugin.xenomorph.util.SchemaReference;
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
import java.util.ArrayList;
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
        Logger logger=project.getLogger();
        Tasks.logHello(this);

        File buildDir=project.getBuildDir();
        Path buildDirectoryPath=project.getBuildDir().toPath();



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
                Path generatedFilesDirectory=buildDirectoryPath.resolve(Paths.get(OUTPUT_DIRECTORY_NAME));
                Files.createDirectories(generatedFilesDirectory);

//                Path bindingFilesDirectory1=projectDir.resolve(Paths.get("src","main","resources","xjb","mapping.xjb"));
//                Path bindingFilesDirectory=projectDir.resolve(Paths.get("src","main","resources","xjb"));
                Path bindingFilesDirectory2=projectDir.resolve(Paths.get("src","main","resources","xjb","Graph-Exchange-XML/1.1/mapping.xjb"));
                Path bindingFilesDirectory3=projectDir.resolve(Paths.get("src","main","resources","xjb","Graph-Exchange-XML/1.2/mapping.xjb"));
                Path bindingFilesDirectory4=projectDir.resolve(Paths.get("src","main","resources","xjb","Graph-Exchange-XML/1.3/mapping.xjb"));
//                Path bindingFilesDirectory5=projectDir.resolve(Paths.get("src","main","resources","xjb","GraphML/mapping.xjb"));
//                Files.createDirectories(generatedFilesDirectory);

//                Path schemaFile1=projectDir.resolve(Paths.get("src","main","resources","xsd","pain.001.001.02.xsd"));
                Path schemaFile2=projectDir.resolve(Paths.get("src","main","resources","xsd","Graph-Exchange-XML/1.1/XSD/gexf.xsd"));
                Path schemaFile3=projectDir.resolve(Paths.get("src","main","resources","xsd","Graph-Exchange-XML/1.2/XSD/gexf.xsd"));
                Path schemaFile4=projectDir.resolve(Paths.get("src","main","resources","xsd","Graph-Exchange-XML/1.3/XSD/gexf.xsd"));
                Path schemaFile5=projectDir.resolve(Paths.get("src","main","resources","xsd","GraphML/graphml.xsd"));
//                Path schemaFile51=projectDir.resolve(Paths.get("src","main","resources","xsd","GraphML/graphml-parseinfo.xsd"));
//                Path schemaFile52=projectDir.resolve(Paths.get("src","main","resources","xsd","GraphML/graphml-attributes.xsd"));
//                Files.createDirectories(generatedFilesDirectory);

                Path catalogFile=projectDir.resolve(Paths.get("src","main","resources","cat","catalog.cat"));
if (!Files.exists(catalogFile)) {
    throw new IllegalStateException();
}

/*
    Add -Dxml.catalog.verbosity=999 as a command line option to Ant/Maven.
*/
/*
    -Djavax.xml.accessExternalSchema=all
    -Djavax.xml.accessExternalSchema=file,http
    //https://docs.oracle.com/javase/tutorial/jaxp/properties/properties.html
 */

/*
    DGML
    GraphML
    XGMML
    PhyloXML
    NeXML
*/

                List<String> args=
                    List.of(
                            "-d",generatedFilesDirectory.toAbsolutePath().toString(),
                            "-catalog",catalogFile.toAbsolutePath().toString(),
//                            "-verbose",

//                            schemaFile1.toAbsolutePath().toString(),
//                            schemaFile2.toAbsolutePath().toString(),
                            schemaFile3.toAbsolutePath().toString(),
//                            schemaFile4.toAbsolutePath().toString(),
//                            schemaFile5.toAbsolutePath().toString(),
//                            schemaFile51.toAbsolutePath().toString(),
//                            schemaFile52.toAbsolutePath().toString(),

//                            "-b",bindingFilesDirectory1.toAbsolutePath().toString(),
//                            "-b",bindingFilesDirectory2.toAbsolutePath().toString(),
                            "-b",bindingFilesDirectory3.toAbsolutePath().toString(),
//                            "-b",bindingFilesDirectory4.toAbsolutePath().toString(),
//                            "-b",bindingFilesDirectory5.toAbsolutePath().toString(),

                            "-mark-generated",
                            "-enableIntrospection"
                           );
//                run(args,null);

                //project.get
            }


            XJCExtension e=XJCExtension.get(project);
            List<XJCExtension.Run> runs=e.getRuns();

            if (runs==null || runs.isEmpty()) {
                logger.warn("Nothing to do!");
            } else {
                for (XJCExtension.Run run: runs) {
                    executeRun(run);
                }
            }

//            System.err.println("SchemaGenExtension: "+ SchemaGenExtension.get(project));

        } catch (Exception ex) {
            throw new TaskExecutionException(this,ex);
        }
    }

    private void executeRun(XJCExtension.Run run) throws Exception {
        Project project=getProject();
        Logger logger=project.getLogger();

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Run is named %s!",run.getName()),run);
        }

        XJCExtension.Run.Options options=run.getOptions();
        XJCExtension.Run.Extensions extensions=run.getExtensions();

        File buildDir=project.getBuildDir();
        Path buildDirectoryPath=project.getBuildDir().toPath();
        Path projectDir=project.getProjectDir().toPath();

        List<String> args=new ArrayList<>();

        Path generatedFilesDirectory=buildDirectoryPath.resolve(Paths.get(OUTPUT_DIRECTORY_NAME));

        args.add("-d");
        args.add(generatedFilesDirectory.toAbsolutePath().toString());

        if (options.getTargetPackage()!=null) {
            args.add("-p");
            args.add(options.getTargetPackage());
        }

        if (options.getCatalogFile()!=null) {
            args.add("-catalog");
            Path catalogPath=options.getCatalogFile().toPath();
            args.add(catalogPath.toAbsolutePath().toString());
        }

        if (run.getSourceSchema()!=null) {
            List<SchemaReference> schemas=run.getSourceSchema();
            for (SchemaReference schema: schemas) {
                String schemaText=schema.resolve(project);
                args.add(schemaText);
/*
                Path schemaFile=projectDir.resolve(Paths.get(schema));
                args.add(schemaFile.toAbsolutePath().toString());
*/
            }
        }

        if (options.getBindingFile()!=null) {
            List<File> bindingFiles=options.getBindingFile();
            for (File binding: bindingFiles) {
                Path bindingPath=binding.toPath();
                Path bindingFilesDirectory=projectDir.resolve(bindingPath);
                args.add("-b");
                args.add(bindingFilesDirectory.toAbsolutePath().toString());
            }
        }

        if (extensions.getMarkGenerated()!=Boolean.FALSE) {
            args.add("-mark-generated");
        }
        if (options.getEnableIntrospection()!=Boolean.FALSE) {
            args.add("-enableIntrospection");
        }

System.err.println("Run: "+args);
        run(args,null);
        if (run.getEnable()!=Boolean.FALSE) {
            run(args);
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


    /*
JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
Graphml graphml= (Graphml) jaxbUnmarshaller.unmarshal(xmlFile);
     */

}
