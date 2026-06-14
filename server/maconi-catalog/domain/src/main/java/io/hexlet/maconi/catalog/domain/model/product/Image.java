package io.hexlet.maconi.catalog.domain.model.product;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import io.hexlet.maconi.shared.identifiers.ImageId;

public record Image(ImageId id, StorageKey storageKey) {

    public Image {
        if (id == null) {
            throw new DomainValidationException("ImageId cannot be null");
        }
        if (storageKey == null) {
            throw new DomainValidationException("StorageKey cannot be null");
        }
    }
}