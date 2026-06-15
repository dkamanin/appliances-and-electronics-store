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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("SkuAttributes")
class SkuAttributesTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null map")
        void rejectsNullMap() {
            assertThatThrownBy(() -> new SkuAttributes(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Attributes map cannot be null");
        }

        @Test
        @DisplayName("should reject map containing null key")
        void rejectsNullKey() {
            Map<String, AttributeValue> values = new HashMap<>();
            values.put(null, new StringAttributeValue("Value"));

            assertThatThrownBy(() -> new SkuAttributes(values))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Attributes map cannot contains null key or value");
        }

        @Test
        @DisplayName("should reject map containing null value")
        void rejectsNullValue() {
            Map<String, AttributeValue> values = new HashMap<>();
            values.put("color", null);

            assertThatThrownBy(() -> new SkuAttributes(values))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Attributes map cannot contains null key or value");
        }
    }

    @Nested
    @DisplayName("Immutability and Factory methods")
    class ImmutabilityAndFactory {

        @Test
        @DisplayName("should return an empty immutable map when calling empty()")
        void emptyReturnsEmptyImmutableMap() {
            SkuAttributes attributes = SkuAttributes.empty();

            assertThat(attributes.isEmpty()).isTrue();
            assertThat(attributes.values()).isEmpty();
        }

        @Test
        @DisplayName("should prevent modification of the internal map")
        void preventsModification() {
            Map<String, AttributeValue> input = new HashMap<>();
            input.put("size", new StringAttributeValue("XL"));
            SkuAttributes attributes = new SkuAttributes(input);

            assertThatThrownBy(
                            () -> attributes.values().put("color", new StringAttributeValue("Red")))
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    @Nested
    @DisplayName("Basic queries")
    class BasicQueries {

        @Test
        @DisplayName("should return true for isEmpty when map is empty")
        void isEmptyTrueForEmptyMap() {
            SkuAttributes attributes = SkuAttributes.empty();
            assertThat(attributes.isEmpty()).isTrue();
        }

        @Test
        @DisplayName("should return false for isEmpty when map has values")
        void isEmptyFalseForNonEmptyMap() {
            SkuAttributes attributes =
                    new SkuAttributes(Map.of("color", new StringAttributeValue("Red")));
            assertThat(attributes.isEmpty()).isFalse();
        }

        @Test
        @DisplayName("should return true if key exists")
        void containsKeyTrueIfPresent() {
            SkuAttributes attributes =
                    new SkuAttributes(Map.of("color", new StringAttributeValue("Red")));
            assertThat(attributes.containsKey("color")).isTrue();
        }

        @Test
        @DisplayName("should return false if key is missing")
        void containsKeyFalseIfMissing() {
            SkuAttributes attributes =
                    new SkuAttributes(Map.of("color", new StringAttributeValue("Red")));
            assertThat(attributes.containsKey("size")).isFalse();
        }

        @Test
        @DisplayName("should throw NPE when containsKey is called with null")
        void containsKeyThrowsNpeOnNull() {
            SkuAttributes attributes = SkuAttributes.empty();
            assertThatThrownBy(() -> attributes.containsKey(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("Key cannot be null");
        }
    }

    @Nested
    @DisplayName("Value retrieval")
    class ValueRetrieval {

        private final SkuAttributes attributes =
                new SkuAttributes(
                        Map.of(
                                "color", new StringAttributeValue("Red"),
                                "weight", new NumericAttributeValue(new BigDecimal("1.5")),
                                "is_fragile", new BooleanAttributeValue(true),
                                "range",
                                        new RangeNumericAttributeValue(
                                                new BigDecimal("10"), new BigDecimal("20")),
                                "tags", new ListAttributeValue(java.util.List.of("new", "sale"))));

        @Test
        @DisplayName("should throw NPE when get is called with null")
        void getThrowsNpeOnNull() {
            assertThatThrownBy(() -> attributes.get(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("Key cannot be null");
        }

        @Test
        @DisplayName("should return value if key exists")
        void getReturnsValueIfPresent() {
            Optional<AttributeValue> result = attributes.get("color");
            assertThat(result).isPresent().contains(new StringAttributeValue("Red"));
        }

        @Test
        @DisplayName("should return empty Optional if key is missing")
        void getReturnsEmptyIfMissing() {
            assertThat(attributes.get("unknown")).isEmpty();
        }

        @Test
        @DisplayName("should correctly retrieve string attribute")
        void getAsStringSuccess() {
            assertThat(attributes.getAsString("color")).contains("Red");
            assertThat(attributes.getAsString("weight")).isEmpty(); // Wrong type
            assertThat(attributes.getAsString("unknown")).isEmpty(); // Missing
        }

        @Test
        @DisplayName("should correctly retrieve numeric attribute")
        void getAsNumericSuccess() {
            assertThat(attributes.getAsNumeric("weight")).contains(new BigDecimal("1.5"));
            assertThat(attributes.getAsNumeric("color")).isEmpty(); // Wrong type
            assertThat(attributes.getAsNumeric("unknown")).isEmpty(); // Missing
        }

        @Test
        @DisplayName("should correctly retrieve boolean attribute")
        void getAsBooleanSuccess() {
            assertThat(attributes.getAsBoolean("is_fragile")).contains(true);
            assertThat(attributes.getAsBoolean("weight")).isEmpty(); // Wrong type
            assertThat(attributes.getAsBoolean("unknown")).isEmpty(); // Missing
        }

        @Test
        @DisplayName("should correctly retrieve range numeric attribute")
        void getAsRangeNumericSuccess() {
            Optional<RangeNumericAttributeValue> result = attributes.getAsRangeNumeric("range");
            assertThat(result).isPresent();
            assertThat(result.get()).isInstanceOf(RangeNumericAttributeValue.class);

            assertThat(attributes.getAsRangeNumeric("color")).isEmpty(); // Wrong type
            assertThat(attributes.getAsRangeNumeric("unknown")).isEmpty(); // Missing
        }

        @Test
        @DisplayName("should correctly retrieve list attribute")
        void getAsListSuccess() {
            Optional<ListAttributeValue> result = attributes.getAsList("tags");
            assertThat(result).isPresent();
            assertThat(result.get()).isInstanceOf(ListAttributeValue.class);

            assertThat(attributes.getAsList("color")).isEmpty(); // Wrong type
            assertThat(attributes.getAsList("unknown")).isEmpty(); // Missing
        }
    }
}
