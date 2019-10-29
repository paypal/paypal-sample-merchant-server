package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;


@Component
class TokenUtil {

    private String clientIdUS;
    private String clientSecretUS;
    private String clientIdGB;
    private String clientSecretGB;

    @Autowired
    public TokenUtil(@Value("${us.client.id}") String clientIdUS,
                     @Value("${us.client.secret}") String clientSecretUS,
                     @Value("${gb.client.id}") String clientIdGB,
                     @Value("${gb.client.secret}") String clientSecretGB) {
        this.clientIdUS = clientIdUS;
        this.clientSecretUS = clientSecretUS;
        this.clientIdGB = clientIdGB;
        this.clientSecretGB = clientSecretGB;
    }

    String getTokenAuthorizationHeaderForLowScope(String countryCode) {
        String clientId = "GB".equalsIgnoreCase(countryCode) ? clientIdGB : clientIdUS;
        return "Basic " + Base64.getEncoder().encodeToString(clientId.getBytes());
    }

    String getTokenAuthorizationHeaderForFullScope(String countryCode) {
        String clientId = "GB".equalsIgnoreCase(countryCode) ? clientIdGB : clientIdUS;
        String clientSecret = "GB".equalsIgnoreCase(countryCode) ? clientSecretGB : clientSecretUS;
        return "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }
}
