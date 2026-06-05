/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api.dto;

import io.hexlet.maconi.shared.identifiers.CustomerId;
import java.util.List;

public record PlaceOrderCommand(
        CustomerId customerId, String shippingAddress, List<PlaceOrderItem> items) {}
