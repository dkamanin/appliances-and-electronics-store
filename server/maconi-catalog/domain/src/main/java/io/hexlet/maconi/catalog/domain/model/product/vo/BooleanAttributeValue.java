/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record BooleanAttributeValue(Boolean value) implements AttributeValue {
    public BooleanAttributeValue {
        if (value == null) {
            throw new DomainValidationException("Value cannot be null");
        }
    }
}
