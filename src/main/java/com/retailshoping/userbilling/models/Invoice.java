package com.retailshoping.userbilling.models;

import lombok.Data;

import java.util.List;

@Data
public class Invoice {
    private List<Item> items;
}
