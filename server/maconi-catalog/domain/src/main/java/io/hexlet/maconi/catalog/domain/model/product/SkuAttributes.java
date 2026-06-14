package io.hexlet.maconi.catalog.domain.model.product;

import io.hexlet.maconi.catalog.domain.model.product.attribute.*;
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
        if (values.containsKey(null) || values.containsValue(null)) {
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
        return Optional.of(values.get(key));
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
}