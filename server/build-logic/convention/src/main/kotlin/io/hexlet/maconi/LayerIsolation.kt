/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 */

package io.hexlet.maconi

import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.FileCollectionDependency
import org.gradle.api.artifacts.ProjectDependency

/**
 * Resolvable configurations used to assemble the final compile and runtime classpaths.
 * We monitor these specific entry points to enforce architectural boundaries
 * (e.g., preventing external leaks into the Domain layer or Shared/API modules).
 * * Note: We target resolvable configurations (*Classpath) instead of declarative ones
 * (implementation/api) to catch dependencies at the resolution stage, ensuring
 * 'Fail Fast' behavior during Gradle Sync.
 */
val forbiddenConfigurationsForCleanModules = setOf("runtimeClasspath", "compileClasspath", "annotationProcessor")

internal fun Project.configureLayerIsolation() {
    val projectPath = path
    configurations.configureEach {
        val configurationName = name
        incoming.beforeResolve {
            when {
                configurationName in forbiddenConfigurationsForCleanModules -> {
                    when {
                        projectPath.equals(":maconi-shared") -> checkSharedModuleDependencies(dependencies, projectPath)
                        projectPath.endsWith(":api") -> checkApiModuleDependencies(dependencies, projectPath)
                        projectPath.endsWith(":domain") -> checkDomainLayerDependencies(dependencies, projectPath)
                    }
                }

                projectPath.endsWith(":application") -> {
                    checkApplicationLayerDependencies(dependencies, projectPath)
                }

                projectPath.endsWith(":infra") -> {
                    checkInfraLayerDependencies(dependencies, projectPath)
                }
            }
        }
    }
}

private fun ensureNoDependencies(
    context: String,
    dependencies: DependencySet,
    projectPath: String,
) {
    dependencies.all {
        val dependencyInfo =
            when (this) {
                is ProjectDependency -> "'project(\"$path\")' dependency"
                is FileCollectionDependency -> "file dependency '${files.asPath}'"
                is ExternalModuleDependency -> "'$group:$name:$version' external dependency"
                else -> "'$group:$name:$version' dependency"
            }
        throw InvalidUserDataException(
            """
            Invalid configuration detected for '$projectPath'.

            The $context must not have any dependencies.

            To fix this, remove the $dependencyInfo from the 'build.gradle.kts' file.
            """.trimIndent(),
        )
    }
}

private fun checkSharedModuleDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    ensureNoDependencies("shared module", dependencies, projectPath)
}

private fun checkApiModuleDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    ensureNoDependencies("api module", dependencies, projectPath)
}

private fun checkDomainLayerDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    ensureNoDependencies("domain layer", dependencies, projectPath)
}

private fun checkApplicationLayerDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    dependencies.configureEach {
        if (this is ProjectDependency && !this.path.endsWith(":domain")) {
            throw InvalidUserDataException(
                """
                Invalid configuration detected for '$projectPath'.
                
                The application layer must only have project dependency on the domain layer.
                
                To fix this, remove the 'project("$path")' dependency from the 'build.gradle.kts' file.
                """.trimIndent(),
            )
        }
    }
}

private fun checkInfraLayerDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    dependencies.configureEach {
        val moduleName = projectPath.substringBeforeLast(":infra")
        if (this is ProjectDependency &&
            !path.equals(":maconi-shared") &&
            !path.endsWith(":api") &&
            !path.contains(moduleName)
        ) {
            throw InvalidUserDataException(
                """
                Invalid configuration detected for '$projectPath'.
                
                For cross-module dependencies, the infra layer must only depend on api modules, except common 'maconi-shared' module.
                
                To fix this, remove the 'project("$path")' dependency from the 'build.gradle.kts' file.
                """.trimIndent(),
            )
        }
    }
}
