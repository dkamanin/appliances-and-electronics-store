/*
 * Copyright (c) 2025-2026 Daniel Kamanin and contributors.
 *
 * SPDX-License-Identifier: MIT
 */

package io.hexlet.maconi.shared.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.hexlet.maconi.shared.exceptions.BusinessRuleViolationException;
import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Money")
public class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final BigDecimal TEN = new BigDecimal("10.00");
    private static final BigDecimal TWENTY = new BigDecimal("20.00");
    private static final BigDecimal NEGATIVE_TEN = new BigDecimal("-10.00");
    private static final BigDecimal NEGATIVE_TWENTY = new BigDecimal("-20.00");

    @Nested
    @DisplayName("Money creation")
    class Creation {

        @Test
        @DisplayName("should throw DomainValidationException when currency is null")
        void shouldThrowWhenCurrencyIsNull() {
            assertThatThrownBy(() -> Money.of(null, TEN))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Currency must not be null");
        }

        @Test
        @DisplayName("should throw DomainValidationException when amount is null")
        void shouldThrowWhenAmountIsNull() {
            assertThatThrownBy(() -> Money.of(USD, null))
                    .isInstanceOf(DomainValidationException.class)
                    .hasMessage("Amount must not be null");
        }

        @Test
        @DisplayName("should create money with given currency and amount")
        void shouldCreateWithGivenCurrencyAndAmount() {
            Money actual = Money.of(USD, TEN);
            Money expected = Money.of(USD, TEN);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("should create money with given currency and negative amount")
        void shouldCreateWithNegativeAmount() {
            Money actual = Money.of(USD, NEGATIVE_TEN);
            Money expected = Money.of(USD, NEGATIVE_TEN);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("should always store amount with scale of two")
        void shouldStoreAmountWithScaleOfTwo() {
            Money money = Money.of(USD, TEN);

            assertThat(money.amount().scale()).isEqualTo(2);
        }

        @Test
        @DisplayName("should round amount to two decimal places using HALF_EVEN during creation")
        void shouldRoundAmountToTwoDecimalPlacesUsingHalfEven() {
            Money actual = Money.of(USD, new BigDecimal("99.995"));
            Money expected = Money.of(USD, new BigDecimal("100.00"));

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("should create zero money")
        void shouldCreateZeroMoney() {
            Money actual = Money.zero();
            Money expected = Money.of(USD, BigDecimal.ZERO);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("should create money with different input scales")
        void shouldCreateWithDifferentInputScales() {
            Money actual = Money.of(USD, new BigDecimal("50.1"));
            Money expected = Money.of(USD, new BigDecimal("50.10"));

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Money operations")
    class Operations {

        @Nested
        @DisplayName("When currencies are the same")
        class SameCurrency {
            @Nested
            @DisplayName("Addition operations")
            class Addition {

                @Test
                @DisplayName("should throw when adding null money")
                void shouldThrowWhenAddingNullMoney() {
                    Money money = Money.of(USD, TEN);
                    assertThatThrownBy(() -> money.add(null))
                            .isInstanceOf(NullPointerException.class);
                }

                @Test
                @DisplayName("should add two positive monies with same currency")
                void shouldAddTwoPositiveMonies() {
                    Money first = Money.of(USD, TEN);
                    Money second = Money.of(USD, TWENTY);
                    Money actual = first.add(second);
                    Money expected = Money.of(USD, new BigDecimal("30.00"));

                    assertThat(actual).isEqualTo(expected);
                }

                @Test
                @DisplayName("should add positive and negative monies with same currency")
                void shouldAddPositiveAndNegativeMonies() {
                    Money positive = Money.of(USD, TEN);
                    Money negative = Money.of(USD, NEGATIVE_TEN);
                    Money actual = positive.add(negative);

                    assertThat(actual).isEqualTo(Money.zero());
                }

                @Test
                @DisplayName("should add zero and return same money")
                void shouldAddZeroAndReturnSameMoney() {
                    Money money = Money.of(USD, TEN);
                    Money zero = Money.zero();
                    Money actual = money.add(zero);

                    assertThat(actual).isEqualTo(money);
                }
            }

            @Nested
            @DisplayName("Negation operations")
            class Negation {

                @Test
                @DisplayName("should negate positive money to negative")
                void shouldNegatePositiveMoneyToNegative() {
                    Money positive = Money.of(USD, TEN);
                    Money actual = positive.negate();
                    Money expected = Money.of(USD, NEGATIVE_TEN);

                    assertThat(actual).isEqualTo(expected);
                }

                @Test
                @DisplayName("should negate negative money to positive")
                void shouldNegateNegativeMoneyToPositive() {
                    Money negative = Money.of(USD, NEGATIVE_TEN);
                    Money actual = negative.negate();
                    Money expected = Money.of(USD, TEN);

                    assertThat(actual).isEqualTo(expected);
                }

                @Test
                @DisplayName("should negate zero money to zero")
                void shouldNegateZero() {
                    Money zero = Money.zero();
                    Money actual = zero.negate();

                    assertThat(actual).isEqualTo(zero);
                }
            }

            @Nested
            @DisplayName("Subtraction operations")
            class Subtraction {

                @Test
                @DisplayName("should throw when subtracting null money")
                void shouldThrowWhenSubtractingNullMoney() {
                    Money money = Money.of(USD, TEN);
                    assertThatThrownBy(() -> money.subtract(null))
                            .isInstanceOf(NullPointerException.class);
                }

                @Test
                @DisplayName("should subtract smaller from larger and return positive result")
                void shouldSubtractSmallerFromLargerReturningPositive() {
                    Money larger = Money.of(USD, TWENTY);
                    Money smaller = Money.of(USD, TEN);
                    Money actual = larger.subtract(smaller);

                    assertThat(actual).isEqualTo(Money.of(USD, TEN));
                }

                @Test
                @DisplayName("should subtract larger from smaller and return negative result")
                void shouldSubtractLargerFromSmallerReturningNegative() {
                    Money smaller = Money.of(USD, TEN);
                    Money larger = Money.of(USD, TWENTY);
                    Money actual = smaller.subtract(larger);

                    assertThat(actual).isEqualTo(Money.of(USD, NEGATIVE_TEN));
                }

                @Test
                @DisplayName("should subtract equal monies and return zero result")
                void shouldSubtractEqualMoniesReturningZero() {
                    Money first = Money.of(USD, TEN);
                    Money second = Money.of(USD, TEN);
                    Money actual = first.subtract(second);

                    assertThat(actual).isEqualTo(Money.zero());
                }

                @Test
                @DisplayName("should subtract zero and return same money")
                void shouldSubtractZeroAndReturnSameMoney() {
                    Money money = Money.of(USD, TEN);
                    Money zero = Money.zero();
                    Money actual = money.subtract(zero);

                    assertThat(actual).isEqualTo(money);
                }
            }

            @Nested
            @DisplayName("Multiplication operations")
            class Multiplication {

                @Test
                @DisplayName("should throw when multiplying by null")
                void shouldThrowWhenMultiplyingByNull() {
                    Money money = Money.of(USD, TEN);
                    assertThatThrownBy(() -> money.multiply(null))
                            .isInstanceOf(NullPointerException.class);
                }

                @Test
                @DisplayName("should multiply by BigDecimal correctly")
                void shouldMultiplyByBigDecimalCorrectly() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.multiply(new BigDecimal("2"));
                    assertThat(actual).isEqualTo(Money.of(USD, TWENTY));
                }

                @Test
                @DisplayName("should multiply by long correctly")
                void shouldMultiplyByLongCorrectly() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.multiply(3L);

                    assertThat(actual).isEqualTo(Money.of(USD, new BigDecimal("30.00")));
                }

                @Test
                @DisplayName("should multiply by one and return same money")
                void shouldMultiplyByOneAndReturnSameMoney() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.multiply(BigDecimal.ONE);

                    assertThat(actual).isEqualTo(money);
                }

                @Test
                @DisplayName("should multiply by one (long) and return same money")
                void shouldMultiplyByOneLongAndReturnSameMoney() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.multiply(1L);

                    assertThat(actual).isEqualTo(money);
                }

                @Test
                @DisplayName("should multiply by zero and return zero")
                void shouldMultiplyByZeroAndReturnZero() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.multiply(BigDecimal.ZERO);
                    assertThat(actual).isEqualTo(Money.zero());
                }

                @Test
                @DisplayName("should multiply by zero (long) and return zero")
                void shouldMultiplyByZeroLongAndReturnZero() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.multiply(0L);
                    assertThat(actual).isEqualTo(Money.zero());
                }

                @Test
                @DisplayName("should divide zero by non-zero and return zero")
                void shouldDivideZeroByNonZeroAndReturnZero() {
                    Money zero = Money.zero();
                    Money actual = zero.divide(5L);
                    assertThat(actual).isEqualTo(zero);
                }

                @Test
                @DisplayName("should multiply by 2.5 and apply banker's rounding")
                void shouldMultiplyWithFractionalFactorAndApplyBankersRounding() {
                    Money money = Money.of(USD, new BigDecimal("10.01"));
                    Money actual = money.multiply(new BigDecimal("2.5"));
                    Money expected = Money.of(USD, new BigDecimal("25.02"));

                    assertThat(actual).isEqualTo(expected);
                }

                @Test
                @DisplayName(
                        "should round multiplication result correctly with fractional multiplier")
                void shouldRoundMultiplicationResultCorrectlyWithFractionalFactor() {
                    Money money = Money.of(USD, new BigDecimal("10.01"));
                    Money actual = money.multiply(new BigDecimal("0.1"));
                    Money expected = Money.of(USD, new BigDecimal("1.00"));

                    assertThat(actual).isEqualTo(expected);
                }
            }

            @Nested
            @DisplayName("division")
            class Division {

                @Test
                @DisplayName("should throw when dividing by null")
                void shouldThrowWhenDividingByNull() {
                    Money money = Money.of(USD, TEN);
                    assertThatThrownBy(() -> money.divide(null))
                            .isInstanceOf(NullPointerException.class);
                }

                @Test
                @DisplayName("should throw when dividing by zero")
                void shouldThrowWhenDividingByZero() {
                    Money money = Money.of(USD, TEN);

                    assertThatThrownBy(() -> money.divide(BigDecimal.ZERO))
                            .isInstanceOf(DomainValidationException.class)
                            .hasMessage("Cannot divide by zero");
                }

                @Test
                @DisplayName("should divide by BigDecimal correctly")
                void shouldDivideByBigDecimalCorrectly() {
                    Money money = Money.of(USD, TWENTY);
                    Money actual = money.divide(new BigDecimal("2"));

                    assertThat(actual).isEqualTo(Money.of(USD, TEN));
                }

                @Test
                @DisplayName("should divide by long correctly")
                void shouldDivideByLongCorrectly() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.divide(2L);

                    assertThat(actual).isEqualTo(Money.of(USD, new BigDecimal("5.00")));
                }

                @Test
                @DisplayName("should divide and round result correctly")
                void shouldDivideAndRoundCorrectly() {
                    Money money = Money.of(USD, TEN);
                    Money actual = money.divide(new BigDecimal("3"));
                    Money expected = Money.of(USD, new BigDecimal("3.33"));

                    assertThat(actual).isEqualTo(expected);
                }

                @Test
                @DisplayName("should divide zero by non-zero and return zero")
                void shouldDivideZeroByNonZeroAndReturnZero() {
                    Money zero = Money.zero();
                    Money actual = zero.divide(5L);
                    assertThat(actual).isEqualTo(zero);
                }
            }

            @Nested
            @DisplayName("absolute value")
            class AbsoluteValue {

                @Test
                @DisplayName("should return positive money when amount is negative")
                void shouldReturnPositiveMoneyForNegativeAmount() {
                    Money negative = Money.of(USD, NEGATIVE_TEN);
                    Money expected = Money.of(USD, TEN);
                    Money actual = negative.abs();

                    assertThat(actual).isEqualTo(expected);
                }

                @Test
                @DisplayName("should return equivalent money when amount is positive")
                void shouldReturnSameMoneyForPositiveAmount() {
                    Money positive = Money.of(USD, TEN);
                    Money actual = positive.abs();

                    assertThat(actual).isEqualTo(positive);
                }

                @Test
                @DisplayName("should return zero when money is zero")
                void shouldReturnZeroForZeroAmount() {
                    Money zero = Money.zero();
                    Money actual = zero.abs();

                    assertThat(actual).isEqualTo(zero);
                }
            }
        }

        @Nested
        @DisplayName("When currencies are different")
        class CrossCurrency {
            @Test
            @DisplayName("should throw when adding different currencies")
            void shouldThrowWhenAddingDifferentCurrencies() {
                Currency eur = Currency.getInstance("EUR");
                Money usd = Money.of(USD, TEN);
                Money eurMoney = Money.of(eur, TEN);
                assertThatThrownBy(() -> usd.add(eurMoney))
                        .isInstanceOf(BusinessRuleViolationException.class)
                        .hasMessage("Cannot operate on different currencies");
            }

            @Test
            @DisplayName("should throw when subtracting different currencies")
            void shouldThrowWhenSubtractingDifferentCurrencies() {
                Currency eur = Currency.getInstance("EUR");
                Money usd = Money.of(USD, TEN);
                Money eurMoney = Money.of(eur, TEN);
                assertThatThrownBy(() -> usd.subtract(eurMoney))
                        .isInstanceOf(BusinessRuleViolationException.class)
                        .hasMessage("Cannot operate on different currencies");
            }

            @Test
            @DisplayName("should throw when comparing different currencies")
            void shouldThrowWhenComparingDifferentCurrencies() {
                Currency eur = Currency.getInstance("EUR");
                Money usd = Money.of(USD, TEN);
                Money eurMoney = Money.of(eur, TEN);
                assertThatThrownBy(() -> usd.isGreaterThan(eurMoney))
                        .isInstanceOf(BusinessRuleViolationException.class)
                        .hasMessage("Cannot operate on different currencies");
            }

            @Test
            @DisplayName("should throw when comparing with compareTo and different currencies")
            void shouldThrowWhenComparingWithCompareToAndDifferentCurrencies() {
                Currency eur = Currency.getInstance("EUR");
                Money usd = Money.of(USD, TEN);
                Money eurMoney = Money.of(eur, TEN);
                assertThatThrownBy(() -> usd.compareTo(eurMoney))
                        .isInstanceOf(BusinessRuleViolationException.class)
                        .hasMessage("Cannot compare Money objects with different currencies");
            }
        }
    }

    @Nested
    @DisplayName("Money queries")
    class Queries {

        @Test
        @DisplayName("should return true when amount is zero")
        void shouldReturnTrueWhenAmountIsZero() {
            assertThat(Money.zero().isZero()).isTrue();
            assertThat(Money.of(USD, TEN).isZero()).isFalse();
        }

        @Test
        @DisplayName("should return true when amount is negative")
        void shouldReturnTrueWhenAmountIsNegative() {
            assertThat(Money.of(USD, NEGATIVE_TEN).isNegative()).isTrue();
            assertThat(Money.zero().isNegative()).isFalse();
        }

        @Test
        @DisplayName("should return true when amount is positive")
        void shouldReturnTrueWhenAmountIsPositive() {
            assertThat(Money.of(USD, TEN).isPositive()).isTrue();
            assertThat(Money.of(USD, NEGATIVE_TEN).isPositive()).isFalse();
        }

        @Nested
        @DisplayName("comparisons")
        class Comparisons {

            @Test
            @DisplayName("should throw when comparing with null")
            void shouldThrowWhenComparingWithNull() {
                Money money = Money.of(USD, TEN);
                assertThatThrownBy(() -> money.isGreaterThan(null))
                        .isInstanceOf(NullPointerException.class);
                assertThatThrownBy(() -> money.isGreaterThanOrEqual(null))
                        .isInstanceOf(NullPointerException.class);
            }

            @Test
            @DisplayName("should return true when money is greater than another")
            void shouldReturnTrueWhenMoneyIsGreaterThanOther() {
                Money larger = Money.of(USD, TWENTY);
                Money smaller = Money.of(USD, TEN);

                assertThat(larger.isGreaterThan(smaller)).isTrue();
                assertThat(smaller.isGreaterThan(larger)).isFalse();
                assertThat(larger.isGreaterThan(larger)).isFalse();
            }

            @Test
            @DisplayName(
                    "should return true when less negative money "
                            + "is greater than more negative money")
            void shouldReturnTrueWhenLessNegativeIsGreaterThanMoreNegative() {
                Money lessNegative = Money.of(USD, NEGATIVE_TEN);
                Money moreNegative = Money.of(USD, NEGATIVE_TWENTY);

                assertThat(lessNegative.isGreaterThan(moreNegative)).isTrue();
            }

            @Test
            @DisplayName("should return true when money is greater than or equal to another")
            void shouldReturnTrueWhenMoneyIsGreaterThanOrEqual() {
                Money larger = Money.of(USD, TWENTY);
                Money smaller = Money.of(USD, TEN);

                assertThat(larger.isGreaterThanOrEqual(smaller)).isTrue();
                assertThat(larger.isGreaterThanOrEqual(larger)).isTrue();
                assertThat(smaller.isGreaterThanOrEqual(larger)).isFalse();
            }

            @Nested
            @DisplayName("Cross currency comparisons")
            class CrossCurrencyComparisons {
                @Test
                @DisplayName("should throw when isGreaterThan with different currencies")
                void shouldThrowWhenIsGreaterThanWithDifferentCurrencies() {
                    Currency eur = Currency.getInstance("EUR");
                    Money usd = Money.of(USD, TEN);
                    Money eurMoney = Money.of(eur, TEN);
                    assertThatThrownBy(() -> usd.isGreaterThan(eurMoney))
                            .isInstanceOf(BusinessRuleViolationException.class)
                            .hasMessage("Cannot operate on different currencies");
                }

                @Test
                @DisplayName("should throw when isGreaterThanOrEqual with different currencies")
                void shouldThrowWhenIsGreaterThanOrEqualWithDifferentCurrencies() {
                    Currency eur = Currency.getInstance("EUR");
                    Money usd = Money.of(USD, TEN);
                    Money eurMoney = Money.of(eur, TEN);
                    assertThatThrownBy(() -> usd.isGreaterThanOrEqual(eurMoney))
                            .isInstanceOf(BusinessRuleViolationException.class)
                            .hasMessage("Cannot operate on different currencies");
                }
            }
        }

        @Nested
        @DisplayName("CompareTo contract")
        class CompareToContract {

            @Test
            @DisplayName("throws exception when argument is null")
            void rejectsOnNullValue() {
                Money money = Money.of(USD, TEN);

                assertThatThrownBy(() -> money.compareTo(null))
                        .isInstanceOf(NullPointerException.class);
            }

            @Test
            @DisplayName("is reflexive")
            void shouldBeReflexive() {
                Money money = Money.of(USD, TEN);

                assertThat(money.compareTo(money)).isZero();
            }

            @Test
            @DisplayName("returns opposite signs when arguments are swapped")
            void shouldReturnOppositeSignsWhenArgumentsAreSwapped() {
                Money smaller = Money.of(USD, TEN);
                Money larger = Money.of(USD, TWENTY);

                assertThat(smaller.compareTo(larger)).isNegative();
                assertThat(larger.compareTo(smaller)).isPositive();
            }

            @Test
            @DisplayName("is transitive")
            void shouldBeTransitive() {
                Money a = Money.of(USD, TEN);
                Money b = Money.of(USD, TWENTY);
                Money c = Money.of(USD, new BigDecimal("30"));

                assertThat(a.compareTo(b)).isNegative();
                assertThat(b.compareTo(c)).isNegative();
                assertThat(a.compareTo(c)).isNegative();
            }

            @Test
            @DisplayName("preserves transitivity when equal elements are present")
            void shouldPreserveTransitivityWithEqualElements() {
                Money a = Money.of(USD, TEN);
                Money b = Money.of(USD, TEN);
                Money c = Money.of(USD, TWENTY);

                assertThat(a.compareTo(b)).isZero();
                assertThat(b.compareTo(c)).isNegative();
                assertThat(a.compareTo(c)).isNegative();
            }

            @Test
            @DisplayName("returns zero if and only if the objects are equal")
            void shouldReturnZeroExactlyWhenObjectsAreEqual() {
                Money money1 = Money.of(USD, TEN);
                Money money2 = Money.of(USD, TEN);
                Money money3 = Money.of(USD, TWENTY);

                assertThat(money1.compareTo(money2)).isZero();
                assertThat(money1.equals(money2)).isTrue();

                assertThat(money1.compareTo(money3)).isNotZero();
                assertThat(money1.equals(money3)).isFalse();
            }

            @Test
            @DisplayName("compareTo result is consistent with equals for equal instances")
            void shouldBeConsistentWithEqualsForEqualInstances() {
                Money a = Money.of(USD, TEN);
                Money b = Money.of(USD, TEN);
                Money c = Money.of(USD, TWENTY);

                assertThat(a.compareTo(b)).isZero();
                assertThat(a.compareTo(c)).isEqualTo(b.compareTo(c));
            }
        }

        @Nested
        @DisplayName("equals and hashCode")
        class EqualsHashCode {

            @Test
            @DisplayName("should be equal for same amount with different scales")
            void shouldBeEqualForDifferentScales() {
                Money m1 = Money.of(USD, new BigDecimal("10.0"));
                Money m2 = Money.of(USD, new BigDecimal("10.00"));

                assertThat(m1).isEqualTo(m2);
                assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
            }

            @Test
            @DisplayName("should be equal for zero with different scales")
            void shouldBeEqualForZeroDifferentScales() {
                Money m1 = Money.of(USD, BigDecimal.ZERO);
                Money m2 = Money.of(USD, new BigDecimal("0.00"));

                assertThat(m1).isEqualTo(m2);
                assertThat(m1.hashCode()).isEqualTo(m2.hashCode());
            }

            @Test
            @DisplayName("should not be equal for different currencies")
            void shouldBeNotEqualForDifferentCurrencies() {
                Currency eur = Currency.getInstance("EUR");
                Money usdMoney = Money.of(USD, TEN);
                Money eurMoney = Money.of(eur, TEN);

                assertThat(usdMoney).isNotEqualTo(eurMoney);
                assertThat(usdMoney.hashCode()).isNotEqualTo(eurMoney.hashCode());
            }
        }

        @Nested
        @Disabled(
                "Only USD currency exists in the system, "
                        + "cannot test comparison across different currencies")
        @DisplayName("Money comparison across currencies")
        class CrossCurrencyComparison {}
    }

    @Nested
    @DisplayName("Money formatting")
    class Formatting {

        @Test
        @DisplayName("should format positive USD money")
        void shouldFormatPositiveUsdMoney() {
            Money money = Money.of(USD, TEN);
            String expected = "$10.00";
            String actual = money.format();

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("should format negative USD money")
        void shouldFormatNegativeUsdMoney() {
            Money money = Money.of(USD, NEGATIVE_TEN);
            String expected = "-$10.00";
            String actual = money.format();

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("should format zero USD money")
        void shouldFormatZeroMoney() {
            Money zero = Money.zero();
            String expected = "$0.00";
            String actual = zero.format();
            assertThat(actual).isEqualTo(expected);
        }
    }
}
