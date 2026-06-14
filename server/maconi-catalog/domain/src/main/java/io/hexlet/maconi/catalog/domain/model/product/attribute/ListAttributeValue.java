package io.hexlet.maconi.catalog.domain.model.product.attribute;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

import java.util.List;

public record ListAttributeValue(List<String> values) implements AttributeValue {
    public ListAttributeValue {
        if (values == null) {
            throw new DomainValidationException("Value list cannot be null");
        }
        if (values.contains(null)) {
            throw new DomainValidationException("Value list elements cannot be null");
        }
        List<String> cleaned = values.stream()
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();
        if (cleaned.isEmpty()) {
            throw new DomainValidationException("List attribute elements cannot be empty");
        }
        values = List.copyOf(values);
    }
}