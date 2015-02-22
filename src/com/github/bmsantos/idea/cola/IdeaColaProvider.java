package com.github.bmsantos.idea.cola;

import com.github.bmsantos.core.cola.provider.IColaProvider;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import static com.github.bmsantos.core.cola.utils.ColaUtils.isSet;
import static java.io.File.separator;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.codehaus.plexus.util.SelectorUtils.matchPath;

public class IdeaColaProvider implements IColaProvider {

    private String targetDirectory;
    private List<String> classpathElements = emptyList();
    private List<String> deltas = emptyList();
    private List<String> includes = emptyList();
    private List<String> excludes = emptyList();

    @Override
    public String getTargetDirectory() {
        return targetDirectory.endsWith(separator) ? targetDirectory : targetDirectory + separator;
    }

    @Override
    public URLClassLoader getTargetClassLoader() throws Exception {
        final List<URL> urls = new ArrayList<>();

        for (final String path : classpathElements) {
            urls.add(new File(path).toURI().toURL());
        }

        return new URLClassLoader(urls.toArray(new URL[urls.size()]), IdeaColaProvider.class.getClassLoader());
    }

    @Override
    public List<String> getTargetClasses() {
        return filter(deltas);
    }

    public void setTargetDirectory(final String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setClasspathElements(final List<String> classpathElements) {
        this.classpathElements = classpathElements;
    }

    public void setDeltas(final List<String> deltas) {
        this.deltas = deltas;
    }

    public void setIncludes(@NotNull final String includes) {
        this.includes = asList(includes.split(","));
    }

    public void setExcludes(@NotNull final String excludes) {
        this.excludes = asList(excludes.split(","));
    }

    private List<String> filter(final List<String> deltas) {
        final List<String> result = new ArrayList<>();
        for (final String path : deltas) {
            if (!isExcluded(path) && isIncluded(path)) {
                result.add(path);
            }
        }
        return result;
    }

    private boolean isIncluded(final String name) {
        if (!isSet(includes)) {
            return true;
        }

        for (final String filter : includes) {
            if (matchPath(filter.trim(), name, true)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExcluded(final String name) {
        for (final String filter : excludes) {
            if (matchPath(filter.trim(), name, true)) {
                return true;
            }
        }
        return false;
    }
}