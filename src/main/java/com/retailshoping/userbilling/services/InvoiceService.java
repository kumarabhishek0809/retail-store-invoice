package com.retailshoping.userbilling.services;


import com.retailshoping.userbilling.models.Invoice;
import com.retailshoping.userbilling.models.User;

import java.math.BigDecimal;

public interface InvoiceService {

    BigDecimal generateInvoice(User user, Invoice bill);
}
