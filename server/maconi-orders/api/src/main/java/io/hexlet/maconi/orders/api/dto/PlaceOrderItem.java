/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api.dto;

import io.hexlet.maconi.shared.identifiers.ProductId;

public record PlaceOrderItem(ProductId productId, int quantity) {}
