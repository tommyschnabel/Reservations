/*
 * Copyright 2013 the original author or authors.
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

package org.gradle.api.internal.tasks.compile.incremental.jar;

import org.gradle.api.internal.tasks.compile.incremental.deps.DefaultDependentsSet;
import org.gradle.api.internal.tasks.compile.incremental.deps.DependentsSet;

import java.io.Serializable;
import java.util.Collection;

class ClassSnapshot implements Serializable {

    private final byte[] hash;
    private final Collection<String> dependentClasses;

    public ClassSnapshot(byte[] hash) {
        this(hash, null);
    }

    public ClassSnapshot(byte[] hash, Collection<String> dependentClasses) {
        this.hash = hash;
        this.dependentClasses = dependentClasses;
    }

    public DependentsSet getDependents() {
        if (dependentClasses == null) {
            return new DefaultDependentsSet(true);
        }
        return new DefaultDependentsSet(dependentClasses);
    }

    public byte[] getHash() {
        return hash;
    }
}
