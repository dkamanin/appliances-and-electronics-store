/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 */

import io.hexlet.maconi.configureCheckstyle
import io.hexlet.maconi.configureJavaLibrary
import io.hexlet.maconi.configureLayerIsolation
import io.hexlet.maconi.configureSpotlessForJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.apply

abstract class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<JavaLibraryPlugin>()
            apply(plugin = "com.diffplug.spotless")

            configureJavaLibrary()
            configureLayerIsolation()
            configureSpotlessForJvm()
            configureCheckstyle()
        }
    }
}
