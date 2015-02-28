package com.github.bmsantos.idea.cola;

import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/*
 * COLA Tests = BDD + JUnit
 * Home: http://bmsantos.github.io/cola-tests/
 * Created by: Bruno Santos
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
