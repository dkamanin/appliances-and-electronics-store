/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

plugins {
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.maconi.root)
}

group = "io.hexlet.maconi"

version = "2.0.0"
