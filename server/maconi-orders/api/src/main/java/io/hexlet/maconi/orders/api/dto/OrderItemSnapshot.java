/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api.dto;

import io.hexlet.maconi.shared.identifiers.ProductId;
import io.hexlet.maconi.shared.money.Money;

public record OrderItemSnapshot(
        ProductId productId, String productName, int quantity, Money unitPrice, Money totalPrice) {}
