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

@DisplayName("BrandDescription")
class BrandDescriptionTest {

    private static final int MAX_LENGTH = 2000;

    @Nested
    @DisplayName("Validation")
    class Validation {

        @Test
        @DisplayName("should reject null")
        void rejectsNull() {
            assertThatThrownBy(() -> new BrandDescription(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand description cannot be null");
        }

        @Test
        @DisplayName("should reject value longer than maximum length")
        void rejectsWhenLongerThanMaximum() {
            String tooLong = "a".repeat(MAX_LENGTH + 1);

            assertThatThrownBy(() -> new BrandDescription(tooLong))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand description cannot exceed " + MAX_LENGTH + " characters");
        }

        @Test
        @DisplayName("should reject value that exceeds limit even after trimming")
        void rejectsWhenLongerThanMaximumAfterTrimming() {
            String value = "   " + "a".repeat(MAX_LENGTH + 1) + "   ";

            assertThatThrownBy(() -> new BrandDescription(value))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand description cannot exceed " + MAX_LENGTH + " characters");
        }

        @Test
        @DisplayName("should accept empty string")
        void acceptsEmptyString() {
            BrandDescription description = new BrandDescription("");

            assertThat(description.value()).isEmpty();
            assertThat(description.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Normalization")
    class Normalization {

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            BrandDescription description = new BrandDescription("  High quality products  ");

            assertThat(description.value()).isEqualTo("High quality products");
        }

        @Test
        @DisplayName("should trim before applying length validation")
        void trimsBeforeLengthValidation() {
            String value = "   " + "a".repeat(MAX_LENGTH) + "   ";

            BrandDescription description = new BrandDescription(value);

            assertThat(description.value()).hasSize(MAX_LENGTH);
        }

        @Test
        @DisplayName("should be equal when values become the same after normalization")
        void shouldBeEqualAfterNormalization() {
            BrandDescription desc1 = new BrandDescription("  Premium quality  ");
            BrandDescription desc2 = new BrandDescription("Premium quality");

            assertThat(desc1).isEqualTo(desc2);
        }

        @Test
        @DisplayName("should normalize whitespace-only value to empty")
        void normalizesWhitespaceOnlyToEmpty() {
            BrandDescription description = new BrandDescription("   \t\n   ");

            assertThat(description.value()).isEmpty();
            assertThat(description.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("should accept value with exactly maximum length")
        void acceptsExactlyMaximumLength() {
            String value = "a".repeat(MAX_LENGTH);

            BrandDescription description = new BrandDescription(value);

            assertThat(description.value()).hasSize(MAX_LENGTH);
        }
    }

    @Nested
    @DisplayName("Query Methods")
    class QueryMethods {

        @Test
        @DisplayName("should return true for empty description")
        void returnsTrueForEmpty() {
            BrandDescription description = BrandDescription.empty();

            assertThat(description.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("should return false for non-empty description")
        void returnsFalseForNonEmpty() {
            BrandDescription description = new BrandDescription("Some description");

            assertThat(description.isEmpty()).isFalse();
        }
    }
}
