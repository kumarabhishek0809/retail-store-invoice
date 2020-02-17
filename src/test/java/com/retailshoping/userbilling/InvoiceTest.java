package com.retailshoping.userbilling;


import com.retailshoping.userbilling.helper.RuleProcessor;
import com.retailshoping.userbilling.models.*;
import com.retailshoping.userbilling.services.InvoiceService;
import com.retailshoping.userbilling.services.InvoiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class InvoiceTest {

    @Test
    public void testCalculateTotal_GroceriesOnly() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals(300.00, total.doubleValue(), 0);
    }


    @Test
    public void testCalculateTotalNonGroceriesOnly() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.TECHNOLOGY, new BigDecimal(100.0)));

        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateTotal(items);
        assertEquals(300.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotalMix() {
        List<Item> items = new ArrayList<Item>();
        items.add(new Item(ItemType.CLOTHES, new BigDecimal(100.0)));
        items.add(new Item(ItemType.OTHER, new BigDecimal(100.0)));
        items.add(new Item(ItemType.TECHNOLOGY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));
        items.add(new Item(ItemType.GROCERY, new BigDecimal(100.0)));

        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateTotalPerType(items, ItemType.GROCERY);
        assertEquals(200.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateTotal_error() {
        RuleProcessor helper = new RuleProcessor();
        Assertions.assertThrows(NullPointerException.class, () -> helper.getUserDiscount(null));
    }

    @Test
    public void testCalculateDiscount_10pct() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.1));
        assertEquals(900.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_50pct() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000), new BigDecimal(0.5));
        assertEquals(500.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_0pct() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(0.0));
        assertEquals(1000.00, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_100pct() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal total = helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(1.0));
        assertEquals(0.0, total.doubleValue(), 0);
    }

    @Test
    public void testCalculateDiscount_error() {
        RuleProcessor helper = new RuleProcessor();
        Assertions.assertThrows(
                IllegalArgumentException.class , () ->
                        helper.calculateDiscount(new BigDecimal(1000),  new BigDecimal(2.0)));
    }

    @Test
    public void testGetUserSpecificDiscount_affiliate() {
        User user = new User(UserType.AFFILIATE, LocalDate.now());
        RuleProcessor helper = new RuleProcessor();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.1, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_employee() {
        User user = new User(UserType.EMPLOYEE, LocalDate.now());
        RuleProcessor helper = new RuleProcessor();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.3, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_old() {
        LocalDate joinDate = LocalDate.of(2016, 2, 23);
        User user = new User(UserType.CUSTOMER, joinDate);
        RuleProcessor helper = new RuleProcessor();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.05, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_new() {
        User user = new User(UserType.CUSTOMER, LocalDate.now());
        RuleProcessor helper = new RuleProcessor();
        BigDecimal discount = helper.getUserDiscount(user);
        assertEquals(0.0, discount.doubleValue(), 0);
    }

    @Test
    public void testGetUserSpecificDiscount_customer_null_user() {
        RuleProcessor helper = new RuleProcessor();
        Assertions.assertThrows(NullPointerException.class , () -> helper.getUserDiscount(null));
    }

    @Test
    public void testIsCustomerSince() {
        RuleProcessor helper = new RuleProcessor();
        LocalDate joinDate = LocalDate.now();
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertFalse(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_1year() {
        RuleProcessor helper = new RuleProcessor();
        LocalDate joinDate = LocalDate.now().minusYears(1);
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertFalse(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_2years() {
        RuleProcessor helper = new RuleProcessor();
        LocalDate joinDate = LocalDate.now().minusYears(2);
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertTrue(isTwoYearsJoined);
    }

    @Test
    public void testIsCustomerSince_3years() {
        RuleProcessor helper = new RuleProcessor();
        LocalDate joinDate = LocalDate.now().minusYears(3);
        boolean isTwoYearsJoined = helper.isCustomerSince(joinDate, 2);
        assertTrue(isTwoYearsJoined);
    }

    @Test
    public void testCalculateBillsDiscount() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal amount = helper.calculateBillsDiscount(new BigDecimal(1000),  new BigDecimal(100),  new BigDecimal(5));
        assertEquals(50, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_2() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal amount = helper.calculateBillsDiscount(new BigDecimal(1000),  new BigDecimal(50),  new BigDecimal(5));
        assertEquals(100, amount.doubleValue(), 0);
    }

    @Test
    public void testCalculateBillsDiscount_3() {
        RuleProcessor helper = new RuleProcessor();
        BigDecimal amount = helper.calculateBillsDiscount( new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
        assertEquals(280, amount.doubleValue(), 0);
    }

    @Test
	public void testDiscountServiceCalculate() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(ItemType.GROCERY, new BigDecimal(50.0)));
		items.add(new Item(ItemType.TECHNOLOGY, new BigDecimal(200.0)));
		items.add(new Item(ItemType.GROCERY, new BigDecimal(10.0)));

        Invoice bill = new Invoice();
		bill.setItems(items);

        InvoiceService invoiceService = new InvoiceServiceImpl(new RuleProcessor());

		invoiceService.generateInvoice(new User(UserType.CUSTOMER, LocalDate.now()),bill);
		RuleProcessor helper = new RuleProcessor();
		BigDecimal amount = helper.calculateBillsDiscount(new BigDecimal(5632), new BigDecimal(100), new BigDecimal(5));
		assertEquals(280, amount.doubleValue(), 0);
	}

}
