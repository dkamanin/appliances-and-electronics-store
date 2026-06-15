/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record BaseName(String value) {
    public static int MAX_LENGTH = 255;

    public BaseName {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("Base name cannot be null or blank");
        }
        String normalized = value.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new DomainValidationException(
                    "Base name cannot exceed " + MAX_LENGTH + " characters");
        }
        value = normalized;
    }
}
