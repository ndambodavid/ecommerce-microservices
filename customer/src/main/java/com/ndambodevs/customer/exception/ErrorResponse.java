package com.ndambodevs.customer.exception;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
