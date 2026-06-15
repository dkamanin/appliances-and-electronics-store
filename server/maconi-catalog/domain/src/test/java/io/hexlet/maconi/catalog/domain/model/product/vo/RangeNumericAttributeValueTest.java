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

@DisplayName("RangeNumericAttributeValue")
class RangeNumericAttributeValueTest {

    @Nested
    @DisplayName("Rejects invalid input during creation")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null min value")
        void rejectsNullMin() {
            assertThatThrownBy(() -> new RangeNumericAttributeValue(null, BigDecimal.TEN))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Range bounds cannot be null");
        }

        @Test
        @DisplayName("should reject null max value")
        void rejectsNullMax() {
            assertThatThrownBy(() -> new RangeNumericAttributeValue(BigDecimal.TEN, null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Range bounds cannot be null");
        }

        @Test
        @DisplayName("should reject if min is greater than max")
        void rejectsMinGreaterThanMax() {
            BigDecimal min = BigDecimal.valueOf(20);
            BigDecimal max = BigDecimal.valueOf(10);

            assertThatThrownBy(() -> new RangeNumericAttributeValue(min, max))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Invalid range bounds: min value 20 is greater than max value 10");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid numeric boundaries")
        void createsFromValidValues() {
            BigDecimal min = BigDecimal.valueOf(10);
            BigDecimal max = BigDecimal.valueOf(20);
            RangeNumericAttributeValue range = new RangeNumericAttributeValue(min, max);

            assertThat(range.min()).isEqualTo(min);
            assertThat(range.max()).isEqualTo(max);
        }
    }

    @Nested
    @DisplayName("Method contains()")
    class ContainsMethod {

        private final RangeNumericAttributeValue range =
                new RangeNumericAttributeValue(BigDecimal.valueOf(10), BigDecimal.valueOf(20));

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> range.contains(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("Value cannot be null");
        }

        @Test
        @DisplayName("should return true if value is exactly the min boundary")
        void returnsTrueForMinBoundary() {
            assertThat(range.contains(BigDecimal.valueOf(10))).isTrue();
        }

        @Test
        @DisplayName("should return true if value is exactly the max boundary")
        void returnsTrueForMaxBoundary() {
            assertThat(range.contains(BigDecimal.valueOf(20))).isTrue();
        }

        @Test
        @DisplayName("should return true if value is strictly inside the range")
        void returnsTrueForValueInsideRange() {
            assertThat(range.contains(BigDecimal.valueOf(15))).isTrue();
        }

        @Test
        @DisplayName("should return false if value is less than min")
        void returnsFalseForValueBelowRange() {
            assertThat(range.contains(BigDecimal.valueOf(9.99))).isFalse();
        }

        @Test
        @DisplayName("should return false if value is greater than max")
        void returnsFalseForValueAboveRange() {
            assertThat(range.contains(BigDecimal.valueOf(20.01))).isFalse();
        }

        @Test
        @DisplayName("should return true if value has different scale but same numerical value")
        void returnsTrueForSameValueDifferentScale() {
            assertThat(range.contains(BigDecimal.valueOf(10.00))).isTrue();
        }
    }
}
