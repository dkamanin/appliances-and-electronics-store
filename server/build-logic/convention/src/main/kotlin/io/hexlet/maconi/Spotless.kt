package io.hexlet.maconi

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureSpotlessForJvm() {
    extensions.configure<SpotlessExtension> {
        java {
            googleJavaFormat(libs.findVersion("googleJavaFormat").get().requiredVersion).aosp()
            target("**/*.java")
            endWithNewline()
        }
    }
}

internal fun Project.configureSpotlessForRoot() {
    extensions.configure<SpotlessExtension> {
        kotlin {
            target("build-logic/convention/src/**/*.kt")
            ktlint()
            endWithNewline()
        }
    }
}
