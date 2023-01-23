package com.yelstream.topp.gradle.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.PathValidation;
import org.gradle.api.Project;

import java.io.File;
import java.net.URI;
import java.net.URL;
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
     * Resolves a file path relative to the project directory of this project.
     * @param reference The object to resolve as a File.
     * @return Resolved file.
     *         This is never {@code null}.
     */
    public File file(Object reference) {
        File resolved;
        if (reference==null) {
            throw new IllegalArgumentException("Failure to resolve path reference; reference is not set!");
        }
/*
        switch (reference) {
            case String name -> {
                File file=new File(getResourceDir(),name);
                resolved=file.getAbsoluteFile();
            }
            case File file -> {
                if (file.isAbsolute()) {
                    resolved=file;
                } else {
                    resolved=new File(getResourceDir(),file.getName());
                }
            }
            case Path path -> {
                if (path.isAbsolute()) {
                    resolved=path.toFile();
                } else {
                    Path projectDir=getResourceDir().toPath();
                    resolved=projectDir.resolve(path).toFile();
                }
            }
            default -> {
                throw new IllegalArgumentException(String.format("Failure to resolve path reference; cannot recognize path reference type %s!",reference.getClass().getName()));
            }
        }
 */
        if (reference instanceof String name) {
            File file=new File(getResourceDir(),name);
                resolved=file.getAbsoluteFile();
        } else {
            if (reference instanceof File file) {
                if (file.isAbsolute()) {
                    resolved=file;
                } else {
                    resolved=new File(getResourceDir(),file.getName());
                }
            } else {
                if (reference instanceof Path path) {
                    if (path.isAbsolute()) {
                        resolved=path.toFile();
                    } else {
                        Path projectDir=getResourceDir().toPath();
                        resolved=projectDir.resolve(path).toFile();
                    }
                } else {
                    throw new IllegalArgumentException(String.format("Failure to resolve path reference; cannot recognize path reference type %s!", reference.getClass().getName()));
                }
            }
        }
        return resolved;
    }

    /**
     * Resolves a file path relative to the project directory of this project.
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
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must not exist!",file));
                    }
                    break;
                }
                case EXISTS: {
                    if (!file.exists()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must exist!",file));
                    }
                    break;
                }
                case FILE: {
                    if (!file.isFile()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must be an existing file!",file));
                    }
                    break;
                }
                case DIRECTORY: {
                    if (!file.isDirectory()) {
                        throw new InvalidUserDataException(String.format("Failure to resolve path reference; path resolved as %s, but path must be an existing directory!",file));
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException(String.format("Failure to resolve path reference; cannot recognize validation value %s!",validation));
                }
            }
        }
        return file;
    }

    public Path path(Object reference) {
        return null;
    }

    public Path path(Object reference, PathValidation validation) throws InvalidUserDataException {
        return null;
    }

    public URI uri(Object name) {
        return null;
    }

    public URL url(Object name) {
        return null;
    }
}
