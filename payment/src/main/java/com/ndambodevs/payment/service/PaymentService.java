package com.ndambodevs.payment.service;

import com.ndambodevs.payment.kafka.NotificationProducer;
import com.ndambodevs.payment.mapper.PaymentMapper;
import com.ndambodevs.payment.repository.PaymentRepository;
import com.ndambodevs.payment.request.PaymentNotificationRequest;
import com.ndambodevs.payment.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = this.repository.save(this.mapper.toPayment(request));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }
}
