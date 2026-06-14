package io.hexlet.maconi.catalog.domain.model.product.attribute;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record BooleanAttributeValue(Boolean value) implements AttributeValue {
    public BooleanAttributeValue {
        if (value == null) {
            throw new DomainValidationException("Value cannot be null");
        }
    }
}