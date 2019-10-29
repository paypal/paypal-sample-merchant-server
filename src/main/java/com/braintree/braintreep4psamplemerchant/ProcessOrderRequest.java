package com.braintree.braintreep4psamplemerchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessOrderRequest {

    private String orderId;
    private String intent;
    private String countryCode;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(final String intent) {
        this.intent = intent;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }
}
