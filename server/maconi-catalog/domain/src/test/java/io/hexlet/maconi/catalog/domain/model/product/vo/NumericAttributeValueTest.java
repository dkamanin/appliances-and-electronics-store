/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("NumericAttributeValue")
class NumericAttributeValueTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new NumericAttributeValue(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Value cannot be null");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid decimal value")
        void createsFromValidValue() {
            BigDecimal value = new BigDecimal("123.45");
            NumericAttributeValue numericAttributeValue = new NumericAttributeValue(value);

            assertThat(numericAttributeValue.value()).isEqualTo(value);
        }
    }
}
