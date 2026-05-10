/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record BrandId(@NonNull UUID value) implements DomainIdentifier<UUID> {
    public BrandId {
        if (value == null) {
            throw new DomainValidationException("Brand identifier must not be null");
        }
    }

    public static BrandId generate() {
        return new BrandId(UUID.randomUUID());
    }

    public static BrandId of(@NonNull String value) {
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
