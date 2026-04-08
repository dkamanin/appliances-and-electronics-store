/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 */

import io.hexlet.maconi.configureSpotlessForRoot
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

abstract class RootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        require(target.path == ":")
        with(target) {
            apply(plugin = "com.diffplug.spotless")

            configureSpotlessForRoot()
        }
    }
}
