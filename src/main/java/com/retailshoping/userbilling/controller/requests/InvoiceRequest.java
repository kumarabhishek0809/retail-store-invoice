package com.retailshoping.userbilling.controller.requests;


import com.retailshoping.userbilling.models.User;
import lombok.Data;

@Data
public class InvoiceRequest {

    private User user;
    private Invoice bill;

}
