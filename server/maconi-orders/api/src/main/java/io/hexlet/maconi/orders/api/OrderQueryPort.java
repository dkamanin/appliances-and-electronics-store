/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api;

import io.hexlet.maconi.orders.api.dto.OrderSummary;
import io.hexlet.maconi.shared.identifiers.CustomerId;
import io.hexlet.maconi.shared.identifiers.OrderId;
import java.util.List;
import java.util.Optional;

public interface OrderQueryPort {
    Optional<OrderSummary> findSummaryById(OrderId orderId);

    List<OrderSummary> findByCustomerId(CustomerId customerId);
}
