package com.github.bmsantos.idea.test;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NormalJunitTest {
    @Test
    public void shouldPass() {
        System.out.println("Normal Junit Test executed.");
        assertTrue(true);
    }
}
