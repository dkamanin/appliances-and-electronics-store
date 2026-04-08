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

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotlessForJvm() {
    extensions.configure<SpotlessExtension> {
        java {
            target("src/**/*.java")
            licenseHeaderFile(rootDir.resolve("spotless/copyright.java"))
            googleJavaFormat(libs.findVersion("googleJavaFormat").get().requiredVersion).aosp()
            endWithNewline()
        }
        kotlinGradle {
            target("*.kts")
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
            ktlint()
            endWithNewline()
        }
    }
}

internal fun Project.configureSpotlessForRoot() {
    extensions.configure<SpotlessExtension> {
        kotlin {
            target("build-logic/convention/src/**/*.kt")
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kt"))
            ktlint()
            endWithNewline()
        }
        kotlinGradle {
            target("*.kts", "build-logic/*.kts", "build-logic/convention/*.kts")
            licenseHeaderFile(rootDir.resolve("spotless/copyright.kts"), "(^(?![\\/ ]\\*).*$)")
            ktlint()
            endWithNewline()
        }
    }
}
