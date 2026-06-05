/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

plugins {
    alias(libs.plugins.maconi.bounded.context)
}

dependencies {
    implementation(project(":maconi-cart:api"))
}
