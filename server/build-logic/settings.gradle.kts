/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

rootProject.name = "build-logic"

include(":convention")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
    repositories {
        mavenCentral()
    }
}
