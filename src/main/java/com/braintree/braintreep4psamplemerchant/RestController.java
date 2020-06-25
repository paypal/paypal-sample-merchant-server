package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private OrdersV2Client ordersV2Client;
    private PayPalTokenClient payPalTokenClient;

    @Autowired
    public RestController(OrdersV2Client ordersV2Client, PayPalTokenClient payPalTokenClient) {
        this.ordersV2Client = ordersV2Client;
        this.payPalTokenClient = payPalTokenClient;
    }

    @GetMapping(path = "/id-token")
    IdToken getIDToken(@RequestParam(value = "countryCode") String countryCode) {
        System.out.println("******************************");
        System.out.println("REQUEST to /v1/oauth2/token:");
        System.out.println("Country code: " + countryCode);
        return payPalTokenClient.getLowScopedToken(countryCode);
    }

    @PostMapping(path = "/order")
    Order createOrder(@RequestBody CreateOrderRequest createOrderRequest,
                      @RequestParam(value = "countryCode") String countryCode) {
        System.out.println("******************************");
        System.out.println("REQUEST to /v2/checkout/orders:");
        System.out.println("Order Request body: " + createOrderRequest.toString());
        System.out.println("Country code: " + countryCode);
        return ordersV2Client.createOrder(createOrderRequest, countryCode);
    }

    @PostMapping(path = "/capture-order")
    Order captureOrder(@RequestBody ProcessOrderRequest processOrderRequest) {
        System.out.println("******************************");
        System.out.println("REQUEST to /v2/checkout/capture-order:");
        System.out.println("Process Order Request body: " + processOrderRequest.toString());
        return ordersV2Client.processOrder(processOrderRequest);
    }

    @PostMapping("/authorize-order")
    Order authorizeOrder(@RequestBody ProcessOrderRequest processOrderRequest) {
        System.out.println("******************************");
        System.out.println("REQUEST to /v2/checkout/authorize-order:");
        System.out.println("Process Order Request body: " + processOrderRequest.toString());
        return ordersV2Client.processOrder(processOrderRequest);
    }
}
