/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.inventory.api.dto;

import io.hexlet.maconi.shared.identifiers.ProductId;

public record ReservationItem(ProductId productId, int quantity) {}
