package com.ndambodevs.customer.response;


import com.ndambodevs.customer.model.Address;

public record CustomerResponse(
    String id,
    String firstname,
    String lastname,
    String email,
    Address address
) {}
