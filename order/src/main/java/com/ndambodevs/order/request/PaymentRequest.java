package com.ndambodevs.order.request;

import com.ndambodevs.order.model.PaymentMethod;
import com.ndambodevs.order.response.CustomerResponse;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
