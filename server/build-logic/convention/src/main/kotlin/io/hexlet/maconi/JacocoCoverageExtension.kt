/*
 * Copyright (c) 2026 Daniil Kamanin and contributors.
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
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
