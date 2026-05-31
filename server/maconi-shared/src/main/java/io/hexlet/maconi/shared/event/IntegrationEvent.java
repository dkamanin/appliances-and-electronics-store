/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.event;

import io.hexlet.maconi.shared.identifiers.EventId;
import java.time.Instant;

public interface IntegrationEvent {
    EventId eventId();

    Instant occurredOn();

    default String eventType() {
        return this.getClass().getSimpleName();
    }
}
