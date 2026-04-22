/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi

import org.gradle.api.provider.Property

abstract class JacocoCoverageExtension {
    abstract val moduleName: Property<String>
    abstract val minPercentage: Property<Double>

    init {
        minPercentage.convention(
            moduleName.map { name ->
                when {
                    name.equals("application") -> 0.80
                    name.equals("domain") -> 1.00
                    name.equals("infra") -> 0.10
                    else -> 0.00
                }
            },
        )
    }
}
