/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

plugins {
    alias(libs.plugins.maconi.jvm.library)
    alias(libs.plugins.maconi.jvm.library.test)
}

dependencies {
    api(libs.jspecify) {
        because("Nullability annotations for contract compliance (Comparable, equals, etc.)")
    }
}
