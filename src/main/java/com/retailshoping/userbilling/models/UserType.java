package com.retailshoping.userbilling.models;

import java.math.BigDecimal;

public enum UserType {
	EMPLOYEE(BigDecimal.valueOf(0.30)), AFFILIATE(BigDecimal.valueOf(0.10)), CUSTOMER(BigDecimal.valueOf(0));

	UserType(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	private BigDecimal discountPercentage;

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}
}
