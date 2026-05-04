/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.Objects;
import java.util.UUID;

public record CategoryId(UUID value) implements DomainIdentifier<UUID> {
    public CategoryId {
        if (value == null) {
            throw new DomainValidationException("Category identifier must not be null");
        }
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }

    public static CategoryId of(String value) {
        Objects.requireNonNull(value, "Category identifier must not be null");
        if (value.isBlank()) {
            throw new DomainValidationException("Category identifier must not be blank");
        }
        try {
            return new CategoryId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}
