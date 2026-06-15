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

@DisplayName("StringAttributeValue")
class StringAttributeValueTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new StringAttributeValue(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Value cannot be null or blank");
        }

        @Test
        @DisplayName("should reject empty string")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> new StringAttributeValue(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Value cannot be null or blank");
        }

        @Test
        @DisplayName("should reject blank string")
        void rejectsBlankString() {
            assertThatThrownBy(() -> new StringAttributeValue("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Value cannot be null or blank");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid string")
        void createsFromValidValue() {
            String value = "Red";
            StringAttributeValue attributeValue = new StringAttributeValue(value);

            assertThat(attributeValue.value()).isEqualTo(value);
        }

        @Test
        @DisplayName("should trim leading and trailing whitespace")
        void trimsWhitespace() {
            String value = "  Blue  ";
            StringAttributeValue attributeValue = new StringAttributeValue(value);

            assertThat(attributeValue.value()).isEqualTo("Blue");
        }
    }
}
