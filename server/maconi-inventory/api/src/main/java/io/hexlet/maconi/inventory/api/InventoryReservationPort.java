/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.inventory.api;

import io.hexlet.maconi.inventory.api.dto.ReserveStockCommand;

public interface InventoryReservationPort {
    boolean reserve(ReserveStockCommand command);
}
