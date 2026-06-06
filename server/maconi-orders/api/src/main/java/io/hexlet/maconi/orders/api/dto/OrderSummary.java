/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api.dto;

import io.hexlet.maconi.shared.identifiers.OrderId;
import io.hexlet.maconi.shared.identifiers.OrderNumber;
import io.hexlet.maconi.shared.money.Money;
import java.time.Instant;

public record OrderSummary(
        OrderId id,
        OrderNumber orderNumber,
        OrderStatus status,
        Money totalAmount,
        Instant createdAt) {}
