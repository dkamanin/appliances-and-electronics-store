/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configureSpring
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

abstract class SpringLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.withPlugin("maconi.jvm.library") {
                apply(plugin = "org.springframework.boot")

                configureSpring()
            }
        }
    }
}
