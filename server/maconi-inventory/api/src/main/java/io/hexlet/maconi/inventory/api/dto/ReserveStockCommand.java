/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.inventory.api.dto;

import io.hexlet.maconi.shared.identifiers.OrderId;
import java.util.List;

public record ReserveStockCommand(OrderId orderId, List<ReservationItem> items) {}
