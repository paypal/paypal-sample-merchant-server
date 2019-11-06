package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;


@Component
class TokenUtil {

    private String clientIdUS;
    private String clientSecretUS;
    private String clientIdUK;
    private String clientSecretUK;

    @Autowired
    public TokenUtil(@Value("${us.client.id}") String clientIdUS,
                     @Value("${us.client.secret}") String clientSecretUS,
                     @Value("${uk.client.id}") String clientIdUK,
                     @Value("${uk.client.secret}") String clientSecretUK) {
        this.clientIdUS = clientIdUS;
        this.clientSecretUS = clientSecretUS;
        this.clientIdUK = clientIdUK;
        this.clientSecretUK = clientSecretUK;
    }

    String getTokenAuthorizationHeaderForLowScope(String countryCode) {
        String clientId = "UK".equalsIgnoreCase(countryCode) ? clientIdUK : clientIdUS;
        return "Basic " + Base64.getEncoder().encodeToString(clientId.getBytes());
    }

    String getTokenAuthorizationHeaderForFullScope(String countryCode) {
        String clientId = "UK".equalsIgnoreCase(countryCode) ? clientIdUK : clientIdUS;
        String clientSecret = "UK".equalsIgnoreCase(countryCode) ? clientSecretUK : clientSecretUS;
        return "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }
}
