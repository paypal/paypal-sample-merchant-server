package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RestController
public class BTRestController {

    private RestTemplate restTemplate;
    private String usAuth;
    private String ukAuth;
    private String url;

    @Autowired
    public BTRestController(RestTemplate restTemplate, @Value("${authorization.us}") String usAuth, @Value("${authorization.uk}") String ukAuth, @Value("${url}") String url) {
        this.restTemplate = restTemplate;
        this.usAuth = usAuth;
        this.ukAuth = ukAuth;
        this.url = url;
    }

    @RequestMapping("/")
    String hello() {
        System.out.println("\nHello, World\n");
        return "Hello, World";
    }

    @RequestMapping("/order-validation-info")
    OrderValidationInfo getOrderValidationInfo(@RequestParam(value = "payeeEmail", required = false, defaultValue = "sample@email.com") String payeeEmail, 
                                               @RequestParam(value = "amount", required = false, defaultValue = "10.00") String amount,
                                               @RequestParam(value = "intent", required = false, defaultValue = "CAPTURE") String intent,
                                               @RequestParam(value = "partnerCountry", required = false, defaultValue = "US") String partnerCountry) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "US".equals(partnerCountry) ? usAuth : ukAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("response_type", "uat");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<UniversalAccessToken> response = restTemplate.postForEntity(url + "/v1/oauth2/token", request, UniversalAccessToken.class);
        String uat = response.getBody().getToken();

        HttpHeaders orderHeaders = new HttpHeaders();
        orderHeaders.add("Authorization", "Bearer " + uat);
        orderHeaders.setContentType(MediaType.APPLICATION_JSON);

        String orderBody = "{\n" +
                "  \"intent\":\"" + intent + "\",\n" +
                "  \"purchase_units\":[\n" +
                "    {\n" +
                "      \"payment_group_id\":\"1\",\n" +
                "      \"reference_id\":\"ARG0-586-1531956885075\",\n" +
                "      \"description\":\"DescriptionofPU1\",\n" +
                "      \"custom_id\":\"CUSTOMID1001\",\n" +
                "      \"soft_descriptor\":\"SOFT1001\",\n" +
                "      \"amount\":{\n" +
                "        \"currency_code\":\"USD\",\n" +
                "        \"value\":\"" + amount + "\",\n" +
                "        \"breakdown\":{\n" +
                "          \"item_total\":{\n" +
                "            \"currency_code\":\"USD\",\n" +
                "            \"value\":\"" + amount + "\"\n" +
                "          },\n" +
                "          \"shipping\":{\n" +
                "            \"currency_code\":\"USD\",\n" +
                "            \"value\":\"0\"\n" +
                "          },\n" +
                "          \"handling\":{\n" +
                "            \"currency_code\":\"USD\",\n" +
                "            \"value\":\"0\"\n" +
                "          },\n" +
                "          \"tax_total\":{\n" +
                "            \"currency_code\":\"USD\",\n" +
                "            \"value\":\"0\"\n" +
                "          },\n" +
                "          \"gift_wrap\":{\n" +
                "            \"currency_code\":\"USD\",\n" +
                "            \"value\":\"0\"\n" +
                "          },\n" +
                "          \"shipping_discount\":{\n" +
                "            \"currency_code\":\"USD\",\n" +
                "            \"value\":\"0\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"name\": \"VRLens1\",\n" +
                "          \"description\": \"VRLens1\",\n" +
                "          \"sku\": \"259483234816\",\n" +
                "          \"unit_amount\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"" + amount + "\"\n" +
                "          },\n" +
                "          \"tax\": {\n" +
                "            \"currency_code\": \"USD\",\n" +
                "            \"value\": \"0.00\"\n" +
                "          },\n" +
                "          \"quantity\": \"1\",\n" +
                "          \"category\": \"PHYSICAL_GOODS\",\n" +
                "          \"postback_data\": [\n" +
                "            {\n" +
                "              \"name\": \"order_id\",\n" +
                "              \"value\": \"test1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"site_id\",\n" +
                "              \"value\": \"test2\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"name\": \"external_id\",\n" +
                "              \"value\": \"test3\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ],\n" +
                "      \"payee\": {\n" +
                "       \"email_address\": \"" + payeeEmail + "\"\n" +
                "      },\n" +
                "      \"shipping\":{\n" +
                "        \"address\":{\n" +
                "          \"recipient_name\":\"WilliamWoodbridge\",\n" +
                "          \"phone\":\"02097822101\",\n" +
                "          \"address_line_1\":\"500HillsideStreet\",\n" +
                "          \"address_line_2\":\"#100\",\n" +
                "          \"admin_area_1\":\"CA\",\n" +
                "          \"admin_area_2\":\"SanJose\",\n" +
                "          \"postal_code\":\"95131\",\n" +
                "          \"country_code\":\"US\"\n" +
                "        },\n" +
                "        \"method\":\"USPS\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        System.out.println("******************************");
        System.out.println("\nREQUEST to /v2/checkout/orders:");
        System.out.println("Headers: " + orderHeaders.toString());
        System.out.println("Intent: " + intent);
        System.out.println("Payee Email: " + payeeEmail);
        System.out.println("Amount: " + amount);

        HttpEntity<String> orderRequest = new HttpEntity<>(orderBody, orderHeaders);
        ResponseEntity<Order> orderResponse = restTemplate.postForEntity(url + "/v2/checkout/orders", orderRequest, Order.class);

        System.out.println("OrderID: " + orderResponse.getBody().getId());
        System.out.println("HTTP status code: " + orderResponse.getStatusCode());

        return new OrderValidationInfo(uat, orderResponse.getBody().getId());
    }

    @RequestMapping("/process-order/{orderId}")
    OrderCaptureInfo processOrder(@PathVariable String orderId,
                                  @RequestParam(value = "intent", required = false, defaultValue = "capture") String intent,
                                  @RequestParam(value = "partnerCountry", required = false, defaultValue = "US") String partnerCountry) {
      HttpHeaders orderHeaders = new HttpHeaders();
      orderHeaders.add("Authorization", "US".equals(partnerCountry) ? usAuth : ukAuth);
      orderHeaders.setContentType(MediaType.APPLICATION_JSON);

      System.out.println("******************************");
      System.out.println("\nREQUEST to /v2/checkout/orders/" + orderId + "/" + intent.toLowerCase());
      System.out.println("Intent: " + intent.toLowerCase());
      System.out.println("Headers: " + orderHeaders.toString());

      HttpEntity<String> orderRequest = new HttpEntity<>(null, orderHeaders);
      ResponseEntity<Order> orderResponse = restTemplate.postForEntity(url + "/v2/checkout/orders/" + orderId + "/" + intent.toLowerCase(), orderRequest, Order.class);

      System.out.println("OrderID: " + orderResponse.getBody().getId());
      System.out.println("HTTP status code: " + orderResponse.getStatusCode());
      
	  return new OrderCaptureInfo(orderResponse.getBody().getId(), orderResponse.getBody().getStatus());
    }
}
