/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) implements DomainIdentifier<UUID> {
    public ProductId {
        if (value == null) {
            throw new DomainValidationException("Product identifier must not be null");
        }
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId of(String value) {
        Objects.requireNonNull(value, "Product identifier must not be null");
        if (value.isBlank()) {
            throw new DomainValidationException("Product identifier must not be blank");
        }
        try {
            return new ProductId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}
