/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.cart.api;

import io.hexlet.maconi.cart.api.dto.CartSnapshot;
import io.hexlet.maconi.shared.identifiers.CustomerId;
import java.util.Optional;

public interface CartQueryPort {
    Optional<CartSnapshot> findByCustomerId(CustomerId customerId);
}
