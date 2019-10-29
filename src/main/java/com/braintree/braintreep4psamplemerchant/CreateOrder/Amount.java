package com.braintree.braintreep4psamplemerchant.CreateOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amount {

    private String currencyCode;
    private String value;

    @JsonProperty("currency_code")
    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonProperty("currency_code")
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
