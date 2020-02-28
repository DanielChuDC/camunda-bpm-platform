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
package org.camunda.bpm.engine.test.util;

import java.io.FileNotFoundException;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.test.PluggableProcessEngineTestCase;
import org.camunda.commons.testconainers.DatabaseContainerProvider;


/** Base class for the process engine test cases.
 *
 * The main reason not to use our own test support classes is that we need to
 * run our test suite with various configurations, e.g. with and without spring,
 * standalone or on a server etc.  Those requirements create some complications
 * so we think it's best to use a separate base class.  That way it is much easier
 * for us to maintain our own codebase and at the same time provide stability
 * on the test support classes that we offer as part of our api (in org.camunda.bpm.engine.test).
 *
 * @author Tom Baeyens
 * @author Joram Barrez
 */
public class PluggableProcessEngineTest extends PluggableProcessEngineTestCase {

  public static ProcessEngine getProcessEngine() {
    return getOrInitializeCachedProcessEngineWithTC();
  }

  @Override
  protected void initializeProcessEngine() {
    processEngine = getProcessEngine();
  }

  protected static ProcessEngine getOrInitializeCachedProcessEngineWithTC() {
    if (cachedProcessEngine == null) {
      ProcessEngineConfigurationImpl processEngineConfiguration;
      try {
        processEngineConfiguration = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
            .createProcessEngineConfigurationFromResource("camunda.cfg.xml");
      } catch (RuntimeException ex) {
        if (ex.getCause() != null && ex.getCause() instanceof FileNotFoundException) {
          processEngineConfiguration = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
              .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        } else {
          throw ex;
        }
      }

      // bootstrap Testcontainers database
      DatabaseContainerProvider databaseProvider = new DatabaseContainerProvider();
      databaseProvider.startDatabase();
      if (databaseProvider.getDbContainer() != null) {
        processEngineConfiguration.setJdbcUrl(databaseProvider.getJdbcUrl());
      }

      cachedProcessEngine = processEngineConfiguration.buildProcessEngine();
    }
    return cachedProcessEngine;
  }
}