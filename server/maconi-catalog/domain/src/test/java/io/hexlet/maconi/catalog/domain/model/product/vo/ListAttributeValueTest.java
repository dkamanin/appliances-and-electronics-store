/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.product.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("ListAttributeValue")
class ListAttributeValueTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null list")
        void rejectsNullList() {
            assertThatThrownBy(() -> new ListAttributeValue(null))
                    .isInstanceOf(DomainValidationException.class);
        }

        @Test
        @DisplayName("should reject list containing null elements")
        void rejectsListWithNullElements() {
            List<String> values = Arrays.asList("Valid", null, "Valid");

            assertThatThrownBy(() -> new ListAttributeValue(values))
                    .isInstanceOf(DomainValidationException.class);
        }

        @Test
        @DisplayName("should reject list containing empty strings")
        void rejectsListWithEmptyStrings() {
            List<String> values = List.of("Valid", "");

            assertThatThrownBy(() -> new ListAttributeValue(values))
                    .isInstanceOf(DomainValidationException.class);
        }

        @Test
        @DisplayName("should reject list containing blank strings")
        void rejectsListWithBlankStrings() {
            List<String> values = List.of("Valid", "   ");

            assertThatThrownBy(() -> new ListAttributeValue(values))
                    .isInstanceOf(DomainValidationException.class);
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid list of strings")
        void createsFromValidValues() {
            List<String> input = List.of("Value1", "Value2");
            ListAttributeValue result = new ListAttributeValue(input);

            assertThat(result.values()).containsExactly("Value1", "Value2");
        }

        @Test
        @DisplayName("should trim all elements in the list")
        void trimsAllElements() {
            List<String> input = List.of("  Value1  ", "Value2  ", "  Value3");
            ListAttributeValue result = new ListAttributeValue(input);

            assertThat(result.values()).containsExactly("Value1", "Value2", "Value3");
        }
    }

    @Nested
    @DisplayName("Immutability and Integrity")
    class ImmutabilityAndIntegrity {

        @Test
        @DisplayName(
                "should be immutable and throw exception when attempting to modify returned list")
        void returnedListIsImmutable() {
            List<String> input = List.of("A", "B");
            ListAttributeValue result = new ListAttributeValue(input);
            List<String> values = result.values();

            assertThatThrownBy(() -> values.add("C"))
                    .isInstanceOf(UnsupportedOperationException.class);
        }

        @Test
        @DisplayName("should not be affected by modifications of the original input list")
        void createsDefensiveCopy() {
            List<String> input = new ArrayList<>();
            input.add("A");
            input.add("B");

            ListAttributeValue result = new ListAttributeValue(input);
            input.add("C");

            assertThat(result.values()).containsExactly("A", "B");
            assertThat(result.values()).doesNotContain("C");
        }
    }
}
