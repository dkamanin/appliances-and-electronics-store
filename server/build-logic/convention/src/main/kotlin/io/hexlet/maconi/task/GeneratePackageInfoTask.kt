/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GeneratePackageInfoTask : DefaultTask() {
    @get:InputDirectory
    abstract val sourceDirectory: DirectoryProperty

    @get:Input
    abstract val layerName: Property<String>

    @TaskAction
    fun generate() {
        val rootDir = sourceDirectory.get().asFile
        val layer = layerName.get()

        val packages = findPackagesInsideLayer(rootDir, layer)

        packages.forEach { packageName ->
            val packageDir = File(rootDir, packageName.replace('.', File.separatorChar))
            val packageInfoFile = File(packageDir, "package-info.java")

            if (!packageInfoFile.exists()) {
                packageDir.mkdirs()
                packageInfoFile.writeText(buildPackageInfoContent(packageName))
                val relativePath = packageInfoFile.relativeTo(rootDir)
                logger.lifecycle("Generated: $relativePath")
            }
        }
    }

    private fun findPackagesInsideLayer(root: File, layer: String): List<String> {
        if (!root.exists()) return emptyList()

        return root.walkTopDown()
            .filter { it.isDirectory }
            .filter { dir ->
                val relativePath = dir.relativeTo(root).path.replace(File.separatorChar, '/')

                val isInsideLayer = relativePath.contains("/$layer/") || relativePath.endsWith("/$layer")
                val startsCorrectly = relativePath.startsWith("io/hexlet/maconi")

                isInsideLayer && startsCorrectly
            }
            .filter { dir ->
                dir.listFiles()?.any { it.extension.equals("java", ignoreCase = true) } == true
            }
            .map { it.relativeTo(root).path.replace(File.separatorChar, '.') }
            .distinct()
            .toList()
    }

    private fun buildPackageInfoContent(packageName: String): String {
        return """
            /**
             * This package is null-marked by convention using JSpecify.
             *
             * @see org.jspecify.annotations.NullMarked
             */
            @org.jspecify.annotations.NullMarked
            package $packageName;
            """.trimIndent()
    }
}
