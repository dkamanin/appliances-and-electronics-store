package io.hexlet.maconi.catalog.domain.model.product.attribute;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

import java.math.BigDecimal;

public record RangeNumericAttributeValue(BigDecimal min, BigDecimal max) implements AttributeValue {

    public RangeNumericAttributeValue {
        if (min == null || max == null) {
            throw new DomainValidationException("Range bounds cannot be null");
        }
        if (min.compareTo(max) > 0) {
            throw new DomainValidationException(
                    "Invalid range bounds: min value " + min + " is greater than max value " + max
            );
        }
    }

    public boolean contains(BigDecimal value) {
        if (value == null) return false;
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
}
