/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

public sealed interface AttributeValue
        permits StringAttributeValue,
                NumericAttributeValue,
                BooleanAttributeValue,
                RangeNumericAttributeValue,
                ListAttributeValue {}
