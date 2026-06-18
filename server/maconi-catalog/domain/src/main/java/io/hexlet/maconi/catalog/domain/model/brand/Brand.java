/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.domain.model.brand;

import io.hexlet.maconi.catalog.domain.model.brand.vo.BrandDescription;
import io.hexlet.maconi.catalog.domain.model.brand.vo.BrandName;
import io.hexlet.maconi.catalog.domain.model.brand.vo.BrandStatus;
import io.hexlet.maconi.catalog.domain.model.shared.Image;
import io.hexlet.maconi.shared.exceptions.InvalidStateException;
import io.hexlet.maconi.shared.identifiers.BrandId;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

public final class Brand {
    private final BrandId id;
    private BrandName name;
    private BrandDescription description;
    private Image logo;
    private BrandStatus status;

    public Brand(
            BrandId brandId,
            BrandName brandName,
            @Nullable BrandDescription brandDescription,
            @Nullable Image brandLogo) {
        this.id = Objects.requireNonNull(brandId, "BrandId cannot be null");
        this.name = Objects.requireNonNull(brandName, "BrandName cannot be null");
        this.description = brandDescription;
        this.logo = brandLogo;
        this.status = BrandStatus.ACTIVE;
    }

    static Brand reconstitute(
            BrandId brandId, BrandName brandName, BrandDescription description, Image logo, BrandStatus status) {
        Brand brand = new Brand(brandId, brandName, description, logo);
        brand.status = Objects.requireNonNull(status, "BrandStatus cannot be null");
        return brand;
    }

    public BrandId getId() {
        return id;
    }

    public BrandName getName() {
        return name;
    }

    public Optional<BrandDescription> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Image> getLogo() {
        return Optional.ofNullable(logo);
    }

    public BrandStatus getStatus() {
        return status;
    }

    public void changeName(BrandName brandName) {
        Objects.requireNonNull(brandName, "BrandName cannot be null");
        ensureNotArchived();
        this.name = brandName;
    }

    public void changeDescription(@Nullable BrandDescription brandDescription) {
        ensureNotArchived();
        this.description = brandDescription;
    }

    public void changeLogo(@Nullable Image brandLogo) {
        ensureNotArchived();
        this.logo = brandLogo;
    }

    private void ensureNotArchived() {
        if (status == BrandStatus.ARCHIVED) {
            throw new InvalidStateException("Cannot update brand in ARCHIVED status");
        }
    }

    public void archive() {
        status = BrandStatus.ARCHIVED;
    }
}
