/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ImageId")
class ImageIdTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null UUID in constructor")
        void rejectsNullUuid() {
            assertThatThrownBy(() -> new ImageId(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Image identifier must not be null");
        }

        @Test
        @DisplayName("should reject empty string in of() method")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> ImageId.of(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Image identifier must not be blank");
        }

        @Test
        @DisplayName("should reject blank string in of() method")
        void rejectsBlankString() {
            assertThatThrownBy(() -> ImageId.of("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Image identifier must not be blank");
        }

        @Test
        @DisplayName("should reject malformed UUID string in of() method")
        void rejectsMalformedUuid() {
            String invalidUuid = "not-a-uuid-12345";

            assertThatThrownBy(() -> ImageId.of(invalidUuid))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Malformed UUID: " + invalidUuid);
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid UUID")
        void createsFromValidUuid() {
            UUID uuid = UUID.randomUUID();
            ImageId imageId = new ImageId(uuid);

            assertThat(imageId.value()).isEqualTo(uuid);
        }

        @Test
        @DisplayName("should create from valid UUID string")
        void createsFromValidString() {
            String uuidString = UUID.randomUUID().toString();
            ImageId imageId = ImageId.of(uuidString);

            assertThat(imageId.value()).isEqualTo(UUID.fromString(uuidString));
        }

        @Test
        @DisplayName("should generate random ImageId")
        void generatesRandomId() {
            ImageId imageId = ImageId.generate();

            assertThat(imageId).isNotNull();
            assertThat(imageId.value()).isNotNull();
        }
    }
}
