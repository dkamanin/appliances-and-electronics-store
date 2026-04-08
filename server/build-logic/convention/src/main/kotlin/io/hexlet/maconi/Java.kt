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
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

internal fun Project.configureJavaLibrary() {
    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(
            JavaLanguageVersion.of(libs.findVersion("java").get().requiredVersion),
        )
    }
}
