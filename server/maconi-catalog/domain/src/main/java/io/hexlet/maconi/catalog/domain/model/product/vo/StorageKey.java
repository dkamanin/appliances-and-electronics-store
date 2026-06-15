/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record StorageKey(String value) {
    public static int MAX_LENGTH = 1024;

    public StorageKey {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("Storage key cannot be null or blank");
        }
        String normalized = value.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new DomainValidationException(
                    "Storage key cannot exceed " + MAX_LENGTH + " characters");
        }
        value = normalized;
    }
}
