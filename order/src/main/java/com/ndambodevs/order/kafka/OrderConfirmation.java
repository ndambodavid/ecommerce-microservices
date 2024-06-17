package com.ndambodevs.order.kafka;

import com.ndambodevs.order.model.PaymentMethod;
import com.ndambodevs.order.response.CustomerResponse;
import com.ndambodevs.order.response.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
