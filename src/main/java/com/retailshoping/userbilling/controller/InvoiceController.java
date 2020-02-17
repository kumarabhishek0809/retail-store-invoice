package com.retailshoping.userbilling.controller;

import com.retailshoping.userbilling.controller.requests.InvoiceRequest;
import com.retailshoping.userbilling.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public BigDecimal generateInvoice(@Valid @RequestBody InvoiceRequest request) {
        return invoiceService.generateInvoice(request.getUser(), request.getBill());
    }

}
