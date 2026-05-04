/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.Objects;
import java.util.UUID;

public record BrandId(UUID value) implements DomainIdentifier<UUID> {
    public BrandId {
        if (value == null) {
            throw new DomainValidationException("Brand identifier must not be null");
        }
    }

    public static BrandId generate() {
        return new BrandId(UUID.randomUUID());
    }

    public static BrandId of(String value) {
        Objects.requireNonNull(value, "Brand identifier must not be null");
        if (value.isBlank()) {
            throw new DomainValidationException("Brand identifier must not be blank");
        }
        try {
            return new BrandId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}
