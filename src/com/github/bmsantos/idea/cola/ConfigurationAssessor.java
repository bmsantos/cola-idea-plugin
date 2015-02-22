package com.github.bmsantos.idea.cola;

import com.intellij.ide.DataManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;

public class ConfigurationAssessor {
    private static final String INCLUDES = "com.github.bmsantos.idea.cola.includes";
    private static final String EXCLUDES = "com.github.bmsantos.idea.cola.excludes";
    private static final String BASE_CLASS = "com.github.bmsantos.idea.cola.baseClass";
    private static final String TEST_METHOD = "com.github.bmsantos.idea.cola.testMethod";
    private static final String ENABLED = "com.github.bmsantos.idea.cola.enabled";

    private final PropertiesComponent properties;

    public ConfigurationAssessor(final Project project) {
        properties = PropertiesComponent.getInstance(getProject(project));
    }

    public String getIncludeFilter() {
        return properties.getValue(INCLUDES, "");
    }

    public void setIncludeFilter(final String value) {
        properties.setValue(INCLUDES, value);
    }

    public String getExcludeFilter() {
        return properties.getValue(EXCLUDES, "");
    }

    public void setExcludeFilter(final String value) {
        properties.setValue(EXCLUDES, value);
    }

    public String getIdeBaseClass() {
        return properties.getValue(BASE_CLASS, "");
    }

    public void setIdeBaseClass(final String value) {
        properties.setValue(BASE_CLASS, value);
    }

    public String getIdeTestMethod() {
        return properties.getValue(TEST_METHOD, "");
    }

    public void setIdeTestMethod(final String value) {
        properties.setValue(TEST_METHOD, value);
    }

    public Boolean getColaTestsEnabled() {
        return properties.getBoolean(ENABLED, true);
    }

    public void setColaTestsEnabled(final Boolean value) {
        properties.setValue(ENABLED, value.toString());
    }

    private Project getProject(final Project project) {
        if (project == null) {
            DataContext dataContext = DataManager.getInstance().getDataContext();
            return DataKeys.PROJECT.getData(dataContext);
        }
        return project;
    }
}
