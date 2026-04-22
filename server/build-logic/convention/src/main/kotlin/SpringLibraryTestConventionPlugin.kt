/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configureIntegrationTestSuite
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JvmTestSuitePlugin
import org.gradle.kotlin.dsl.apply

abstract class SpringLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("maconi.spring.library") {
                apply<JvmTestSuitePlugin>()

                configureIntegrationTestSuite()
            }
        }
    }
}
