package com.github.bmsantos.idea.test;

import com.github.bmsantos.core.cola.story.annotations.Given;
import com.github.bmsantos.core.cola.story.annotations.Then;
import com.github.bmsantos.core.cola.story.annotations.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.contains;
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
public class SimpleColaTest extends BaseColaTest {
  private final String stories =
    "Feature: Introduce drinking\n"
      + "Scenario: Should get happy\n"
      + "Given a beer to enjoy\n"
      + "When mixed with 13 other alcoholic drinks\n"
      + "Then one will be drunk!";

  public List<String> executionOrder = new ArrayList<>();

  @Given("a (juice|beer) to enjoy")
  public void given() {
    executionOrder.add(Thread.currentThread().getStackTrace()[1].getMethodName());
  }

  @When("mixed with \\d+ other (redbull|alcoholic drinks)")
  public void when() {
    executionOrder.add(Thread.currentThread().getStackTrace()[1].getMethodName());
  }

  @Then("one will be (drunk|really energetic)!")
  public void then() {
    executionOrder.add(Thread.currentThread().getStackTrace()[1].getMethodName());

    assertThat(executionOrder, contains("given", "when", "then"));
  }
}
