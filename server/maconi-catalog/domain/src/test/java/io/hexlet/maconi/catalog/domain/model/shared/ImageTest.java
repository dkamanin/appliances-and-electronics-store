/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.shared;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Image")
class ImageTest {

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("should reject null ImageId")
        void rejectsNullId() {
            StorageKey storageKey = new StorageKey("path/to/img.jpg");

            assertThatThrownBy(() -> new Image(null, storageKey))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("ImageId cannot be null");
        }

        @Test
        @DisplayName("should reject null StorageKey")
        void rejectsNullStorageKey() {
            ImageId imageId = ImageId.generate();

            assertThatThrownBy(() -> new Image(imageId, null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("StorageKey cannot be null");
        }
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create Image when both components are valid")
        void createsImage() {
            ImageId imageId = ImageId.generate();
            StorageKey storageKey = new StorageKey("path/to/img.jpg");

            Image image = new Image(imageId, storageKey);

            assertThat(image.id()).isEqualTo(imageId);
            assertThat(image.storageKey()).isEqualTo(storageKey);
        }
    }
}
