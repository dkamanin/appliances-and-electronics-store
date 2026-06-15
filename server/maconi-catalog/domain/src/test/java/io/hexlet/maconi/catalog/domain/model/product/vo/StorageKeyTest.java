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

@DisplayName("StorageKey")
class StorageKeyTest {
    private static final int MAX_EXPECTED_LENGTH = 1024;

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new StorageKey(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot be null or blank");
        }

        @Test
        @DisplayName("should reject empty string")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> new StorageKey(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot be null or blank");
        }

        @Test
        @DisplayName("should reject blank string")
        void rejectsBlankString() {
            assertThatThrownBy(() -> new StorageKey("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot be null or blank");
        }

        @Test
        @DisplayName("should reject value exceeding 1024 characters")
        void rejectsTooLongValue() {
            String longValue = "a".repeat(MAX_EXPECTED_LENGTH + 1);

            assertThatThrownBy(() -> new StorageKey(longValue))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot exceed " + MAX_EXPECTED_LENGTH + " characters");
        }

        @Test
        @DisplayName("should reject value that exceeds 1024 characters after trimming")
        void rejectsTooLongValueAfterTrimming() {
            String longValue = "  " + "a".repeat(MAX_EXPECTED_LENGTH + 1) + "  ";

            assertThatThrownBy(() -> new StorageKey(longValue))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot exceed " + MAX_EXPECTED_LENGTH + " characters");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid string")
        void createsFromValidValue() {
            String value = "/products/smartphones/oneplus-15.jpg";
            StorageKey storageKey = new StorageKey(value);

            assertThat(storageKey.value()).isEqualTo(value);
        }

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            String value = "  /products/smartphones/oneplus-15.jpg  ";
            StorageKey storageKey = new StorageKey(value);

            assertThat(storageKey.value()).isEqualTo("/products/smartphones/oneplus-15.jpg");
        }

        @Test
        @DisplayName("should accept value with exactly 1024 characters")
        void acceptsMaxLengthValue() {
            String maxValue = "a".repeat(MAX_EXPECTED_LENGTH);
            StorageKey storageKey = new StorageKey(maxValue);

            assertThat(storageKey.value()).isEqualTo(maxValue);
        }

        @Test
        @DisplayName("should accept value if it exceeds 1024 characters only due to whitespace")
        void acceptsValueThatIsTooLongOnlyBecauseOfWhitespace() {
            String value = "  " + "a".repeat(MAX_EXPECTED_LENGTH) + "  ";

            StorageKey storageKey = new StorageKey(value);

            assertThat(storageKey.value()).isEqualTo("a".repeat(MAX_EXPECTED_LENGTH));
        }
    }
}
