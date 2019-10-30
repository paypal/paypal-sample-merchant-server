package com.braintree.braintreep4psamplemerchant;

import com.braintree.braintreep4psamplemerchant.CreateOrder.CreateOrderRequest;
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

    @PostMapping(path = "/uat")
    UniversalAccessToken getUat(@RequestParam(value = "countryCode", required = false, defaultValue = "US") String countryCode) {
        System.out.println("******************************");
        System.out.println("REQUEST to /v2/checkout/authorize-order:");
        System.out.println("Country code: " + countryCode);
        return payPalTokenClient.getFullScopedUAT(countryCode);
    }

    @PostMapping(path = "/order")
    Order createOrder(@RequestBody CreateOrderRequest createOrderRequest,
                      @RequestParam(value = "countryCode", required = false, defaultValue = "US") String countryCode) {
        System.out.println("******************************");
        System.out.println("REQUEST to /v2/checkout/orders:");
        System.out.println("Order Request body: " + createOrderRequest.toString());
        System.out.println("Country code: " + countryCode);
        return ordersV2Client.createOrder(createOrderRequest, countryCode);
    }

    @PostMapping("/capture-order")
    Order captureOrder(@RequestBody ProcessOrderRequest processOrderRequest)  {
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
