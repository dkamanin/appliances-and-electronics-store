/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record OrderNumber(String value) implements DomainIdentifier<String> {
    private static final String PREFIX = "ORD-";
    private static final byte NUMBER_LENGTH = 8;

    public OrderNumber {
        if (value == null) {
            throw new DomainValidationException("Order number must not be null");
        }
        if (value.isBlank()) {
            throw new DomainValidationException("Order number must not be blank");
        }
        if (!value.startsWith(PREFIX) || value.length() != PREFIX.length() + NUMBER_LENGTH) {
            throw new DomainValidationException(
                    "Order number must start with '"
                            + PREFIX
                            + "' and have exactly "
                            + NUMBER_LENGTH
                            + " characters after prefix");
        }
    }

    public static OrderNumber create(OrderId orderId) {
        if (orderId == null) {
            throw new DomainValidationException(
                    "Order identifier must not be null to generate order number");
        }
        return new OrderNumber(
                PREFIX + orderId.value().toString().substring(0, NUMBER_LENGTH).toUpperCase());
    }
}
