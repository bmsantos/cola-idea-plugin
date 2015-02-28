package com.github.bmsantos.idea.cola;

import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.mockito.Mock;

import static com.intellij.openapi.compiler.CompilerTopics.COMPILATION_STATUS;
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
public class ColaTestsProjectComponentTest extends LightCodeInsightFixtureTestCase {

  public static final String STRING = "Some String";
  private ColaTestsProjectComponent uut;

  @Mock
  private ColaCompilerListener listener;

  @Override
  public void setUp() throws Exception {
    super.setUp();

    initMocks(this);

    uut = new TestableColaTestsProjectComponent(getProject(), listener);
  }

  @Override
  protected String getTestDataPath() {
    return "./testData";
  }

  public void testShouldSubscribeCompilationStatusListener() {
    // Given
    final CompilationStatusListener publisher = getProject().getMessageBus().syncPublisher(COMPILATION_STATUS);

    // When
    uut.projectOpened();
    publisher.fileGenerated(STRING, STRING);

    // Then

    verify(listener).fileGenerated(STRING, STRING);
  }

  public void testShouldUnsubscribeCompilationStatusListener() {
    // Given
    final CompilationStatusListener publisher = getProject().getMessageBus().syncPublisher(COMPILATION_STATUS);
    uut.projectOpened();

    // When
    uut.projectClosed();
    publisher.fileGenerated(STRING, STRING);

    // Then
    verify(listener, never()).fileGenerated(STRING, STRING);
  }

  private class TestableColaTestsProjectComponent extends ColaTestsProjectComponent {

    public TestableColaTestsProjectComponent(final Project project, final ColaCompilerListener listener) {
      super(project);
      this.listener = listener;
    }

    @Override
    protected ColaCompilerListener getColaCompilerListener() {
      return listener;
    }
  }
}
