/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configureBoundedContextRootIsolation
import io.hexlet.maconi.configureJavaLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.apply

abstract class BoundedContextConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        require(validatePath(target.path))
        with(target) {
            apply<JavaLibraryPlugin>()

            configureJavaLibrary()
            configureBoundedContextRootIsolation()
        }
    }

    private fun validatePath(path: String): Boolean {
        val isNotRoot = path != ":"
        val isNotLayer =
            !path.endsWith("api") &&
                !path.endsWith("domain") &&
                !path.endsWith("application") &&
                !path.endsWith("infra")
        val isNotShared = path != ":maconi-shared"
        return isNotRoot && isNotLayer && isNotShared
    }
}
