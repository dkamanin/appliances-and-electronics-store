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

@DisplayName("BaseName")
class BaseNameTest {
    private static final int MAX_EXPECTED_LENGTH = 255;

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new BaseName(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Base name cannot be null or blank");
        }

        @Test
        @DisplayName("should reject empty string")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> new BaseName(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Base name cannot be null or blank");
        }

        @Test
        @DisplayName("should reject blank string")
        void rejectsBlankString() {
            assertThatThrownBy(() -> new BaseName("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Base name cannot be null or blank");
        }

        @Test
        @DisplayName("should reject value exceeding 255 characters")
        void rejectsTooLongValue() {
            String longValue = "a".repeat(MAX_EXPECTED_LENGTH + 1);

            assertThatThrownBy(() -> new BaseName(longValue))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Base name cannot exceed 255 characters");
        }

        @Test
        @DisplayName("should reject value that exceeds 255 characters after trimming")
        void rejectsTooLongValueAfterTrimming() {
            String longValue = "  " + "a".repeat(MAX_EXPECTED_LENGTH + 1) + "  ";

            assertThatThrownBy(() -> new BaseName(longValue))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Base name cannot exceed 255 characters");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid string")
        void createsFromValidValue() {
            String value = "Smartphone";
            BaseName baseName = new BaseName(value);

            assertThat(baseName.value()).isEqualTo(value);
        }

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            String value = "  Laptop  ";
            BaseName baseName = new BaseName(value);

            assertThat(baseName.value()).isEqualTo("Laptop");
        }

        @Test
        @DisplayName("should accept value with exactly 255 characters")
        void acceptsMaxLengthValue() {
            String maxValue = "a".repeat(MAX_EXPECTED_LENGTH);
            BaseName baseName = new BaseName(maxValue);

            assertThat(baseName.value()).isEqualTo(maxValue);
        }

        @Test
        @DisplayName("should accept value if it exceeds 255 characters only due to whitespace")
        void acceptsValueThatIsTooLongOnlyBecauseOfWhitespace() {
            String value = "  " + "a".repeat(MAX_EXPECTED_LENGTH) + "  ";

            BaseName baseName = new BaseName(value);

            assertThat(baseName.value()).isEqualTo("a".repeat(255));
        }
    }
}
