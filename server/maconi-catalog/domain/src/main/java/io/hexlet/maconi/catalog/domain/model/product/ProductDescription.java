package io.hexlet.maconi.catalog.domain.model.product;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record ProductDescription(String value) {

    public ProductDescription {
        if (value == null) {
            throw new DomainValidationException("Product description cannot be null");
        }
        value = value.trim();
    }

    public static ProductDescription empty() {
        return new ProductDescription("");
    }
}