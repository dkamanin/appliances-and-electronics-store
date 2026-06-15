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

@DisplayName("BooleanAttributeValue")
class BooleanAttributeValueTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new BooleanAttributeValue(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Value cannot be null");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from true value")
        void createsFromTrueValue() {
            Boolean value = true;
            BooleanAttributeValue attributeValue = new BooleanAttributeValue(value);

            assertThat(attributeValue.value()).isEqualTo(value);
        }

        @Test
        @DisplayName("should create from false value")
        void createsFromFalseValue() {
            Boolean value = false;
            BooleanAttributeValue attributeValue = new BooleanAttributeValue(value);

            assertThat(attributeValue.value()).isEqualTo(value);
        }
    }
}
