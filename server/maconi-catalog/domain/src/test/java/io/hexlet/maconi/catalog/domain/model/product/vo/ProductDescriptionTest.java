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

@DisplayName("ProductDescription")
class ProductDescriptionTest {
    private static final int MAX_EXPECTED_LENGTH = 5000;

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new ProductDescription(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Product description cannot be null");
        }

        @Test
        @DisplayName("should reject value exceeding 5000 characters")
        void rejectsTooLongValue() {
            String longValue = "a".repeat(MAX_EXPECTED_LENGTH + 1);

            assertThatThrownBy(() -> new ProductDescription(longValue))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage(
                            "Product description cannot exceed "
                                    + MAX_EXPECTED_LENGTH
                                    + " characters");
        }

        @Test
        @DisplayName("should reject value that exceeds 5000 characters after trimming")
        void rejectsTooLongValueAfterTrimming() {
            String longValue = "  " + "a".repeat(MAX_EXPECTED_LENGTH + 1) + "  ";

            assertThatThrownBy(() -> new ProductDescription(longValue))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage(
                            "Product description cannot exceed "
                                    + MAX_EXPECTED_LENGTH
                                    + " characters");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid string")
        void createsFromValidValue() {
            String value = "High-quality wireless headphones with noise cancellation";
            ProductDescription description = new ProductDescription(value);

            assertThat(description.value()).isEqualTo(value);
        }

        @Test
        @DisplayName("should create from empty string")
        void createsFromEmptyString() {
            ProductDescription description = new ProductDescription("");

            assertThat(description.value()).isEmpty();
        }

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            String value = "  Professional Camera  ";
            ProductDescription description = new ProductDescription(value);

            assertThat(description.value()).isEqualTo("Professional Camera");
        }

        @Test
        @DisplayName("should accept value with exactly 5000 characters")
        void acceptsMaxLengthValue() {
            String maxValue = "a".repeat(MAX_EXPECTED_LENGTH);
            ProductDescription description = new ProductDescription(maxValue);

            assertThat(description.value()).isEqualTo(maxValue);
        }

        @Test
        @DisplayName("should accept value if it exceeds 5000 characters only due to whitespace")
        void acceptsValueThatIsTooLongOnlyBecauseOfWhitespace() {
            String value = "  " + "a".repeat(MAX_EXPECTED_LENGTH) + "  ";

            ProductDescription description = new ProductDescription(value);

            assertThat(description.value()).isEqualTo("a".repeat(MAX_EXPECTED_LENGTH));
        }
    }

    @Nested
    @DisplayName("Business methods logic")
    class BusinessMethodsLogic {

        @Test
        @DisplayName("should create empty description via empty() factory method")
        void createsEmptyViaFactoryMethod() {
            ProductDescription description = ProductDescription.empty();

            assertThat(description.value()).isEmpty();
            assertThat(description.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("isEmpty() should return true for empty string")
        void isEmptyReturnsTrueForEmpty() {
            ProductDescription description = new ProductDescription("");

            assertThat(description.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("isEmpty() should return true for blank string (due to trimming)")
        void isEmptyReturnsTrueForBlank() {
            ProductDescription description = new ProductDescription("   ");

            assertThat(description.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("isEmpty() should return false for non-empty string")
        void isEmptyReturnsFalseForNonEmpty() {
            ProductDescription description = new ProductDescription("Available in black");

            assertThat(description.isEmpty()).isFalse();
        }
    }
}
