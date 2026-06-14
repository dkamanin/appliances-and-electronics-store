/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

plugins {
    alias(libs.plugins.maconi.jvm.library)
    alias(libs.plugins.maconi.jvm.library.test)
    alias(libs.plugins.maconi.jvm.library.jacoco)
}

dependencies {
    implementation(project(":maconi-shared"))
}
