package com.retailshoping.userbilling.helper;

import com.retailshoping.userbilling.models.Item;
import com.retailshoping.userbilling.models.ItemType;
import com.retailshoping.userbilling.models.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Component
public class RuleProcessor {

    private static final int YEARS_FOR_DISCOUNT = 2;


    public BigDecimal calculateTotal(List<Item> items) {
        return items.stream().map(i -> i.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalPerType(List<Item> items, ItemType type) {
        BigDecimal sum = new BigDecimal(0);
        if (type != null) {
            sum = items.stream()
                    .filter(i -> type.equals(i.getType()))
                    .map(i -> i.getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return sum;
    }

    public BigDecimal getUserDiscount(User user) {
        Optional.ofNullable(user).orElseThrow(() -> new NullPointerException("User cannot be null"));
        return user.getType().getDiscountPercentage();
    }

    public boolean isCustomerSince(LocalDate registeredDate, long years) {
        Period period = Period.between(registeredDate, LocalDate.now());
        return period.getYears() >= years;
    }

    public BigDecimal calculateBillsDiscount(BigDecimal totalAmount, BigDecimal amount, BigDecimal discountAmount) {
        int value = totalAmount.divide(amount).intValue();
        return discountAmount.multiply(new BigDecimal(value));
    }

    public BigDecimal calculateDiscount(BigDecimal amount, BigDecimal discount) {
        if (discount.doubleValue() > 1.0) {
            throw new IllegalArgumentException("Discount cannot be more than 100%");
        }

        BigDecimal x = amount.multiply(discount);
        return amount.subtract(x);
    }
}
