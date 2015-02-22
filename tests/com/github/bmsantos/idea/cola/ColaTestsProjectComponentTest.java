package com.github.bmsantos.idea.cola;

import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.mockito.Mock;

import static com.intellij.openapi.compiler.CompilerTopics.COMPILATION_STATUS;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ColaTestsProjectComponentTest extends LightCodeInsightFixtureTestCase {

    public static final String STRING = "Some String";
    private ColaTestsProjectComponent uut;

    @Mock private ColaCompilerListener listener;

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
