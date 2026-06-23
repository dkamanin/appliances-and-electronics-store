/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.extension

import org.gradle.api.DomainObjectSet

abstract class JacocoAggregationExtension {
    abstract val includedProjectPaths: DomainObjectSet<String>
}
