package io.hexlet.maconi.catalog.domain.model.product;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

public record StorageKey(String value) {

    public StorageKey {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("Storage key cannot be null or blank");
        }
        value = value.trim();
    }
}