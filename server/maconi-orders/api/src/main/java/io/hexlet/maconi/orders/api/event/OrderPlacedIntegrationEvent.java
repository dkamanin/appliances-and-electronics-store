/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api.event;

import io.hexlet.maconi.orders.api.dto.OrderItemSnapshot;
import io.hexlet.maconi.shared.event.IntegrationEvent;
import io.hexlet.maconi.shared.identifiers.CustomerId;
import io.hexlet.maconi.shared.identifiers.EventId;
import io.hexlet.maconi.shared.identifiers.OrderId;
import io.hexlet.maconi.shared.identifiers.OrderNumber;
import io.hexlet.maconi.shared.money.Money;
import java.time.Instant;
import java.util.List;

public record OrderPlacedIntegrationEvent(
        EventId eventId,
        OrderId orderId,
        OrderNumber orderNumber,
        CustomerId customerId,
        List<OrderItemSnapshot> items,
        Money totalAmount,
        Instant occurredOn)
        implements IntegrationEvent {}
