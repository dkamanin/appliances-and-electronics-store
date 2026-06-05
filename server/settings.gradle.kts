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

include(":maconi-shared")

include(":maconi-cart")
include(":maconi-cart:api")

include(":maconi-catalog")
include(":maconi-catalog:api")

include(":maconi-inventory")
include(":maconi-inventory:api")

include(":maconi-orders")
include(":maconi-orders:api")

include(":maconi-payment")
include(":maconi-payment:api")

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}
