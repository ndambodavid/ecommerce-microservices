package com.ndambodevs.order.response;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
