/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record EventId(@NonNull UUID value) implements DomainIdentifier<UUID> {
    public EventId {
        if (value == null) {
            throw new DomainValidationException("Event identifier must not be null");
        }
    }

    public static EventId generate() {
        return new EventId(UUID.randomUUID());
    }

    public static EventId of(@NonNull String value) {
        if (value.isBlank()) {
            throw new DomainValidationException("Event identifier must not be blank");
        }
        try {
            return new EventId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}
