/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.shared;

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
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("should reject null UUID")
        void rejectsNull() {
            assertThatThrownBy(() -> new ImageId(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Image identifier must not be null");
        }

        @Test
        @DisplayName("should reject blank string in of()")
        void rejectsBlankString() {
            assertThatThrownBy(() -> ImageId.of(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Image identifier must not be blank");

            assertThatThrownBy(() -> ImageId.of("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Image identifier must not be blank");
        }

        @Test
        @DisplayName("should reject malformed UUID string")
        void rejectsMalformedUuid() {
            String invalid = "not-a-uuid";

            assertThatThrownBy(() -> ImageId.of(invalid))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Malformed UUID: " + invalid);
        }
    }

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create from UUID")
        void createsFromUuid() {
            UUID uuid = UUID.randomUUID();
            ImageId imageId = new ImageId(uuid);

            assertThat(imageId.value()).isEqualTo(uuid);
        }

        @Test
        @DisplayName("should create from valid UUID string")
        void createsFromString() {
            String uuidStr = UUID.randomUUID().toString();
            ImageId imageId = ImageId.of(uuidStr);

            assertThat(imageId.value()).isEqualTo(UUID.fromString(uuidStr));
        }

        @Test
        @DisplayName("should generate unique random ImageId")
        void generatesUniqueRandomId() {
            ImageId id1 = ImageId.generate();
            ImageId id2 = ImageId.generate();

            assertThat(id1).isNotEqualTo(id2);
        }
    }

    @Nested
    @DisplayName("Equality")
    class Equality {

        @Test
        @DisplayName("should be equal when underlying UUID is the same")
        void shouldBeEqualWhenUuidIsTheSame() {
            UUID uuid = UUID.randomUUID();
            ImageId id1 = new ImageId(uuid);
            ImageId id2 = new ImageId(uuid);

            assertThat(id1).isEqualTo(id2);
            assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
        }

        @Test
        @DisplayName("should not be equal when UUIDs are different")
        void shouldNotBeEqualWhenUuidsDiffer() {
            ImageId id1 = new ImageId(UUID.randomUUID());
            ImageId id2 = new ImageId(UUID.randomUUID());

            assertThat(id1).isNotEqualTo(id2);
        }
    }
}
