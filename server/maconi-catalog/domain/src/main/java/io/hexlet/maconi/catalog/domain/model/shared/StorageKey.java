/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.shared;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record StorageKey(String value) {
    public static final int MAX_LENGTH = 1024;

    public StorageKey {
        if (value == null) {
            throw new DomainValidationException("Storage key cannot be null");
        }
        String normalized = value.trim();
        if (normalized.isBlank()) {
            throw new DomainValidationException("Storage key cannot be blank");
        }
        if (normalized.length() > MAX_LENGTH) {
            throw new DomainValidationException("Storage key cannot exceed " + MAX_LENGTH + " characters");
        }
        value = normalized;
    }
}
