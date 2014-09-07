/*
 * Copyright 2014 the original author or authors.
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
package org.gradle.api.plugins.tomcat.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.plugins.WarPlugin

/**
 * Parent class for all Tomcat tasks.
 *
 * @author Benjamin Muschko
 */
class Tomcat extends DefaultTask {
    Tomcat() {
        group = WarPlugin.WEB_APP_GROUP

        // No matter what the inputs and outputs make sure that run tasks are never up-to-date
        outputs.upToDateWhen {
            false
        }
    }
}
