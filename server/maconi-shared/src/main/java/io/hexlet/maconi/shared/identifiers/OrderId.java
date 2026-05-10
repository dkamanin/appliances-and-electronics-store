/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record OrderId(@NonNull UUID value) implements DomainIdentifier<UUID> {
    public OrderId {
        if (value == null) {
            throw new DomainValidationException("Order identifier must not be null");
        }
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId of(@NonNull String value) {
        if (value.isBlank()) {
            throw new DomainValidationException("Order identifier must not be blank");
        }
        try {
            return new OrderId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}
