/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api;

import io.hexlet.maconi.orders.api.dto.PlaceOrderCommand;
import io.hexlet.maconi.shared.identifiers.OrderId;

public interface OrderPlacementPort {
    OrderId placeOrder(PlaceOrderCommand command);
}
