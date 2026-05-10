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

@DisplayName("CategoryId")
class CategoryIdTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null UUID")
        void rejectsNullUuid() {
            assertThatThrownBy(() -> new CategoryId(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Category identifier must not be null");
        }

        @Test
        @DisplayName("should reject null string")
        void rejectsNullString() {
            assertThatThrownBy(() -> CategoryId.of(null))
                    .isExactlyInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("should reject empty string")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> CategoryId.of(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Category identifier must not be blank");
        }

        @Test
        @DisplayName("should reject blank string")
        void rejectsBlankString() {
            assertThatThrownBy(() -> CategoryId.of("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Category identifier must not be blank");
        }

        @Test
        @DisplayName("should reject malformed UUID string")
        void rejectsMalformedUuidString() {
            String malformed = "not-a-uuid";

            assertThatThrownBy(() -> CategoryId.of(malformed))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Malformed UUID: " + malformed)
                    .cause()
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Creation from valid input")
    class CreationFromValidInput {

        @Test
        @DisplayName("should create from valid UUID via constructor")
        void createsFromValidUuidViaConstructor() {
            UUID uuid = UUID.randomUUID();

            CategoryId categoryId = new CategoryId(uuid);

            assertThat(categoryId.value()).isEqualTo(uuid);
        }

        @Test
        @DisplayName("should create from valid UUID string via factory method")
        void createsFromValidUuidStringViaFactory() {
            UUID uuid = UUID.randomUUID();

            CategoryId categoryId = CategoryId.of(uuid.toString());

            assertThat(categoryId.value()).isEqualTo(uuid);
        }
    }

    @Nested
    @DisplayName("Generation")
    class Generation {

        @Test
        @DisplayName("should generate valid CategoryId")
        void generatesValidCategoryId() {
            CategoryId categoryId = CategoryId.generate();

            assertThat(categoryId).isNotNull();
            assertThat(categoryId.value()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Equality")
    class Equality {

        @Test
        @DisplayName("should be equal when they have the same value")
        void areEqualWhenValuesAreTheSame() {
            UUID uuid = UUID.randomUUID();
            CategoryId id1 = new CategoryId(uuid);
            CategoryId id2 = CategoryId.of(uuid.toString());

            assertThat(id1).isEqualTo(id2).hasSameHashCodeAs(id2);
        }

        @Test
        @DisplayName("should not be equal when they have different values")
        void areNotEqualWhenValuesDiffer() {
            CategoryId id1 = CategoryId.generate();
            CategoryId id2 = CategoryId.generate();

            assertThat(id1).isNotEqualTo(id2);
        }
    }
}
