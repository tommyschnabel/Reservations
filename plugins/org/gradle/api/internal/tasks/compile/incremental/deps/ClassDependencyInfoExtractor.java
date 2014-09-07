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

package org.gradle.api.internal.tasks.compile.incremental.deps;

import org.gradle.api.file.FileVisitDetails;
import org.gradle.api.file.FileVisitor;
import org.gradle.api.internal.tasks.compile.incremental.analyzer.ClassAnalysis;
import org.gradle.api.internal.tasks.compile.incremental.analyzer.ClassDependenciesAnalyzer;

import java.io.File;
import java.io.IOException;

public class ClassDependencyInfoExtractor implements FileVisitor {

    private final ClassDependenciesAnalyzer analyzer;
    private final String packagePrefix;
    private final ClassDependentsAccumulator accumulator;

    public ClassDependencyInfoExtractor(ClassDependenciesAnalyzer analyzer) {
        this(analyzer, "");
    }

    ClassDependencyInfoExtractor(ClassDependenciesAnalyzer analyzer, String packagePrefix) {
        this.analyzer = analyzer;
        this.packagePrefix = packagePrefix;
        accumulator = new ClassDependentsAccumulator(packagePrefix);
    }

    public void visitDir(FileVisitDetails dirDetails) {}

    public void visitFile(FileVisitDetails fileDetails) {
        File file = fileDetails.getFile();
        if (!file.getName().endsWith(".class")) {
            return;
        }
        String className = fileDetails.getPath().replaceAll("/", ".").replaceAll("\\.class$", "");
        if (!className.startsWith(packagePrefix)) {
            return;
        }

        try {
            ClassAnalysis analysis = analyzer.getClassAnalysis(className, file);
            accumulator.addClass(className, analysis.isDependencyToAll(), analysis.getClassDependencies());
        } catch (IOException e) {
            throw new RuntimeException("Problems extracting class dependency from " + file, e);
        }
    }

    public ClassDependencyInfo getDependencyInfo() {
        return new ClassDependencyInfo(accumulator.getDependentsMap());
    }
}