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

@DisplayName("BrandId")
class BrandIdTest {

    @Nested
    @DisplayName("Rejects invalid input")
    class RejectsInvalidInput {

        @Test
        @DisplayName("should reject null UUID")
        void rejectsNullUuid() {
            assertThatThrownBy(() -> new BrandId(null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand identifier must not be null");
        }

        @Test
        @DisplayName("should reject null string")
        void rejectsNullString() {
            assertThatThrownBy(() -> BrandId.of(null))
                    .isExactlyInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("should reject empty string")
        void rejectsEmptyString() {
            assertThatThrownBy(() -> BrandId.of(""))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand identifier must not be blank");
        }

        @Test
        @DisplayName("should reject blank string")
        void rejectsBlankString() {
            assertThatThrownBy(() -> BrandId.of("   "))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Brand identifier must not be blank");
        }

        @Test
        @DisplayName("should reject malformed UUID string")
        void rejectsMalformedUuidString() {
            String malformed = "not-a-uuid";

            assertThatThrownBy(() -> BrandId.of(malformed))
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

            BrandId brandId = new BrandId(uuid);

            assertThat(brandId.value()).isEqualTo(uuid);
        }

        @Test
        @DisplayName("should create from valid UUID string via factory method")
        void createsFromValidUuidStringViaFactory() {
            UUID uuid = UUID.randomUUID();

            BrandId brandId = BrandId.of(uuid.toString());

            assertThat(brandId.value()).isEqualTo(uuid);
        }
    }

    @Nested
    @DisplayName("Generation")
    class Generation {

        @Test
        @DisplayName("should generate valid BrandId")
        void generatesValidBrandId() {
            BrandId brandId = BrandId.generate();

            assertThat(brandId).isNotNull();
            assertThat(brandId.value()).isNotNull();
        }
    }

    @Nested
    @DisplayName("Equality")
    class Equality {

        @Test
        @DisplayName("should be equal when they have the same value")
        void areEqualWhenValuesAreTheSame() {
            UUID uuid = UUID.randomUUID();
            BrandId id1 = new BrandId(uuid);
            BrandId id2 = BrandId.of(uuid.toString());

            assertThat(id1).isEqualTo(id2).hasSameHashCodeAs(id2);
        }

        @Test
        @DisplayName("should not be equal when they have different values")
        void areNotEqualWhenValuesDiffer() {
            BrandId id1 = BrandId.generate();
            BrandId id2 = BrandId.generate();

            assertThat(id1).isNotEqualTo(id2);
        }
    }
}
