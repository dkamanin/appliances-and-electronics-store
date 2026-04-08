/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
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
