/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record StringAttributeValue(String value) implements AttributeValue {

    public StringAttributeValue {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("Value cannot be null or blank");
        }
        value = value.trim();
    }
}
