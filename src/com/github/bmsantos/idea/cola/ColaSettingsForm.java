package com.github.bmsantos.idea.cola;

import com.intellij.ide.DataManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ColaSettingsForm implements Configurable {

    public static final Project GET_PROJECT = null;

    private JTextField includeFilter;
    private JTextField excludeFilter;
    private JPanel colaTestsPanel;
    private JTextField baseClass;
    private JTextField testMethod;
    private JCheckBox colaTestsEnabled;

    private final ConfigurationAssessor config;

    public ColaSettingsForm() {
        config = new ConfigurationAssessor(GET_PROJECT);
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "COLA Tests";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return "COLA Tests";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return colaTestsPanel;
    }

    @Override
    public boolean isModified() {
        updateEnabledStates(colaTestsEnabled.isSelected());
        return !includeFilter.getText().equals(config.getIncludeFilter()) ||
               !excludeFilter.getText().equals(config.getExcludeFilter()) ||
               !baseClass.getText().equals(config.getIdeBaseClass()) ||
               !testMethod.getText().equals(config.getIdeTestMethod()) ||
               colaTestsEnabled.isSelected() != config.getColaTestsEnabled();
    }

    @Override
    public void apply() throws ConfigurationException {
        config.setIncludeFilter(includeFilter.getText());
        config.setExcludeFilter(excludeFilter.getText());
        config.setIdeBaseClass(baseClass.getText());
        config.setIdeTestMethod(testMethod.getText());
        config.setColaTestsEnabled(colaTestsEnabled.isSelected());
    }

    @Override
    public void reset() {
        includeFilter.setText(config.getIncludeFilter());
        excludeFilter.setText(config.getExcludeFilter());
        baseClass.setText(config.getIdeBaseClass());
        testMethod.setText(config.getIdeTestMethod());
        colaTestsEnabled.setSelected(config.getColaTestsEnabled());
        updateEnabledStates(colaTestsEnabled.isSelected());
    }

    @Override
    public void disposeUIResources() {
        // empty
    }

    private PropertiesComponent getProjectProperties() {
        DataContext dataContext = DataManager.getInstance().getDataContext();
        Project project = DataKeys.PROJECT.getData(dataContext);
        return PropertiesComponent.getInstance(project);
    }

    private void updateEnabledStates(final boolean enabled) {
        includeFilter.setEnabled(enabled);
        excludeFilter.setEnabled(enabled);
        baseClass.setEnabled(enabled);
        testMethod.setEnabled(enabled);
    }
}