/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.cart.api.dto;

import io.hexlet.maconi.shared.identifiers.ProductId;
import io.hexlet.maconi.shared.money.Money;

public record CartItemSnapshot(ProductId productId, int quantity, Money unitPrice) {}
