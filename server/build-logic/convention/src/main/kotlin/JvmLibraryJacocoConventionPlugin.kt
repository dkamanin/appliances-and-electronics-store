/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 */

import io.hexlet.maconi.JacocoCoverageExtension
import io.hexlet.maconi.configureJacoco
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.testing.jacoco.plugins.JacocoPlugin

abstract class JvmLibraryJacocoConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<JacocoPlugin>()

            val extension = extensions.create<JacocoCoverageExtension>("jacocoCoverage")
            extension.moduleName.convention(name)
            configureJacoco(extension)
        }
    }
}
