/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.api;

import io.hexlet.maconi.catalog.api.dto.ProductSummary;
import io.hexlet.maconi.shared.identifiers.ProductId;
import java.util.List;

public interface ProductQueryPort {
    List<ProductSummary> findSummariesByIds(List<ProductId> productIds);
}
