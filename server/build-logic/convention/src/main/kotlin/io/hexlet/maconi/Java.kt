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
