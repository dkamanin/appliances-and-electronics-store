package io.hexlet.maconi.catalog.domain.model.product.attribute;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record StringAttributeValue(String value) implements AttributeValue {

    public StringAttributeValue {
        if (value == null) {
            throw new DomainValidationException("Value cannot be null");
        }
    }
}
