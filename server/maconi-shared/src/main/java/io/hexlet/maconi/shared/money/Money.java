/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.money;

import io.hexlet.maconi.shared.exceptions.BusinessRuleViolationException;
import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public record Money(Currency currency, BigDecimal amount) implements Comparable<Money> {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;

    public Money {
        if (currency == null) {
            throw new DomainValidationException("Currency must not be null");
        }
        if (amount == null) {
            throw new DomainValidationException("Amount must not be null");
        }
        amount = amount.setScale(SCALE, ROUNDING);
    }

    public static Money zero() {
        return new Money(Currency.USD, BigDecimal.ZERO);
    }

    public Money negate() {
        return new Money(currency, amount.negate());
    }

    public Money abs() {
        return isNegative() ? negate() : this;
    }

    public Money add(Money money) {
        requireSameCurrency(money);
        return new Money(currency, amount.add(money.amount));
    }

    public Money subtract(Money money) {
        requireSameCurrency(money);
        BigDecimal result = amount.subtract(money.amount);
        return new Money(currency, result);
    }

    public Money multiply(BigDecimal multiplier) {
        Objects.requireNonNull(multiplier);
        return new Money(currency, amount.multiply(multiplier));
    }

    public Money multiply(long multiplier) {
        return multiply(BigDecimal.valueOf(multiplier));
    }

    public Money divide(BigDecimal divisor) {
        Objects.requireNonNull(divisor);
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new DomainValidationException("Cannot divide by zero");
        }
        return new Money(currency, amount.divide(divisor, SCALE, ROUNDING));
    }

    public Money divide(long divisor) {
        return divide(BigDecimal.valueOf(divisor));
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money) {
        requireSameCurrency(money);
        return amount.compareTo(money.amount) > 0;
    }

    public boolean isGreaterThanOrEqual(Money money) {
        requireSameCurrency(money);
        return amount.compareTo(money.amount) >= 0;
    }

    public String format() {
        if (currency == Currency.USD) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
            return formatter.format(amount);
        }
        return amount.toString();
    }

    private void requireSameCurrency(Money money) {
        if (!currency.equals(money.currency)) {
            throw new BusinessRuleViolationException("Cannot operate on different currencies");
        }
    }

    @Override
    public int compareTo(@NonNull Money money) {
        if (!currency.equals(money.currency)) {
            return currency.compareTo(money.currency);
        }
        return amount.compareTo(money.amount);
    }
}
