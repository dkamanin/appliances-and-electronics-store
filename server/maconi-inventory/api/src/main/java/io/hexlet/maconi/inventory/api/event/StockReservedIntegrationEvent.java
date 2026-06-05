/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.inventory.api.event;

import io.hexlet.maconi.shared.event.IntegrationEvent;
import io.hexlet.maconi.shared.identifiers.EventId;
import io.hexlet.maconi.shared.identifiers.OrderId;
import java.time.Instant;

public record StockReservedIntegrationEvent(EventId eventId, OrderId orderId, Instant occurredOn)
        implements IntegrationEvent {}
