/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Image")
class ImageTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null ImageId")
        void rejectsNullId() {
            StorageKey key = new StorageKey("path/to/img.jpg");

            assertThatThrownBy(() -> new Image(null, key))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("ImageId cannot be null");
        }

        @Test
        @DisplayName("should reject null StorageKey")
        void rejectsNullStorageKey() {
            ImageId id = ImageId.generate();

            assertThatThrownBy(() -> new Image(id, null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("StorageKey cannot be null");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create Image when both components are provided")
        void createsValidImage() {
            ImageId id = ImageId.generate();
            StorageKey key = new StorageKey("path/to/img.jpg");

            Image image = new Image(id, key);

            assertThat(image.id()).isEqualTo(id);
            assertThat(image.storageKey()).isEqualTo(key);
        }
    }
}
