package com.retailshoping.userbilling.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Item {

    private ItemType type;
    private BigDecimal price;
}
