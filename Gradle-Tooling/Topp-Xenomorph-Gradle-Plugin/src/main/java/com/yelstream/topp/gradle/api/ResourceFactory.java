package com.yelstream.topp.gradle.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Singular;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.PathValidation;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.SourceSet;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

/**
 * Resolves file and path objects.
 * Instances of this should be exposed to Gradle scripts.
 * <p>
 *     Note that field-specific getters or setters must be kept hidden from Gradle scripts and per design.
 * </p>
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-23
 */
@AllArgsConstructor
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class ResourceFactory {

    @Getter(AccessLevel.PROTECTED)
    private final Project project;

    @Getter(AccessLevel.PROTECTED)
    @Singular
    private final List<Resolver> resolvers;

    @lombok.Builder.Default
    private final boolean failOnUnresolved=true;

    @lombok.Builder.Default
    private final Logger logger=null;

    @Getter(AccessLevel.PROTECTED)
    @lombok.Builder.Default
    private final LogLevel level=LogLevel.INFO;

    @Getter
    private File defaultResourceDir;

    protected Logger getLogger() {
        return logger!=null?logger:project.getLogger();
    }

    @FunctionalInterface
    public interface Resolver {
        File resolve(ResourceFactory resourceFactory,
                     File file);

        default Path resolve(ResourceFactory resourceFactory,
                             Path path) {
            return resolve(resourceFactory,path.toFile()).toPath();
        }
    }

    @AllArgsConstructor(staticName="of")
    public static class ProjectResolver implements Resolver {
        @Getter
        private final Project project;

        @Override
        public File resolve(ResourceFactory resourceFactory,
                            File file) {
            File resolved=null;
            {
                Logger logger=resourceFactory.getLogger();
                LogLevel level=resourceFactory.level;
                File projectDir=project.getProjectDir();
                File candicateFile=new File(projectDir,file.getPath());
                if (logger.isEnabled(level)) {
                    logger.log(level,String.format("Trying to resolve file against project; file is %s, candidate file is %s!",file,candicateFile));
                }
                if (candicateFile.exists()) {
                    resolved=candicateFile;
                }
            }
            return resolved;
        }
    }

    @AllArgsConstructor(staticName="of")
    public static class SourceSetResolver implements Resolver {
        @Getter
        private final SourceSet sourceSet;

        @Override
        public File resolve(ResourceFactory resourceFactory,
                            File file) {
            File resolved=null;
            {
                Logger logger=resourceFactory.getLogger();
                LogLevel level=resourceFactory.level;
                SourceDirectorySet resources=sourceSet.getResources();
                Set<File> dirs=resources.getSrcDirs();
                for (File dir: dirs) {
                    File candicateFile=new File(dir,file.getPath());
                    if (logger.isEnabled(level)) {
                        logger.log(level,String.format("Trying to resolve file against source-set; file is %s, source-set is %s, candidate file is %s!",file,sourceSet,candicateFile));
                    }
                    if (candicateFile.exists()) {
                        resolved=candicateFile;
                        break;
                    }
                }
            }
            return resolved;
        }
    }

    @AllArgsConstructor(staticName="of")
    public static class ResourceDirectoryResolver implements Resolver {
        @Getter
        private final File resourceDirectory;

        @Override
        public File resolve(ResourceFactory resourceFactory,
                            File file) {
            File resolved=null;
            {
                Logger logger=resourceFactory.getLogger();
                LogLevel level=resourceFactory.level;
                File candicateFile=new File(resourceDirectory,file.getPath());
                if (logger.isEnabled(level)) {
                    logger.log(level,String.format("Trying to resolve file against resource directory; file is %s, resource directory is %s, candidate file is %s!",file,resourceDirectory,candicateFile));
                }
                if (candicateFile.exists()) {
                    resolved=candicateFile;
                }
            }
            return resolved;
        }
    }

    @AllArgsConstructor(staticName="of")
    public static class DefaultResourceDirectoryResolver implements Resolver {
        @Override
        public File resolve(ResourceFactory resourceFactory,
                            File file) {
            File resolved=null;
            {
                Logger logger=resourceFactory.getLogger();
                LogLevel level=resourceFactory.level;
                File resourceDirectory=resourceFactory.getDefaultResourceDir();

                if (resourceDirectory!=null) {
                    File candicateFile=new File(resourceDirectory,file.getPath());
                    if (logger.isEnabled(level)) {
                        logger.log(level,String.format("Trying to resolve file against default resource directory; file is %s, default resource directory is %s, candidate file is %s!",file,resourceDirectory,candicateFile));
                    }
                    if (candicateFile.exists()) {
                        resolved=candicateFile;
                    }
                }
            }
            return resolved;
        }
    }

    @AllArgsConstructor(staticName="of")
    public static class DivergentResourceResolver implements Resolver {
        @Getter
        private final SourceSet sourceSet;

        @Getter
        private final File subSourceSetDirectory;

        private File getDivergentResourceDirectory(ResourceFactory resourceFactory) {
            File projectDir=resourceFactory.getProject().getProjectDir();
            File offsetDirectory=new File(projectDir,String.format("src/%s",sourceSet.getName()));
            return new File(offsetDirectory,subSourceSetDirectory.getPath());
        }

        @Override
        public File resolve(ResourceFactory resourceFactory,
                            File file) {
            File resolved=null;
            {
                Logger logger=resourceFactory.getLogger();
                LogLevel level=resourceFactory.level;
                File divergentResourceDirectory=getDivergentResourceDirectory(resourceFactory);
                File candicateFile=new File(divergentResourceDirectory,file.getPath());
                if (logger.isEnabled(level)) {
                    logger.log(level,String.format("Trying to resolve file against divergent resource directory; file is %s, divergent resource directory is %s, candidate file is %s!",file,divergentResourceDirectory,candicateFile));
                }
                if (candicateFile.exists()) {
                    resolved=candicateFile;
                }
            }
            return resolved;
        }
    }

    /**
     * Resolves a file path relative to the resource directory.
     * @param reference The object to resolve as a file.
     * @return Resolved file.
     *         This is never {@code null}.
     */
    public File file(Object reference) {
        File resolved;
        if (reference==null) {
            throw new IllegalArgumentException("Failure to resolve path reference; reference is not set!");
        }
        if (reference instanceof String name) {
            File file=new File(name);
            resolved=resolve(file);
        } else {
            if (reference instanceof File file) {
                resolved=resolve(file);
            } else {
                if (reference instanceof Path path) {
                    resolved=resolve(path).toFile();
                } else {
                    throw new IllegalArgumentException(String.format("Failure to resolve path reference; cannot recognize path reference type %s!",reference.getClass().getName()));
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves a file path relative to the resource directory.
     * @param reference The object to resolve as a file.
     * @param validation Path validation rule.
     * @return Resolved file.
     *         This is never {@code null}.
     * @throws InvalidUserDataException Thrown in case of invalid user data.
     */
    public File file(Object reference, PathValidation validation) throws InvalidUserDataException {
        File file=file(reference);
        if (validation!=null) {
            switch (validation) {
                case NONE: {
                    if (file.exists()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve file reference; path resolved as %s, but path must not exist!",file));
                    }
                    break;
                }
                case EXISTS: {
                    if (!file.exists()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve file reference; path resolved as %s, but path must exist!",file));
                    }
                    break;
                }
                case FILE: {
                    if (!file.isFile()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve file reference; path resolved as %s, but path must be an existing file!",file));
                    }
                    break;
                }
                case DIRECTORY: {
                    if (!file.isDirectory()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve file reference; path resolved as %s, but path must be an existing directory!",file));
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("Failure to resolve file reference; cannot recognize validation value %s!",validation));
                }
            }
        }
        return file;
    }

    /**
     * Resolves a file path relative to the resource directory.
     * @param reference The object to resolve as a file.
     * @return Resolved file.
     *         This is never {@code null}.
     */
    public Path path(Object reference) {
        Path resolved;
        if (reference==null) {
            throw new IllegalArgumentException("Failure to resolve path reference; reference is not set!");
        }
        if (reference instanceof String name) {
            Path path=Paths.get(name);
            resolved=resolve(path);
        } else {
            if (reference instanceof File file) {
                resolved=resolve(file).toPath();
            } else {
                if (reference instanceof Path path) {
                    resolved=resolve(path);
                } else {
                    throw new IllegalArgumentException(String.format("Failure to resolve path reference; cannot recognize path reference type %s!",reference.getClass().getName()));
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves a file path relative to the resource directory.
     * @param reference The object to resolve as a file.
     * @param validation Path validation rule.
     * @return Resolved file.
     *         This is never {@code null}.
     * @throws InvalidUserDataException Thrown in case of invalid user data.
     */
    public Path path(Object reference, PathValidation validation) throws InvalidUserDataException {
        Path path=path(reference);
        if (validation!=null) {
            switch (validation) {
                case NONE: {
                    if (Files.exists(path)) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must not exist!",path));
                    }
                    break;
                }
                case EXISTS: {
                    if (!Files.exists(path)) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must exist!",path));
                    }
                    break;
                }
                case FILE: {
                    if (!Files.isRegularFile(path)) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must be an existing file!",path));
                    }
                    break;
                }
                case DIRECTORY: {
                    if (!Files.isDirectory(path)) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must be an existing directory!",path));
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("Failure to resolve path reference; cannot recognize validation value %s!",validation));
                }
            }
        }
        return path;
    }

    /**
     * Resolves a reference relative to the resource directory as a URI.
     * @param reference The object to resolve as a file.
     * @return Resolved reference as a URI.
     *         This is never {@code null}.
     */
    public URI uri(Object reference) {
        URI resolved;
        if (reference==null) {
            throw new IllegalArgumentException("Failure to resolve URI; name is not set!");
        }
        if (reference instanceof String name) {
            resolved=URI.create(name);
        } else {
            if (reference instanceof File file) {
                resolved=resolve(file).toURI();
            } else {
                if (reference instanceof Path path) {
                    resolved=resolve(path).toUri();
                } else {
                    throw new IllegalArgumentException(String.format("Failure to resolve URI reference; cannot recognize path reference type %s!", reference.getClass().getName()));
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves a reference relative to the resource directory as a URL.
     * @param reference The object to resolve as a file.
     * @return Resolved reference as a URL.
     *         This is never {@code null}.
     * @throws MalformedURLException Thrown in case of URL being malformed.
     */
    public URL url(Object reference) throws MalformedURLException {
        URL resolved;
        if (reference==null) {
            throw new IllegalArgumentException("Failure to resolve URI; name is not set!");
        }
        if (reference instanceof String name) {
            resolved=new URL(name);
        } else {
            if (reference instanceof File file) {
                resolved=resolve(file).toPath().toUri().toURL();
            } else {
                if (reference instanceof Path path) {
                    resolved=resolve(path).toUri().toURL();
                } else {
                    throw new IllegalArgumentException(String.format("Failure to resolve URL reference; cannot recognize path reference type %s!", reference.getClass().getName()));
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves a file relative to the resource directory.
     * @param file File to resolve.
     * @return Resolved file.
     */
    public File resolve(File file) {
        File resolved=null;
        if (file.isAbsolute()) {
            resolved=file;
        } else {
            if (resolvers==null) {
                throw new IllegalStateException(String.format("Failure to resolve file; no resolvers are present, file is %s!",file));
            } else {
                for (Resolver resolver: resolvers) {
                    resolved=resolver.resolve(this,file);
                    if (resolved!=null) {
                        break;
                    }
                }
            }
            if (failOnUnresolved) {
                if (resolved==null) {
                    throw new IllegalStateException(String.format("Failure to resolve file; resolvers not able to resolve file, file is %s!",file));
                }
                if (!resolved.isAbsolute()) {
                    throw new IllegalStateException(String.format("Failure to resolve file; resolved file is not absolute, file is %s, resolved file is %s!",file,resolved));
                }
                if (!resolved.exists()) {
                    throw new IllegalStateException(String.format("Failure to resolve file; resolved file does not exist, file is %s, resolved file is %s!",file,resolved));
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves a path relative to the resource directory.
     * @param path Path to resolve.
     * @return Resolved path.
     */
    public Path resolve(Path path) {
        Path resolved=null;
        if (path.isAbsolute()) {
            resolved=path;
        } else {
            if (resolvers==null) {
                throw new IllegalStateException(String.format("Failure to resolve path; no resolvers are present, path is %s!",path));
            } else {
                for (Resolver resolver: resolvers) {
                    resolved=resolver.resolve(this,path);
                    if (resolved!=null) {
                        break;
                    }
                }
            }
            if (failOnUnresolved) {
                if (resolved==null) {
                    throw new IllegalStateException(String.format("Failure to resolve path; resolvers not able to resolve path, path is %s!",path));
                }
                if (!resolved.isAbsolute()) {
                    throw new IllegalStateException(String.format("Failure to resolve path; resolved file is not absolute, path is %s, resolved path is %s!",path,resolved));
                }
                if (Files.notExists(path)) {
                    throw new IllegalStateException(String.format("Failure to resolve path; resolved file does not exist, path is %s, resolved path is %s!",path,resolved));
                }
            }
        }
        return resolved;
    }


    public static ResourceFactory of(Project project) {
        Builder builder=ResourceFactory.builder();
        builder.project(project);
        builder.resolver(ResourceFactory.DefaultResourceDirectoryResolver.of());
        builder.resolver(ResourceFactory.ProjectResolver.of(project));
        builder.failOnUnresolved(false);
        return builder.build();
    }

    public static ResourceFactory of(Project project,
                                     SourceSet sourceSet) {
        Builder builder=ResourceFactory.builder();
        builder.project(project);
        builder.resolver(ResourceFactory.DefaultResourceDirectoryResolver.of());
        builder.resolver(ResourceFactory.SourceSetResolver.of(sourceSet));
        builder.resolver(ResourceFactory.ProjectResolver.of(project));
        builder.failOnUnresolved(false);
        return builder.build();
    }
}
