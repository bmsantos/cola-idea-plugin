package com.github.bmsantos.idea.cola;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
