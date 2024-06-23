package com.ndambodevs.order.service;

import com.ndambodevs.order.exception.BusinessException;
import com.ndambodevs.order.kafka.OrderConfirmation;
import com.ndambodevs.order.kafka.OrderProducer;
import com.ndambodevs.order.mapper.OrderMapper;
import com.ndambodevs.order.repository.OrderRepository;
import com.ndambodevs.order.request.OrderLineRequest;
import com.ndambodevs.order.request.OrderRequest;
import com.ndambodevs.order.request.PaymentRequest;
import com.ndambodevs.order.request.PurchaseRequest;
import com.ndambodevs.order.response.OrderResponse;
import com.ndambodevs.order.restclient.CustomerClient;
import com.ndambodevs.order.restclient.PaymentClient;
import com.ndambodevs.order.restclient.ProductClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public Integer createOrder(HttpHeaders headers, OrderRequest request) {
        var customer = this.customerClient.findCustomerById(headers,request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(headers, paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
