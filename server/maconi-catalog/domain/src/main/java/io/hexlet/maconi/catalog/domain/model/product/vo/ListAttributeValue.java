/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.ArrayList;
import java.util.List;

public record ListAttributeValue(List<String> values) implements AttributeValue {
    public ListAttributeValue {
        if (values == null) {
            throw new DomainValidationException("Value list cannot be null");
        }
        List<String> normalized = new ArrayList<>(values.size());
        for (String value : values) {
            if (value == null) {
                throw new DomainValidationException("Value list elements cannot be null");
            }
            String trimmed = value.trim();
            if (trimmed.isBlank()) {
                throw new DomainValidationException("List attribute elements cannot be empty");
            }
            normalized.add(trimmed);
        }
        values = List.copyOf(normalized);
    }
}
