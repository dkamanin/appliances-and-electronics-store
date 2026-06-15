/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.math.BigDecimal;
import java.util.Objects;

public record RangeNumericAttributeValue(BigDecimal min, BigDecimal max) implements AttributeValue {

    public RangeNumericAttributeValue {
        if (min == null || max == null) {
            throw new DomainValidationException("Range bounds cannot be null");
        }
        if (min.compareTo(max) > 0) {
            throw new DomainValidationException(
                    "Invalid range bounds: min value " + min + " is greater than max value " + max);
        }
    }

    public boolean contains(BigDecimal value) {
        Objects.requireNonNull(value, "Value cannot be null");
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
}
