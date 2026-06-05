/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.catalog.api.dto;

import io.hexlet.maconi.shared.identifiers.ProductId;
import io.hexlet.maconi.shared.money.Money;

public record ProductSummary(ProductId id, String name, Money price, boolean available) {}
