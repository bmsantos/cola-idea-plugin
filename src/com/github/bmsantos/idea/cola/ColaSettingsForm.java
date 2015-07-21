package com.github.bmsantos.idea.cola;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/*
 * COLA Tests = BDD + JUnit
 * Home: http://bmsantos.github.io/cola-tests/
 *
 * Created by: Bruno Santos
 *
 * Contact: bmrosantos@gmail.com
 *
 * License:
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ColaSettingsForm implements Configurable {

  public static final Project GET_PROJECT = null;

  private JTextField includeFilter;
  private JTextField excludeFilter;
  private JPanel colaTestsPanel;
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
      colaTestsEnabled.isSelected() != config.getColaTestsEnabled();
  }

  @Override
  public void apply() throws ConfigurationException {
    config.setIncludeFilter(includeFilter.getText());
    config.setExcludeFilter(excludeFilter.getText());
    config.setColaTestsEnabled(colaTestsEnabled.isSelected());
  }

  @Override
  public void reset() {
    includeFilter.setText(config.getIncludeFilter());
    excludeFilter.setText(config.getExcludeFilter());
    colaTestsEnabled.setSelected(config.getColaTestsEnabled());
    updateEnabledStates(colaTestsEnabled.isSelected());
  }

  @Override
  public void disposeUIResources() {
    // empty
  }

  private void updateEnabledStates(final boolean enabled) {
    includeFilter.setEnabled(enabled);
    excludeFilter.setEnabled(enabled);
  }
}