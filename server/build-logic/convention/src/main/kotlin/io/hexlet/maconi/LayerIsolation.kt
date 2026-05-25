/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi

import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
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
val allowedLibs = setOf("org.jspecify:jspecify")

private const val SHARED_MODULE_PATH = ":maconi-shared"
private const val API_MODULE_POSTFIX = ":api"
private const val DOMAIN_LAYER_POSTFIX = ":domain"
private const val APPLICATION_LAYER_POSTFIX = ":application"
private const val INFRA_LAYER_POSTFIX = ":infra"

internal fun Project.configureLayerIsolation() {
    val projectPath = path

    val isShared = projectPath == SHARED_MODULE_PATH
    val isApi = projectPath.endsWith(API_MODULE_POSTFIX)
    val isDomain = projectPath.endsWith(DOMAIN_LAYER_POSTFIX)
    val isApplication = projectPath.endsWith(APPLICATION_LAYER_POSTFIX)
    val isInfra = projectPath.endsWith(INFRA_LAYER_POSTFIX)

    configurations.configureEach {
        if (name in forbiddenConfigurationsForCleanModules) {
            incoming.beforeResolve {
                val isCompileClasspath = name == "compileClasspath"
                when {
                    isShared -> checkSharedModuleDependencies(dependencies, projectPath)
                    isApi -> checkApiModuleDependencies(dependencies, projectPath)
                    isDomain -> checkDomainLayerDependencies(dependencies, projectPath)
                    isApplication && isCompileClasspath -> checkApplicationLayerDependencies(dependencies, projectPath)
                    isInfra && isCompileClasspath -> checkInfraLayerDependencies(dependencies, projectPath)
                }
            }
        }
    }
}

internal fun Project.configureBoundedContextRootIsolation() {
    val projectPath = path
    configurations.configureEach {
        val configurationName = name
        incoming.beforeResolve {
            when {
                configurationName in forbiddenConfigurationsForCleanModules -> {
                    ensureOnlyInternalSubmodules(dependencies, projectPath)
                }
            }
        }
    }
}

private fun ensureOnlyInternalSubmodules(
    dependencies: DependencySet,
    projectPath: String,
) {
    dependencies.configureEach {
        val isInternalModule = this is ProjectDependency && path.startsWith("$projectPath:")

        if (!isInternalModule) {
            throw InvalidUserDataException(
                formatMessage(
                    projectPath,
                    "Context root must only depend on its internal submodules.",
                    this,
                ),
            )
        }
    }
}

private fun ensureNoDependenciesExceptAllowedLibs(
    context: String,
    dependencies: DependencySet,
    projectPath: String,
) {
    dependencies.configureEach {
        val isAllowedLib = allowedLibs.contains("$group:$name")

        if (!isAllowedLib) {
            throw InvalidUserDataException(
                formatMessage(
                    projectPath,
                    "$context must not have any project dependencies and may only use allowed external libraries (e.g., JSpecify)",
                    this,
                ),
            )
        }
    }
}

private fun checkSharedModuleDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    ensureNoDependenciesExceptAllowedLibs("Shared module", dependencies, projectPath)
}

private fun checkApiModuleDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    ensureNoDependenciesExceptAllowedLibs("API module", dependencies, projectPath)
}

private fun checkDomainLayerDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    dependencies.configureEach {
        val isShared = this is ProjectDependency && path == SHARED_MODULE_PATH

        if (!isShared) {
            throw InvalidUserDataException(
                formatMessage(
                    projectPath,
                    "Domain layer must only depend on the shared module.",
                    this,
                ),
            )
        }
    }
}

private fun checkApplicationLayerDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    val contextModuleName = projectPath.substringBeforeLast(APPLICATION_LAYER_POSTFIX)
    dependencies.configureEach {
        if (this !is ProjectDependency) return@configureEach

        val isShared = path == SHARED_MODULE_PATH
        val isSameContextDomain = path == "$contextModuleName$DOMAIN_LAYER_POSTFIX"

        if (!isShared && !isSameContextDomain) {
            throw InvalidUserDataException(
                formatMessage(
                    projectPath,
                    "Application layer must only depend on the shared module and the domain layer of the same context.",
                    this,
                ),
            )
        }
    }
}

private fun checkInfraLayerDependencies(
    dependencies: DependencySet,
    projectPath: String,
) {
    val contextModuleName = projectPath.substringBeforeLast(INFRA_LAYER_POSTFIX)
    dependencies.configureEach {
        if (this !is ProjectDependency) return@configureEach

        val isShared = path == SHARED_MODULE_PATH
        val isApiModule = path.endsWith(API_MODULE_POSTFIX)
        val isSameContextModule = path.startsWith("$contextModuleName:")

        if (!isShared && !isApiModule && !isSameContextModule) {
            throw InvalidUserDataException(
                formatMessage(
                    projectPath,
                    "Infra layer must only depend on modules of the same context, the shared module, or 'api' layers of other contexts. May have any external dependencies.",
                    this,
                ),
            )
        }
    }
}

private fun formatMessage(location: String, message: String, dependency: Dependency): String {
    val dependencyInfo = formatDependencyInfo(dependency)
    return (
        """
        Invalid configuration detected for '$location'.
        
        $message
        
        To fix this, remove the $dependencyInfo from the 'build.gradle.kts' file.
        """.trimIndent()
    )
}

private fun formatDependencyInfo(dependency: Dependency): String =
    dependency.run {
        when (this) {
            is ProjectDependency -> {
                "'project(\"${path}\")' dependency"
            }

            is FileCollectionDependency -> {
                "file dependency '${files.asPath}'"
            }

            is ExternalModuleDependency -> {
                listOfNotNull(group, name, version)
                    .joinToString(":", prefix = "'", postfix = "' external dependency")
            }

            else -> {
                listOfNotNull(group, name, version)
                    .joinToString(":", prefix = "'", postfix = "' dependency")
            }
        }
    }
