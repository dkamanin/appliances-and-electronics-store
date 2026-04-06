package io.hexlet.maconi

import org.gradle.api.Project
import org.gradle.api.plugins.jvm.JvmTestSuite
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.testing.base.TestingExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin

internal fun Project.configureBaseTestSuite() {
    extensions.configure<TestingExtension> {
        suites.named<JvmTestSuite>("test") {
            useJUnitJupiter(libs.findVersion("junit-jupiter").get().requiredVersion)
            dependencies {
                implementation(libs.findLibrary("assertj-core").get())
                implementation(libs.findLibrary("junit-platform-launcher").get())
            }
        }
    }
}

internal fun Project.configureIntegrationTestSuite() {
    val testing = the<TestingExtension>()
    val integrationTest =
        testing.suites.register<JvmTestSuite>("integrationTest") {
            useJUnitJupiter()
            dependencies {
                implementation(project())
                implementation(platform(SpringBootPlugin.BOM_COORDINATES))
                implementation(libs.findLibrary("junit-platform-launcher").get())
                implementation(libs.findLibrary("spring-boot-starter-test").get())
            }
            targets.all {
                testTask.configure {
                    shouldRunAfter(tasks.named("test"))
                }
            }
        }
    tasks.named("check") {
        dependsOn(integrationTest)
    }
}
