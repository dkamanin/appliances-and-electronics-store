/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.brand.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record BrandName(String value) {
    public static final int MAX_LENGTH = 255;

    public BrandName {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("Brand name cannot be null or blank");
        }
        String normalized = value.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new DomainValidationException("Brand name cannot exceed " + MAX_LENGTH + " characters");
        }
        value = normalized;
    }
}
