package com.braintree.braintreep4psamplemerchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final String TOKEN_PATH = "/v1/oauth2/token";

    private final RestTemplate restTemplate;
    private final TokenUtil tokenUtil;
    private final String url;

    @Autowired
    public PayPalTokenClient(RestTemplate restTemplate, TokenUtil tokenUtil, @Value("${url}") String url) {
        this.restTemplate = restTemplate;
        this.tokenUtil = tokenUtil;
        this.url = url;
    }

    public IdToken getLowScopedToken(final String countryCode) {
        return getToken(tokenUtil.getTokenAuthorizationHeaderForLowScope(countryCode));
    }

    public IdToken getFullScopedToken(final String countryCode) {
        return getToken(tokenUtil.getTokenAuthorizationHeaderForFullScope(countryCode));
    }

    private IdToken getToken(final String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationHeader);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("response_type", "id_token");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<IdToken> response = restTemplate.postForEntity(
                url + TOKEN_PATH,
                request,
                IdToken.class);

        System.out.println("id_token: " + response.getBody().getToken());
        return response.getBody();
    }
}
