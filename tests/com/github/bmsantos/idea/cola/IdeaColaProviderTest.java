package com.github.bmsantos.idea.cola;

import org.junit.Before;
import org.junit.Test;

import java.net.URLClassLoader;
import java.util.List;

import static java.io.File.separator;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.empty;
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
public class IdeaColaProviderTest {

  public static final String TARGET_DIRECTORY = "/path/to/test-classes";
  public static final String CLASS_PATH = "/my/class/path";
  public static final String PACKAGE_AND_CLASS = "my/package/Clazz.class";
  private IdeaColaProvider uut;

  @Before
  public void setUp() {
    uut = new IdeaColaProvider();
    uut.setDeltas(asList(PACKAGE_AND_CLASS));
  }

  @Test
  public void shouldGetTargetDirectory() {
    // Given
    uut.setTargetDirectory(TARGET_DIRECTORY);

    // When
    final String result = uut.getTargetDirectory();

    // Then
    assertThat(result, is(TARGET_DIRECTORY + separator));
  }

  @Test
  public void shouldGetTargetClassLoader() throws Exception {
    // When
    final URLClassLoader loader = uut.getTargetClassLoader();

    // Then
    assertThat(loader.getParent(), is(IdeaColaProviderTest.class.getClassLoader()));
  }

  @Test
  public void shouldIncludeClassPathElements() throws Exception {
    // Given
    final List<String> classPaths = asList(CLASS_PATH);
    uut.setClasspathElements(classPaths);

    // When
    final URLClassLoader loader = uut.getTargetClassLoader();

    // Then
    assertThat(loader.getURLs()[0].toString(), containsString(CLASS_PATH));
  }

  @Test
  public void shouldGetTargetClasses() {
    // Given
    uut.setDeltas(asList(PACKAGE_AND_CLASS));

    // When
    final List<String> targetClasses = uut.getTargetClasses();

    // Then
    assertThat(targetClasses, hasItem(PACKAGE_AND_CLASS));
  }

  @Test
  public void shouldExcludeTargetClasses() {
    // Given
    uut.setExcludes("**/Clazz.class");

    // When
    final List<String> targetClasses = uut.getTargetClasses();

    // Then
    assertThat(targetClasses, empty());
  }

  @Test
  public void shouldIncludeTargetClasses() {
    // Given
    uut.setIncludes("**/*Baz.class, **/Clazz.class");
    uut.setExcludes("**/Foo.class,**/Baa.class");

    // When
    final List<String> targetClasses = uut.getTargetClasses();

    // Then
    assertThat(targetClasses, hasItem(PACKAGE_AND_CLASS));
  }

  @Test
  public void shouldImposeExcludeOverInclude() {
    // Given
    uut.setIncludes("**/*Baz.class, **/Clazz.class");
    uut.setExcludes("**/Foo.class, **/Clazz.class");

    // When
    final List<String> targetClasses = uut.getTargetClasses();

    // Then
    assertThat(targetClasses, empty());
  }
}
