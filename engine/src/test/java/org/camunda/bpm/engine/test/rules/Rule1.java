/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.test.rules;

import org.camunda.bpm.engine.impl.ProcessEngineLogger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

public class Rule1 extends TestWatcher {

//  private static Logger LOG = ProcessEngineLogger.TEST_LOGGER.getLogger();


  @Override
  protected void starting(Description description) {
    super.starting(description);
    System.out.println("STARTING RULE1");
  }

  @Override
  public Statement apply(Statement base, Description description) {
    System.out.println("APPLYING RULE1");
    return super.apply(base, description);
  }

  @Override
  protected void finished(Description description) {
    System.out.println("FINISHED RULE1");
    super.finished(description);
  }
}