/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;

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
