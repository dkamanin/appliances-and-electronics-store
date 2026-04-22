/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "maconi-store-backend"

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}
