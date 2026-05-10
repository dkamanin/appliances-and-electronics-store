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
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;
import org.jspecify.annotations.NonNull;

public record Money(Currency currency, BigDecimal amount) implements Comparable<Money> {
    private static final Currency USD = Currency.getInstance("USD");
    private static final RoundingMode ROUNDING = RoundingMode.HALF_EVEN;

    public Money {
        if (currency == null) {
            throw new DomainValidationException("Currency must not be null");
        }
        if (amount == null) {
            throw new DomainValidationException("Amount must not be null");
        }
        int scale = currency.getDefaultFractionDigits();
        amount = amount.setScale(scale, ROUNDING);
    }

    public static Money of(Currency currency, BigDecimal amount) {
        return new Money(currency, amount);
    }

    public static Money of(BigDecimal amount) {
        return of(USD, amount);
    }

    public static Money of(long amount) {
        return of(BigDecimal.valueOf(amount));
    }

    public static Money zero() {
        return new Money(USD, BigDecimal.ZERO);
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
        int scale = currency.getDefaultFractionDigits();
        BigDecimal result = amount.divide(divisor, scale, ROUNDING);
        return new Money(currency, result);
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
        if (currency.equals(USD)) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
            return formatter.format(amount);
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(currency);
        return formatter.format(amount);
    }

    private void requireSameCurrency(Money money) {
        if (!currency.equals(money.currency)) {
            throw new BusinessRuleViolationException("Cannot operate on different currencies");
        }
    }

    @Override
    public int compareTo(@NonNull Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new BusinessRuleViolationException(
                    "Cannot compare Money objects with different currencies");
        }
        return this.amount.compareTo(money.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return currency.equals(money.currency) && amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        // Normalize BigDecimal for hashCode to avoid collisions
        return Objects.hash(currency, amount.stripTrailingZeros());
    }
}
