/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record CustomerId(@NonNull UUID value) implements DomainIdentifier<UUID> {
    public CustomerId {
        if (value == null) {
            throw new DomainValidationException("Customer identifier must not be null");
        }
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId of(@NonNull String value) {
        if (value.isBlank()) {
            throw new DomainValidationException("Customer identifier must not be blank");
        }
        try {
            return new CustomerId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}
