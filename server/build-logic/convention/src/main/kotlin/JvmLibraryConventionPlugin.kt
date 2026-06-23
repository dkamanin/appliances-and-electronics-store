/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configure.configureCheckstyle
import io.hexlet.maconi.configure.configureJavaLibrary
import io.hexlet.maconi.configure.configureLayerIsolation
import io.hexlet.maconi.configure.configureNullability
import io.hexlet.maconi.configure.configureSpotlessForJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.apply

abstract class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<JavaLibraryPlugin>()
            apply(plugin = "com.diffplug.spotless")

            configureNullability()
            configureJavaLibrary()
            configureLayerIsolation()
            configureSpotlessForJvm()
            configureCheckstyle()
        }
    }
}
