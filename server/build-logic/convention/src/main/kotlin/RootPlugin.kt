/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configureSpotlessForRoot
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

abstract class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        require(target.path == ":")
        with(target) {
            apply(plugin = "com.diffplug.spotless")

            configureSpotlessForRoot()
        }
    }
}
