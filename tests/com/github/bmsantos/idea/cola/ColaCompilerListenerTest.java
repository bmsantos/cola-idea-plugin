package com.github.bmsantos.idea.cola;

import com.github.bmsantos.core.cola.main.ColaMain;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.mockito.Mock;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

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
public class ColaCompilerListenerTest extends LightCodeInsightFixtureTestCase {

  public static final String TARGET_CLASS_PATH = "/output";
  public static final String PACKAGE_AND_CLASS = "com/my/Clazz.class";
  public static final String STRING = "Some String";
  private ColaCompilerListener uut;

  @Mock
  private IdeaColaProvider provider;
  @Mock
  private ConfigurationAssessor config;
  @Mock
  private ColaMain colaMain;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    initMocks(this);

    given(config.getColaTestsEnabled()).willReturn(true);
    given(config.getIncludeFilter()).willReturn(STRING);
    given(config.getExcludeFilter()).willReturn(STRING);

    uut = new TestableColaCompilerListener(getProject(), provider, config, colaMain);
  }

  @Override
  protected String getTestDataPath() {
    return "./testData";
  }

  public void testShouldNotCompileTestsWhenDisabled() {
    // Given
    given(config.getColaTestsEnabled()).willReturn(false);

    // When
    uut.fileGenerated(TARGET_CLASS_PATH, PACKAGE_AND_CLASS);

    // Then
    verify(colaMain, never()).execute(provider);
  }

  public void testShouldSetProviderDetails() {
    // When
    uut.fileGenerated(TARGET_CLASS_PATH, PACKAGE_AND_CLASS);

    // Then
    verify(provider).setTargetDirectory(TARGET_CLASS_PATH);
    verify(provider).setClasspathElements(any(List.class));
    verify(provider).setIncludes(STRING);
    verify(provider).setExcludes(STRING);
    verify(provider).setDeltas(asList(PACKAGE_AND_CLASS));
  }

  public void testShouldExecuteColaCompiler() {
    // When
    uut.fileGenerated(TARGET_CLASS_PATH, PACKAGE_AND_CLASS);

    // Then
    verify(colaMain).execute(provider);
  }

  private class TestableColaCompilerListener extends ColaCompilerListener {

    private ColaMain colaMain;

    public TestableColaCompilerListener(final Project project, final IdeaColaProvider provider,
                                        final ConfigurationAssessor config, final ColaMain colaMain) {
      super(project);
      this.provider = provider;
      this.config = config;
      this.colaMain = colaMain;
    }

    @Override
    protected IdeaColaProvider getIdeaColaProvider() {
      return provider;
    }

    @Override
    protected ConfigurationAssessor getConfigurationAssessor(final Project project) {
      return config;
    }

    @Override
    public ColaMain getColaMain() {
      return colaMain;
    }
  }
}
