/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.brand.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record BrandDescription(String value) {
    public static final int MAX_LENGTH = 2000;

    public BrandDescription {
        if (value == null) {
            throw new DomainValidationException("Brand description cannot be null");
        }
        String normalized = value.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new DomainValidationException("Brand description cannot exceed " + MAX_LENGTH + " characters");
        }
        value = normalized;
    }

    public static BrandDescription empty() {
        return new BrandDescription("");
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }
}
