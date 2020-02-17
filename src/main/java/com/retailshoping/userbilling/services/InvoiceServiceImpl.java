package com.retailshoping.userbilling.services;

import com.retailshoping.userbilling.helper.RuleProcessor;
import com.retailshoping.userbilling.models.Invoice;
import com.retailshoping.userbilling.models.ItemType;
import com.retailshoping.userbilling.models.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	private final RuleProcessor ruleProcessor;

	public InvoiceServiceImpl(RuleProcessor ruleProcessor) {
		this.ruleProcessor = ruleProcessor;
	}

	@Override
	public BigDecimal generateInvoice(User user, Invoice bill) {

		BigDecimal totalAmount = ruleProcessor.calculateTotal(bill.getItems());
		BigDecimal groceryAmount = ruleProcessor.calculateTotalPerType(bill.getItems(), ItemType.GROCERY);
		BigDecimal nonGroceryAmount = totalAmount.subtract(groceryAmount);
		BigDecimal userDiscount = ruleProcessor.getUserDiscount(user);
		BigDecimal billsDiscount = ruleProcessor.calculateBillsDiscount(totalAmount, new BigDecimal(100), new BigDecimal(5));
		if (nonGroceryAmount.compareTo(BigDecimal.ZERO) > 0) {
			nonGroceryAmount = ruleProcessor.calculateDiscount(nonGroceryAmount, userDiscount);
		}

		BigDecimal finalAmount = (groceryAmount.add(nonGroceryAmount).subtract(billsDiscount));
		return finalAmount;
	}
}
