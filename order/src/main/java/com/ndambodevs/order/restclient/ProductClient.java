package com.ndambodevs.order.restclient;

import com.ndambodevs.order.exception.BusinessException;
import com.ndambodevs.order.request.PurchaseRequest;
import com.ndambodevs.order.response.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
//        headers.set(AUTHORIZATION, "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjamlId0dZaVJ5dTE0Q2dmaW1WTFZkcklKU1hLT1NVUUZBTk1SMFhTZl9JIn0.eyJleHAiOjE3MTkxNDI1OTgsImlhdCI6MTcxOTE0MjI5OCwianRpIjoiMGFjZjY4N2UtMWNmOS00NzFiLWI5MzctOGNjMzI5YWI3OTNkIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDk4L3JlYWxtcy9lY29tbWVyY2UtbXMiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNWY2NTAwYzYtMmFhYi00MDcwLWIwYzUtMDQ4ZGQ3MjNjYjNkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiZWNvbW1lcmNlLWFwaS1tcyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtZWNvbW1lcmNlLW1zIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImVjb21tZXJjZS1hcGktbXMiOnsicm9sZXMiOlsidW1hX3Byb3RlY3Rpb24iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjE3Mi4xOS4wLjEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZWNvbW1lcmNlLWFwaS1tcyIsImNsaWVudEFkZHJlc3MiOiIxNzIuMTkuMC4xIiwiY2xpZW50X2lkIjoiZWNvbW1lcmNlLWFwaS1tcyJ9.mx09F21g5jxo2GRLXHTX5UcNeft-ZwMggYX7_sEeB3n3c-DxlkUGwS3VaKGhD9SFBYWqT6kMnicUSO3GpxcQ3N6e9gNQ2gehBFSXxugdDUCQB42VVqG-Ch70uoVxC068nPBXzXoNbKwpwmaSXxepzR1MO-RAxbs6DjP65JCH3aFNE4Rwoew84ZmtMA8KVHiIogV6wo5dGRTfr8-st2eeLFbFQ-xrZmzzhBqYQ5MhvpUvOxa649-1u1WBq8CgJh0gws2youv-ZXg94SLhw-ebaMlxOen6Lg9mUFZPBivMs1siD0lsxujS4qcBw7S3PoZXH4tBDo1TyGtY3fZOzrjYHQ");
        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
        };
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the products purchase: " + responseEntity.getStatusCode());
        }
        return  responseEntity.getBody();
    }
}
