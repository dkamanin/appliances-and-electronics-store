/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoCoverageVerification
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.io.File

private val coverageExclusions =
    listOf(
        "**/config/*",
        "**/*Application*",
        "**/dto/**",
        "**/entity/**",
        "**/mapper/**",
        "**/*Exception*",
        "**/config/**",
        "**/*Entity.class",
    )

internal fun Project.configureJacoco(extension: JacocoCoverageExtension) {
    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("jacoco").get().toString()
    }
    val testTasks = tasks.withType<Test>()
    tasks.withType<JacocoReport>().configureEach {
        dependsOn(testTasks)
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
        executionData.setFrom(
            testTasks.map {
                it.extensions.getByType<JacocoTaskExtension>().destinationFile
            },
        )
        classDirectories.setFrom(
            classDirectories.map {
                fileTree(it) {
                    exclude(coverageExclusions)
                }
            },
        )
        // Provide a clickable link to the coverage report, as JaCoCo doesn't log it by default.
        doLast {
            val reportFile =
                File(
                    reports.html.outputLocation
                        .get()
                        .asFile,
                    "index.html",
                )
            println("Test coverage report link: ${reportFile.toURI()}")
        }
    }
    tasks.withType<JacocoCoverageVerification>().configureEach {
        classDirectories.setFrom(
            classDirectories.map {
                fileTree(it) {
                    exclude(coverageExclusions)
                }
            },
        )
        violationRules {
            rule {
                limit {
                    minimum = extension.minPercentage.get().toBigDecimal()
                }
            }
        }
    }
}
