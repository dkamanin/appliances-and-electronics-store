/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.identifiers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("OrderNumber")
class OrderNumberTest {

    private static final UUID FIXED_UUID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");
    private static final OrderId FIXED_ORDER_ID = new OrderId(FIXED_UUID);

    private static final String VALID_VALUE = "ORD-1A2B3C4D";
    private static final String VALID_VALUE_LOWER = "ord-1a2b3c4d";
    private static final String VALUE_WITH_LOWER_PREFIX = "ord-1A2B3C4D";
    private static final String VALUE_WITH_LOWER_BODY = "ORD-1a2b3c4d";

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null value")
        void rejectsNullValue() {
            assertThatThrownBy(() -> new OrderNumber(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage("value must not be null");
        }

        @Test
        @DisplayName("should reject empty value")
        void rejectsEmptyValue() {
            assertThatThrownBy(() -> new OrderNumber(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Order number must have exactly 12 characters");
        }

        @Test
        @DisplayName("should reject blank value")
        void rejectsBlankValue() {
            String stringWithTwelveSpaces = "            ";
            assertThatThrownBy(() -> new OrderNumber(stringWithTwelveSpaces))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Order number must start with 'ORD-'");
        }

        @Test
        @DisplayName("should reject value with incorrect length")
        void rejectsIncorrectLength() {
            assertThatThrownBy(() -> new OrderNumber("ORD-123"))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Order number must have exactly 12 characters");
        }

        @Test
        @DisplayName("should reject value without 'ORD-' prefix")
        void rejectsMissingPrefix() {
            assertThatThrownBy(() -> new OrderNumber("123456789012"))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Order number must start with 'ORD-'");
        }

        @Test
        @DisplayName("should reject lowercase prefix")
        void rejectsLowercasePrefix() {
            assertThatThrownBy(() -> new OrderNumber(VALUE_WITH_LOWER_PREFIX))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Order number must start with 'ORD-'");
        }

        @Test
        @DisplayName("should reject lowercase characters in body")
        void rejectsLowercaseBody() {
            assertThatThrownBy(() -> new OrderNumber(VALUE_WITH_LOWER_BODY))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Order number body must contain only uppercase letters and digits");
        }

        @Test
        @DisplayName("should reject null OrderId during creation")
        void rejectsNullOrderId() {
            assertThatThrownBy(() -> OrderNumber.create(null))
                    .isExactlyInstanceOf(NullPointerException.class)
                    .hasMessage("orderId must not be null");
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid value via constructor")
        void createsFromValidValueViaConstructor() {
            OrderNumber orderNumber = new OrderNumber(VALID_VALUE);

            assertThat(orderNumber.value()).isEqualTo(VALID_VALUE);
        }

        @Test
        @DisplayName("should create from lowercase value via factory method with normalization")
        void createsFromValueViaFactory() {
            OrderNumber orderNumber = OrderNumber.of(VALID_VALUE_LOWER);

            assertThat(orderNumber.value()).isEqualTo(VALID_VALUE);
        }

        @Test
        @DisplayName("should generate OrderNumber with correct format")
        void generatesCorrectFormat() {
            OrderNumber result = OrderNumber.create(FIXED_ORDER_ID);

            assertThat(result.value()).startsWith("ORD-").hasSize(12);
        }

        @Test
        @DisplayName("should always uppercase the generated part")
        void alwaysUppercasesGeneratedPart() {
            OrderNumber result = OrderNumber.create(FIXED_ORDER_ID);

            assertThat(result.value()).isEqualTo("ORD-A1B2C3D4");
        }

        @Test
        @DisplayName("should use first 8 characters of OrderId value")
        void usesFirstEightCharacters() {
            OrderNumber result = OrderNumber.create(FIXED_ORDER_ID);

            assertThat(result.value()).isEqualTo("ORD-A1B2C3D4");
        }

        @Test
        @DisplayName("should accept valid lowercase value via factory method")
        void acceptsValidLowercaseValueViaOf() {
            OrderNumber orderNumber = OrderNumber.of(VALID_VALUE_LOWER);

            assertThat(orderNumber.value()).isEqualTo(VALID_VALUE);
        }

        @Test
        @DisplayName("should accept valid uppercase value")
        void acceptsValidUppercaseValue() {
            OrderNumber orderNumber = new OrderNumber(VALID_VALUE);

            assertThat(orderNumber.value()).isEqualTo(VALID_VALUE);
        }
    }

    @Nested
    @DisplayName("Generation")
    class Generation {

        @Test
        @DisplayName("should generate OrderNumber with correct format from OrderId")
        void generatesCorrectFormatFromOrderId() {
            OrderNumber result = OrderNumber.create(FIXED_ORDER_ID);

            assertThat(result.value()).startsWith("ORD-").hasSize(12).isUpperCase();
        }

        @Test
        @DisplayName("should derive value from first 8 characters of OrderId")
        void derivesValueFromOrderId() {
            OrderNumber result = OrderNumber.create(FIXED_ORDER_ID);

            // FIXED_UUID starts with a1b2c3d4 -> expected ORD-A1B2C3D4
            assertThat(result.value()).isEqualTo("ORD-A1B2C3D4");
        }
    }

    @Nested
    @DisplayName("Equality")
    class Equality {

        @Test
        @DisplayName("should be equal when they have the same value")
        void areEqualWhenValuesAreTheSame() {
            OrderNumber number1 = new OrderNumber(VALID_VALUE);
            OrderNumber number2 = new OrderNumber(VALID_VALUE);

            assertThat(number1).isEqualTo(number2).hasSameHashCodeAs(number2);
        }

        @Test
        @DisplayName("should not be equal when they have different values")
        void areNotEqualWhenValuesDiffer() {
            OrderNumber number1 = new OrderNumber("ORD-11111111");
            OrderNumber number2 = new OrderNumber("ORD-22222222");

            assertThat(number1).isNotEqualTo(number2);
        }
    }
}
