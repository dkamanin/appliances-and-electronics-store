/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.configure

import io.hexlet.maconi.extension.JacocoAggregationExtension
import io.hexlet.maconi.extension.JacocoCoverageExtension
import io.hexlet.maconi.extension.libs
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.reporting.ReportingExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoCoverageReport
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

@org.gradle.api.tasks.UntrackedTask(because = "Must always run to print report link")
internal abstract class PrintReportLink : DefaultTask() {
    @get:Input
    abstract val reportName: Property<String>

    @get:Input
    abstract val reportDirPath: Property<String>

    @get:Input
    @get:Optional
    abstract val aggregated: Property<Boolean>

    @TaskAction
    fun print() {
        val dir = File(reportDirPath.get())
        val indexHtml = File(dir, "index.html")

        if (indexHtml.exists()) {
            val message =
                if (aggregated.getOrElse(false)) {
                    "Aggregated test coverage report link: ${indexHtml.toURI()}"
                } else {
                    "Test coverage report link for '${reportName.get()}' module: ${indexHtml.toURI()}"
                }
            println(message)
        } else {
            println("Test coverage report for '${reportName.get()}' was not generated.")
        }
    }
}

internal fun Project.configureJacoco(extension: JacocoCoverageExtension) {
    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("jacoco").get().toString()
    }

    val testTasks = tasks.withType<Test>()
    val jacocoReportTask = tasks.named<JacocoReport>("jacocoTestReport")

    jacocoReportTask.configure {
        dependsOn(testTasks)
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
        executionData.setFrom(
            testTasks.map { it.extensions.getByType<JacocoTaskExtension>().destinationFile },
        )
        classDirectories.setFrom(
            classDirectories.map {
                fileTree(it) { exclude(coverageExclusions) }
            },
        )
    }

    val printLinkProvider =
        tasks.register<PrintReportLink>("printJacocoTestReportLink") {
            dependsOn(jacocoReportTask)
            reportName.set(
                jacocoReportTask.map { it.path },
            )
            reportDirPath.set(
                jacocoReportTask.map { it.reports.html.outputLocation.get().asFile.absolutePath },
            )
            aggregated.set(false)
        }

    jacocoReportTask.configure {
        finalizedBy(printLinkProvider)
    }

    tasks.withType<JacocoReport>().configureEach {
        if (name != "jacocoTestReport") {
            finalizedBy("print${name.replaceFirstChar { it.uppercaseChar() }}Link")
        }
    }

    tasks.withType<JacocoCoverageVerification>().configureEach {
        classDirectories.setFrom(
            classDirectories.map {
                fileTree(it) { exclude(coverageExclusions) }
            },
        )
        violationRules {
            rule {
                limit {
                    counter = "LINE"
                    value = "COVEREDRATIO"
                    minimum = extension.minLinePercentage.get().toBigDecimal()
                }
                limit {
                    counter = "BRANCH"
                    value = "COVEREDRATIO"
                    minimum = extension.minBranchPercentage.get().toBigDecimal()
                }
            }
        }
    }

    rootProject.extensions.getByType<JacocoAggregationExtension>().includedProjectPaths.add(path)
}

internal fun Project.registerJacocoAggregation(extension: JacocoAggregationExtension) {
    extension.includedProjectPaths.all {
        dependencies {
            add("jacocoAggregation", project(this@all))
        }
    }

    configure<ReportingExtension> {
        reports {
            val report =
                create<JacocoCoverageReport>("jacocoAggregatedReport") {
                    testSuiteName.set("test")
                }

            val printAggregated =
                tasks.register<PrintReportLink>("printJacocoAggregatedReportLink") {
                    dependsOn(report.reportTask)
                    reportName.set("Aggregated")
                    reportDirPath.set(
                        report.reportTask.map { it.reports.html.outputLocation.get().asFile.absolutePath },
                    )
                    aggregated.set(true)
                }

            report.reportTask.configure {
                finalizedBy(printAggregated)
            }
        }
    }
}
