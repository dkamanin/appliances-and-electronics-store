/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.brand.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("BrandName")
class BrandNameTest {

    private static final int MAX_LENGTH = 255;

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("should reject null")
        void rejectsNull() {
            assertThatThrownBy(() -> new BrandName(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand name cannot be null or blank");
        }

        @Test
        @DisplayName("should reject blank value")
        void rejectsBlankValue() {
            assertThatThrownBy(() -> new BrandName(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand name cannot be null or blank");

            assertThatThrownBy(() -> new BrandName("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand name cannot be null or blank");
        }

        @Test
        @DisplayName("should reject value longer than maximum length")
        void rejectsWhenLongerThanMaximum() {
            String tooLong = "a".repeat(MAX_LENGTH + 1);

            assertThatThrownBy(() -> new BrandName(tooLong))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand name cannot exceed " + MAX_LENGTH + " characters");
        }

        @Test
        @DisplayName("should reject value that exceeds limit even after trimming")
        void rejectsWhenLongerThanMaximumAfterTrimming() {
            String value = "   " + "a".repeat(MAX_LENGTH + 1) + "   ";

            assertThatThrownBy(() -> new BrandName(value))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand name cannot exceed " + MAX_LENGTH + " characters");
        }
    }

    @Nested
    @DisplayName("Normalization")
    class Normalization {

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            BrandName brandName = new BrandName("  Apple Inc.  ");

            assertThat(brandName.value()).isEqualTo("Apple Inc.");
        }

        @Test
        @DisplayName("should trim before applying length validation")
        void trimsBeforeLengthValidation() {
            String value = "   " + "a".repeat(MAX_LENGTH) + "   ";

            BrandName brandName = new BrandName(value);

            assertThat(brandName.value()).hasSize(MAX_LENGTH);
        }

        @Test
        @DisplayName("should accept value with exactly maximum length")
        void acceptsExactlyMaximumLength() {
            String value = "a".repeat(MAX_LENGTH);

            BrandName brandName = new BrandName(value);

            assertThat(brandName.value()).hasSize(MAX_LENGTH);
        }

        @Test
        @DisplayName("should be equal when values become the same after normalization")
        void shouldBeEqualAfterNormalization() {
            BrandName name1 = new BrandName("  Samsung  ");
            BrandName name2 = new BrandName("Samsung");

            assertThat(name1).isEqualTo(name2);
        }
    }
}
