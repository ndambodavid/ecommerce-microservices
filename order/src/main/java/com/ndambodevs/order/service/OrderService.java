package com.ndambodevs.order.service;

import com.ndambodevs.order.kafka.OrderProducer;
import com.ndambodevs.order.mapper.OrderMapper;
import com.ndambodevs.order.repository.OrderRepository;
import com.ndambodevs.order.restclient.CustomerClient;
import com.ndambodevs.order.restclient.PaymentClient;
import com.ndambodevs.order.restclient.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
}
