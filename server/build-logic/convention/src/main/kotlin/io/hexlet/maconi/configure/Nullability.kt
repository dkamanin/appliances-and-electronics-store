/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.configure

import io.hexlet.maconi.task.GeneratePackageInfoTask
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

private val nullabilityModules = setOf("api", "domain", "application", "shared")

internal fun Project.configureNullability() {
    val layer = nullabilityModules.firstOrNull { name.endsWith(it) } ?: return

    val sourceSets = extensions.getByType<SourceSetContainer>()
    val mainSourceSet = sourceSets.named(SourceSet.MAIN_SOURCE_SET_NAME)

    val generateTask =
        tasks.register<GeneratePackageInfoTask>("generatePackageInfo") {
            description = "Generates missing package-info.java files with @NullMarked"
            group = "nullability"

            layerName.convention(layer)

            sourceDirectory.convention(
                mainSourceSet.map { it.java.srcDirs.first() }
                    .map { layout.projectDirectory.dir(it.absolutePath) },
            )
        }

    plugins.withId("com.diffplug.spotless") {
        tasks.configureEach {
            if (name == "spotlessApply") {
                dependsOn(generateTask)
            }
        }
    }
}
