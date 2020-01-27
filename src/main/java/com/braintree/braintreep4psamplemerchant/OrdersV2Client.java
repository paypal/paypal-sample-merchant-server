package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrdersV2Client {

    private static final String ORDERS_V2_PATH = "/v2/checkout/orders";

    private RestTemplate restTemplate;
    private PayPalTokenClient payPalTokenService;
    private String url;

    @Autowired
    public OrdersV2Client(RestTemplate restTemplate,
                          PayPalTokenClient payPalTokenService,
                          @Value("${url}") String url) {
        this.restTemplate = restTemplate;
        this.payPalTokenService = payPalTokenService;
        this.url = url;
    }

    Order createOrder(CreateOrderRequest orderBody, String countryCode) {
        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.add("Authorization", "Bearer " + payPalTokenService.getLowScopedUAT(countryCode).getToken());
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateOrderRequest> orderRequest = new HttpEntity<>(orderBody, orderHeaders);
        ResponseEntity<Order> orderResponse = restTemplate.postForEntity(url + ORDERS_V2_PATH, orderRequest, Order.class);

        System.out.println("OrderID: " + orderResponse.getBody().getId());
        System.out.println("HTTP status code: " + orderResponse.getStatusCode());
        System.out.println("Order response headers" + orderResponse.getHeaders());

        // TODO: add error handling or logging?
        return orderResponse.getBody();
    }

    Order processOrder(ProcessOrderRequest processOrderRequest, String metadataId) {
        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.add("Authorization", "Bearer " + payPalTokenService.getFullScopedUAT(processOrderRequest.getCountryCode()).getToken());
        orderHeaders.add("PayPal-Client-Metadata-Id", metadataId);
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> orderRequest = new HttpEntity<>("", orderHeaders);
        ResponseEntity<Order> orderResponse = restTemplate.postForEntity(url + ORDERS_V2_PATH +"/"+ processOrderRequest.getOrderId() +"/" + processOrderRequest.getIntent(),
                orderRequest,
                Order.class);

        System.out.println("OrderID: " + orderResponse.getBody().getId());
        System.out.println("HTTP status code: " + orderResponse.getStatusCode());
        System.out.println("Order response headers" + orderResponse.getHeaders());

        // TODO: add error handling or logging?
        return orderResponse.getBody();
    }
}
