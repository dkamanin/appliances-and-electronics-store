/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.springframework.boot.gradle.plugin.SpringBootPlugin

internal fun Project.configureSpring() {
    dependencies {
        "implementation"(platform(SpringBootPlugin.BOM_COORDINATES))
        "implementation"(libs.findLibrary("spring-boot-starter").get())
    }
}
