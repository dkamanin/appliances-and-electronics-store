/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.orders.api.event;

import io.hexlet.maconi.shared.event.IntegrationEvent;
import io.hexlet.maconi.shared.identifiers.CustomerId;
import io.hexlet.maconi.shared.identifiers.EventId;
import io.hexlet.maconi.shared.identifiers.OrderId;
import io.hexlet.maconi.shared.money.Money;
import java.time.Instant;

public record OrderPaidIntegrationEvent(
        EventId eventId, OrderId orderId, CustomerId customerId, Money amount, Instant occurredOn)
        implements IntegrationEvent {}
