package com.retailshoping.userbilling.models;

public enum ItemType {
    GROCERY(false),
    HEALTH(true),
    TECHNOLOGY(true),
    OTHER(true),
    CLOTHES(true);

    private final boolean percentageBasedDiscount;

    ItemType(boolean percentageBasedDiscount) {
        this.percentageBasedDiscount = percentageBasedDiscount;
    }

    public boolean isPercentageBasedDiscount() {
        return percentageBasedDiscount;
    }
}
