package io.hexlet.maconi.catalog.domain.model.product;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public record BaseName(String value) {
    public BaseName {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException("Base name cannot be null or blank");
        }
        String normalized = value.trim();
        if (normalized.length() > 255) {
            throw new DomainValidationException("Base name cannot exceed 255 characters");
        }
        value = normalized;
    }
}