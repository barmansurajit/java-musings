package com.cognizant.triton.ddd.valueobjects;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    @Test
    void test_money_addition() {
        Money initialAmount = new Money(BigDecimal.valueOf(10.00), Currency.getInstance(Locale.US));
        Money finalAmount = initialAmount.add(new Money(BigDecimal.valueOf(5.00), Currency.getInstance(Locale.US)));
        assertEquals(BigDecimal.valueOf(15.00), finalAmount.value());
    }

    @Test
    void test_money_deduction() {
        Money initialAmount = new Money(BigDecimal.valueOf(10.00), Currency.getInstance(Locale.US));
        Money finalAmount = initialAmount.subtract(new Money(BigDecimal.valueOf(5.00), Currency.getInstance(Locale.US)));
        assertEquals(BigDecimal.valueOf(5.00), finalAmount.value());
    }

    @Test
    void test_different_currencies() {
        Money initialAmount = new Money(BigDecimal.valueOf(10.00), Currency.getInstance(Locale.US));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            initialAmount.add(new Money(BigDecimal.valueOf(5.00), Currency.getInstance(Locale.UK)));
        });
        assertEquals("Money objects must have the same currency",exception.getMessage());
    }
}