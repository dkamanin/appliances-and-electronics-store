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

@DisplayName("StorageKey")
class StorageKeyTest {
    private static final int MAX_LENGTH = 1024;

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("should reject null")
        void rejectsNull() {
            assertThatThrownBy(() -> new StorageKey(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot be null");
        }

        @Test
        @DisplayName("should reject blank value")
        void rejectsBlank() {
            assertThatThrownBy(() -> new StorageKey("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot be blank");
        }

        @Test
        @DisplayName("should reject empty string")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> new StorageKey(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot be blank");
        }

        @Test
        @DisplayName("should reject value longer than maximum length")
        void rejectsWhenLongerThanMaximum() {
            String tooLong = "a".repeat(MAX_LENGTH + 1);

            assertThatThrownBy(() -> new StorageKey(tooLong))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot exceed " + MAX_LENGTH + " characters");
        }

        @Test
        @DisplayName("should reject value that exceeds limit even after trimming")
        void rejectsWhenLongerThanMaximumAfterTrimming() {
            String value = "   " + "a".repeat(MAX_LENGTH + 1) + "   ";

            assertThatThrownBy(() -> new StorageKey(value))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Storage key cannot exceed " + MAX_LENGTH + " characters");
        }
    }

    @Nested
    @DisplayName("Normalization")
    class Normalization {

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            StorageKey key = new StorageKey("  /images/products/oneplus-15.jpg  ");

            assertThat(key.value()).isEqualTo("/images/products/oneplus-15.jpg");
        }

        @Test
        @DisplayName("should trim before applying length validation")
        void trimsBeforeLengthValidation() {
            String value = "   " + "a".repeat(MAX_LENGTH) + "   ";

            StorageKey key = new StorageKey(value);

            assertThat(key.value()).hasSize(MAX_LENGTH);
        }

        @Test
        @DisplayName("should accept value with exactly maximum length")
        void acceptsExactlyMaximumLength() {
            String value = "a".repeat(MAX_LENGTH);

            StorageKey key = new StorageKey(value);

            assertThat(key.value()).hasSize(MAX_LENGTH);
        }

        @Test
        @DisplayName("should be equal when values become the same after normalization")
        void shouldBeEqualAfterNormalization() {
            StorageKey key1 = new StorageKey("  /images/products/oneplus-15.jpg  ");
            StorageKey key2 = new StorageKey("/images/products/oneplus-15.jpg");

            assertThat(key1).isEqualTo(key2);
        }
    }
}
