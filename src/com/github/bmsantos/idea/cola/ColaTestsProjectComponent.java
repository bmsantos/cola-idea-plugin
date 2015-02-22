package com.github.bmsantos.idea.cola;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ColaTestsProjectComponent implements ProjectComponent {
    private final Project project;

    protected ColaCompilerListener listener;

    public ColaTestsProjectComponent(Project project) {
        this.project = project;
    }

    public void initComponent() {
        // empty
    }

    public void disposeComponent() {
        // empty
    }

    @NotNull
    public String getComponentName() {
        return "ColaTestsProjectComponent";
    }

    public void projectOpened() {
        listener = getColaCompilerListener();
        CompilerManager.getInstance(project).addCompilationStatusListener(listener);
    }

    public void projectClosed() {
        CompilerManager.getInstance(project).removeCompilationStatusListener(listener);
    }

    protected ColaCompilerListener getColaCompilerListener() {
        return new ColaCompilerListener(project);
    }
}
