/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.math.BigDecimal;

public record NumericAttributeValue(BigDecimal value) implements AttributeValue {

    public NumericAttributeValue {
        if (value == null) {
            throw new DomainValidationException("Value cannot be null");
        }
    }
}
