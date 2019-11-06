package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Component
public class PayPalTokenClient {

    private static final String TOKEN_URL = "https://api.msmaster.qa.paypal.com/v1/oauth2/token";

    private final RestTemplate restTemplate;
    private final TokenUtil tokenUtil;

    @Autowired
    public PayPalTokenClient(RestTemplate restTemplate, TokenUtil tokenUtil) {
        this.restTemplate = restTemplate;
        this.tokenUtil = tokenUtil;
    }

    public UniversalAccessToken getLowScopedUAT(final String countryCode) {
        return getUAT(tokenUtil.getTokenAuthorizationHeaderForLowScope(countryCode));
    }

    public UniversalAccessToken getFullScopedUAT(final String countryCode) {
        return getUAT(tokenUtil.getTokenAuthorizationHeaderForFullScope(countryCode));
    }

    private UniversalAccessToken getUAT(final String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationHeader);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("response_type", "uat");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<UniversalAccessToken> response = restTemplate.postForEntity(
                TOKEN_URL,
                request,
                UniversalAccessToken.class);

        System.out.println("Universal Access Token: " + response.getBody().getToken());
        return response.getBody();
    }
}