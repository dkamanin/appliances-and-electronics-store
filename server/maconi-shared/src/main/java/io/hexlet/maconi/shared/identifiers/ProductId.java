/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record ProductId(@NonNull UUID value) implements DomainIdentifier<UUID> {
    public ProductId {
        if (value == null) {
            throw new DomainValidationException("Product identifier must not be null");
        }
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }

    public static ProductId of(@NonNull String value) {
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
