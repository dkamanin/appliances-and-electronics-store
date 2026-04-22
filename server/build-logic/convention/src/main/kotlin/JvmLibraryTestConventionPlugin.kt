/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configureBaseTestSuite
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JvmTestSuitePlugin
import org.gradle.internal.Actions.with
import org.gradle.kotlin.dsl.apply

abstract class JvmLibraryTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<JvmTestSuitePlugin>()

            configureBaseTestSuite()
        }
    }
}
