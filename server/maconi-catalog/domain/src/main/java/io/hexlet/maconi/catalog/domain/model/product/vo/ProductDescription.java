/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record ProductDescription(String value) {
    public static int MAX_LENGTH = 5000;

    public ProductDescription {
        if (value == null) {
            throw new DomainValidationException("Product description cannot be null");
        }
        String normalized = value.trim();
        if (normalized.length() > MAX_LENGTH) {
            throw new DomainValidationException(
                    "Product description cannot exceed " + MAX_LENGTH + " characters");
        }
        value = normalized;
    }

    public static ProductDescription empty() {
        return new ProductDescription("");
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }
}
