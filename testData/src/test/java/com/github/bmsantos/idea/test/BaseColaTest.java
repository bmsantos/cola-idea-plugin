package com.github.bmsantos.idea.test;

import com.github.bmsantos.core.cola.story.annotations.IdeEnabler;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public abstract class BaseColaTest {
  @IdeEnabler
  @Test
  public void iWillBeRemoved() {
    assertTrue(true);
  }
}
