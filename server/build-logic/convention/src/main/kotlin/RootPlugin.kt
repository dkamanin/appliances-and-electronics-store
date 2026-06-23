/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

import io.hexlet.maconi.configure.configureSpotlessForRoot
import io.hexlet.maconi.configure.registerJacocoAggregation
import io.hexlet.maconi.extension.JacocoAggregationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.testing.jacoco.plugins.JacocoReportAggregationPlugin

abstract class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        require(target.path == ":")
        with(target) {
            apply(plugin = "com.diffplug.spotless")
            apply<JacocoReportAggregationPlugin>()

            configureSpotlessForRoot()

            val extension = extensions.create<JacocoAggregationExtension>("jacocoAggregation")
            registerJacocoAggregation(extension)
        }
    }
}
