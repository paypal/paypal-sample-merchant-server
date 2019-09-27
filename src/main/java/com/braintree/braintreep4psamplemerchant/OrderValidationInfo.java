package com.braintree.braintreep4psamplemerchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class OrderValidationInfo {

    private String universalAccessToken;
    private String orderId;

    public OrderValidationInfo(String universalAccessToken, String orderId) {
        this.universalAccessToken = universalAccessToken;
        this.orderId = orderId;
    }

    public String getUniversalAccessToken() {
        return universalAccessToken;
    }

    public void setUniversalAccessToken(String universalAccessToken) {
        this.universalAccessToken = universalAccessToken;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
