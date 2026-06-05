/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.payment.api.event;

import io.hexlet.maconi.shared.event.IntegrationEvent;
import io.hexlet.maconi.shared.identifiers.EventId;
import io.hexlet.maconi.shared.identifiers.OrderId;
import io.hexlet.maconi.shared.money.Money;
import java.time.Instant;

public record PaymentSucceededIntegrationEvent(
        EventId eventId, OrderId orderId, Money amount, Instant occurredOn)
        implements IntegrationEvent {}
