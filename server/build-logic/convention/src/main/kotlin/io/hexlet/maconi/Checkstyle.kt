/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 */

package io.hexlet.maconi

import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

internal fun Project.configureCheckstyle() {
    apply<CheckstylePlugin>()
    extensions.configure<CheckstyleExtension> {
        toolVersion = libs.findVersion("checkstyle").get().requiredVersion
        val configDir =
            rootProject.layout.projectDirectory
                .dir("build-logic/convention/src/main/resources")
        configFile = configDir.file("checkstyle.xml").asFile
        /*
         * Injects the absolute configuration path into the "suppress-config-dir" property
         * within checkstyle.xml. This ensures portability across different environments,
         * as Checkstyle requires absolute paths to resolve suppression files.
         */
        configProperties = mapOf("suppress-config-dir" to configDir.asFile.absolutePath)
        // Checkstyle failures will only stop the build if the "CI" environment variable is present
        isIgnoreFailures = System.getenv("CI") == null
        isShowViolations = true
        maxWarnings = 0
    }
    tasks.withType<Checkstyle>().configureEach {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}
