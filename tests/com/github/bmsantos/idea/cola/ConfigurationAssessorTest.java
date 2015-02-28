package com.github.bmsantos.idea.cola;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
public class ConfigurationAssessorTest extends LightCodeInsightFixtureTestCase {

  public static final String STRING_VALUE = "Some String";
  private ConfigurationAssessor uut;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    uut = new ConfigurationAssessor(getProject());
  }

  @Override
  protected String getTestDataPath() {
    return "./testData";
  }

  public void testShouldSetTestsEnabled() {
    // Given
    boolean testsEnabled = true;

    // When
    uut.setColaTestsEnabled(testsEnabled);

    // Then
    assertThat(uut.getColaTestsEnabled(), is(testsEnabled));
  }

  public void testShouldSetIncludeFilter() {
    // When
    uut.setIncludeFilter(STRING_VALUE);

    // Then
    assertThat(uut.getIncludeFilter(), is(STRING_VALUE));
  }

  public void testShouldSetExcludeFilter() {
    // When
    uut.setExcludeFilter(STRING_VALUE);

    // Then
    assertThat(uut.getExcludeFilter(), is(STRING_VALUE));
  }

  public void testShouldSetIdeBaseClass() {
    // When
    uut.setIdeBaseClass(STRING_VALUE);

    // Then
    assertThat(uut.getIdeBaseClass(), is(STRING_VALUE));
  }

  public void testShouldSetIdeTestMethod() {
    // When
    uut.setIdeTestMethod(STRING_VALUE);

    // Then
    assertThat(uut.getIdeTestMethod(), is(STRING_VALUE));
  }
}
