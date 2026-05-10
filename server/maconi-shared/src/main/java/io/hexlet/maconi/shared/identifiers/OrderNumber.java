/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public record OrderNumber(@NonNull String value) implements DomainIdentifier<String> {

    private static final String PREFIX = "ORD-";
    private static final int BODY_LENGTH = 8;
    private static final int TOTAL_LENGTH = PREFIX.length() + BODY_LENGTH;

    public OrderNumber {
        validate(value);
    }

    public static OrderNumber of(@NonNull String rawValue) {
        Objects.requireNonNull(rawValue, "rawValue must not be null");
        String normalized = rawValue.trim().toUpperCase();
        return new OrderNumber(normalized);
    }

    public static OrderNumber create(@NonNull OrderId orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");

        String uuid = orderId.value().toString();
        String body = uuid.substring(0, BODY_LENGTH).toUpperCase();

        return new OrderNumber(PREFIX + body);
    }

    private static void validate(@NonNull String value) {
        Objects.requireNonNull(value, "value must not be null");
        if (value.length() != TOTAL_LENGTH) {
            throw new DomainValidationException(
                    "Order number must have exactly " + TOTAL_LENGTH + " characters");
        }
        if (!value.startsWith(PREFIX)) {
            throw new DomainValidationException("Order number must start with '" + PREFIX + "'");
        }

        String body = value.substring(PREFIX.length());

        if (!body.chars().allMatch(ch -> Character.isUpperCase(ch) || Character.isDigit(ch))) {
            throw new DomainValidationException(
                    "Order number body must contain only uppercase letters and digits");
        }
    }
}
