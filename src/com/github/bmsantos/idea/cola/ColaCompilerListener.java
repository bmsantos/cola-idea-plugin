package com.github.bmsantos.idea.cola;

import com.github.bmsantos.core.cola.main.ColaMain;
import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.project.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

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
public class ColaCompilerListener implements CompilationStatusListener {
  private static Logger log = LoggerFactory.getLogger(ColaCompilerListener.class);

  protected IdeaColaProvider provider;
  protected ConfigurationAssessor config;

  public ColaCompilerListener(final Project project) {
    provider = getIdeaColaProvider();
    config = getConfigurationAssessor(project);
  }

  @Override
  public void compilationFinished(boolean aborted, int errors, int warning, CompileContext compileContext) {
    log.debug("Aborted: " + aborted);
    log.debug("Errors: " + errors);
    log.debug("Warnings: " + warning);
  }

  @Override
  public void fileGenerated(String outputRoot, String relativePath) {
    log.debug("Output Root: " + outputRoot);
    log.debug("Relative Path: " + relativePath);

    if (!config.getColaTestsEnabled()) {
      return;
    }

    provider.setTargetDirectory(outputRoot);
    provider.setClasspathElements(asList(outputRoot));
    provider.setIncludes(config.getIncludeFilter());
    provider.setExcludes(config.getExcludeFilter());
    provider.setDeltas(asList(relativePath));

    getColaMain().execute(provider);
  }

  protected IdeaColaProvider getIdeaColaProvider() {
    return new IdeaColaProvider();
  }

  protected ConfigurationAssessor getConfigurationAssessor(Project project) {
    return new ConfigurationAssessor(project);
  }

  protected ColaMain getColaMain() {
    return new ColaMain(config.getIdeBaseClass(), config.getIdeTestMethod());
  }
}
