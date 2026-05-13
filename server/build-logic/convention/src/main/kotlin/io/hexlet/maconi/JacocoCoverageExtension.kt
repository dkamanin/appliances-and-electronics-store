/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi

import org.gradle.api.provider.Property

abstract class JacocoCoverageExtension {
    abstract val moduleName: Property<String>
    abstract val minLinePercentage: Property<Double>
    abstract val minBranchPercentage: Property<Double>

    init {
        minLinePercentage.convention(
            moduleName.map { name ->
                when {
                    name.equals("application") -> 0.70
                    name.equals("domain") -> 0.85
                    name.equals("infra") -> 0.40
                    name.equals("maconi-shared") -> 0.85
                    else -> 0.00
                }
            },
        )
        minBranchPercentage.convention(
            moduleName.map { name ->
                when {
                    name.equals("application") -> 0.60
                    name.equals("domain") -> 0.80
                    name.equals("infra") -> 0.30
                    name.equals("maconi-shared") -> 0.80
                    else -> 0.00
                }
            },
        )
    }
}
