package com.yelstream.topp.gradle.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.PathValidation;
import org.gradle.api.Project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-23
 */
@Getter
@RequiredArgsConstructor
public class ResourceFactory {

    private final Project project;

    private File defaultResourceDir;

    public File getResourceDir() {
        File dir;
        if (defaultResourceDir!=null) {
            dir=defaultResourceDir;
        } else {
            dir=project.file("src/main/resources");  //TODO: Get this from the Java plugin and the main sourceset!
        }
        return dir;
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
            Path path=getResourceDir().toPath().resolve(name);
            resolved=path.toAbsolutePath();
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
        File resolved;
        if (file.isAbsolute()) {
            resolved=file;
        } else {
            resolved=new File(getResourceDir(),file.getName());
        }
        return resolved;
    }

    /**
     * Resolves a path relative to the resource directory.
     * @param path Path to resolve.
     * @return Resolved path.
     */
    public Path resolve(Path path) {
        Path resolved;
        if (path.isAbsolute()) {
            resolved=path;
        } else {
            Path projectDir=getResourceDir().toPath();
            resolved=projectDir.resolve(path);
        }
        return resolved;
    }
}
