/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record SkuAttributes(Map<String, AttributeValue> values) {

    public SkuAttributes {
        if (values == null) {
            throw new DomainValidationException("Attributes map cannot be null");
        }
        boolean hasNulls =
                values.entrySet().stream()
                        .anyMatch(entry -> entry.getKey() == null || entry.getValue() == null);
        if (hasNulls) {
            throw new DomainValidationException("Attributes map cannot contains null key or value");
        }
        values = Map.copyOf(values);
    }

    public static SkuAttributes empty() {
        return new SkuAttributes(Map.of());
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public boolean containsKey(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return values.containsKey(key);
    }

    public Optional<AttributeValue> get(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return Optional.ofNullable(values.get(key));
    }

    public Optional<String> getAsString(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        AttributeValue attr = values.get(key);
        if (attr instanceof StringAttributeValue(String value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public Optional<BigDecimal> getAsNumeric(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        AttributeValue attr = values.get(key);
        if (attr instanceof NumericAttributeValue(BigDecimal value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public Optional<Boolean> getAsBoolean(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        AttributeValue attr = values.get(key);
        if (attr instanceof BooleanAttributeValue(Boolean value)) {
            return Optional.of(value);
        }
        return Optional.empty();
    }

    public Optional<RangeNumericAttributeValue> getAsRangeNumeric(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        AttributeValue attr = values.get(key);
        if (attr instanceof RangeNumericAttributeValue range) {
            return Optional.of(range);
        }
        return Optional.empty();
    }

    public Optional<ListAttributeValue> getAsList(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        AttributeValue attr = values.get(key);
        if (attr instanceof ListAttributeValue list) {
            return Optional.of(list);
        }
        return Optional.empty();
    }
}
