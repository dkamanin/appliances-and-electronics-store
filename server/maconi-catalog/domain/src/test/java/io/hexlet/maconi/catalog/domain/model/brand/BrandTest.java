/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.brand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.catalog.domain.model.brand.vo.BrandDescription;
import io.hexlet.maconi.catalog.domain.model.brand.vo.BrandName;
import io.hexlet.maconi.catalog.domain.model.brand.vo.BrandStatus;
import io.hexlet.maconi.catalog.domain.model.shared.Image;
import io.hexlet.maconi.catalog.domain.model.shared.ImageId;
import io.hexlet.maconi.catalog.domain.model.shared.StorageKey;
import io.hexlet.maconi.shared.exceptions.InvalidStateException;
import io.hexlet.maconi.shared.identifiers.BrandId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Brand Aggregate")
class BrandTest {

    private static final BrandId BRAND_ID = BrandId.generate();
    private static final BrandName NAME = new BrandName("Samsung");
    private static final BrandDescription DESCRIPTION = new BrandDescription("South Korean electronics");
    private static final Image LOGO = new Image(ImageId.generate(), new StorageKey("brands/samsung.png"));

    @Nested
    @DisplayName("Creation")
    class Creation {

        @Test
        @DisplayName("should create active brand with all fields")
        void createsWithAllFields() {
            Brand brand = new Brand(BRAND_ID, NAME, DESCRIPTION, LOGO);

            assertThat(brand.getId()).isEqualTo(BRAND_ID);
            assertThat(brand.getName()).isEqualTo(NAME);
            assertThat(brand.getDescription()).contains(DESCRIPTION);
            assertThat(brand.getLogo()).contains(LOGO);
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }

        @Test
        @DisplayName("should create active brand with null optional fields")
        void createsWithNullOptionals() {
            Brand brand = new Brand(BRAND_ID, NAME, null, null);

            assertThat(brand.getDescription()).isEmpty();
            assertThat(brand.getLogo()).isEmpty();
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }

        @Test
        @DisplayName("should reject null id")
        void rejectsNullId() {
            assertThatThrownBy(() -> new Brand(null, NAME, DESCRIPTION, LOGO))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("BrandId cannot be null");
        }

        @Test
        @DisplayName("should reject null name")
        void rejectsNullName() {
            assertThatThrownBy(() -> new Brand(BRAND_ID, null, DESCRIPTION, LOGO))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("BrandName cannot be null");
        }
    }

    @Nested
    @DisplayName("Reconstitution")
    class Reconstitution {

        @Test
        @DisplayName("should reconstitute brand with any status")
        void reconstitutesWithStatus() {
            Brand brand = Brand.reconstitute(BRAND_ID, NAME, DESCRIPTION, LOGO, BrandStatus.ARCHIVED);

            assertThat(brand.getId()).isEqualTo(BRAND_ID);
            assertThat(brand.getName()).isEqualTo(NAME);
            assertThat(brand.getDescription()).contains(DESCRIPTION);
            assertThat(brand.getLogo()).contains(LOGO);
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ARCHIVED);
        }

        @Test
        @DisplayName("should reconstitute brand with null optional fields")
        void reconstitutesWithNullOptionals() {
            Brand brand = Brand.reconstitute(BRAND_ID, NAME, null, null, BrandStatus.ACTIVE);

            assertThat(brand.getDescription()).isEmpty();
            assertThat(brand.getLogo()).isEmpty();
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }

        @Test
        @DisplayName("should reject null status")
        void rejectsNullStatus() {
            assertThatThrownBy(() -> Brand.reconstitute(BRAND_ID, NAME, DESCRIPTION, LOGO, null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("BrandStatus cannot be null");
        }
    }

    @Nested
    @DisplayName("Name changes")
    class NameChanges {

        private final Brand brand = new Brand(BRAND_ID, NAME, DESCRIPTION, LOGO);

        @Test
        @DisplayName("should change name")
        void changesName() {
            BrandName newName = new BrandName("Sony");

            brand.changeName(newName);

            assertThat(brand.getName()).isEqualTo(newName);
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }

        @Test
        @DisplayName("should reject null name")
        void rejectsNullName() {
            assertThatThrownBy(() -> brand.changeName(null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("BrandName cannot be null");
        }
    }

    @Nested
    @DisplayName("Description changes")
    class DescriptionChanges {

        private final Brand brand = new Brand(BRAND_ID, NAME, DESCRIPTION, LOGO);

        @Test
        @DisplayName("should change description")
        void changesDescription() {
            BrandDescription newDescription = new BrandDescription("Premium electronics");

            brand.changeDescription(newDescription);

            assertThat(brand.getDescription()).contains(newDescription);
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }

        @Test
        @DisplayName("should set description to null")
        void setsDescriptionToNull() {
            brand.changeDescription(null);

            assertThat(brand.getDescription()).isEmpty();
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }
    }

    @Nested
    @DisplayName("Logo changes")
    class LogoChanges {

        private final Brand brand = new Brand(BRAND_ID, NAME, DESCRIPTION, LOGO);

        @Test
        @DisplayName("should change logo")
        void changesLogo() {
            Image newLogo = new Image(ImageId.generate(), new StorageKey("brands/sony.png"));

            brand.changeLogo(newLogo);

            assertThat(brand.getLogo()).contains(newLogo);
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }

        @Test
        @DisplayName("should set logo to null")
        void setsLogoToNull() {
            brand.changeLogo(null);

            assertThat(brand.getLogo()).isEmpty();
            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ACTIVE);
        }
    }

    @Nested
    @DisplayName("Archiving")
    class Archiving {

        @Test
        @DisplayName("should archive active brand")
        void archivesActiveBrand() {
            Brand brand = new Brand(BRAND_ID, NAME, DESCRIPTION, LOGO);

            brand.archive();

            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ARCHIVED);
        }

        @Test
        @DisplayName("should allow archiving already archived brand")
        void allowsArchivingArchivedBrand() {
            Brand brand = Brand.reconstitute(BRAND_ID, NAME, DESCRIPTION, LOGO, BrandStatus.ARCHIVED);

            brand.archive();

            assertThat(brand.getStatus()).isEqualTo(BrandStatus.ARCHIVED);
        }
    }

    @Nested
    @DisplayName("Modification guards when archived")
    class ArchivedGuards {

        private final Brand archivedBrand = Brand.reconstitute(BRAND_ID, NAME, DESCRIPTION, LOGO, BrandStatus.ARCHIVED);

        @Test
        @DisplayName("should reject name change")
        void rejectsNameChange() {
            assertThatThrownBy(() -> archivedBrand.changeName(new BrandName("New")))
                    .isInstanceOf(InvalidStateException.class);
        }

        @Test
        @DisplayName("should reject description change")
        void rejectsDescriptionChange() {
            assertThatThrownBy(() -> archivedBrand.changeDescription(new BrandDescription("New")))
                    .isInstanceOf(InvalidStateException.class);
        }

        @Test
        @DisplayName("should reject logo change")
        void rejectsLogoChange() {
            Image newLogo = new Image(ImageId.generate(), new StorageKey("new.png"));

            assertThatThrownBy(() -> archivedBrand.changeLogo(newLogo)).isInstanceOf(InvalidStateException.class);
        }
    }
}
