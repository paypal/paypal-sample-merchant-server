package com.braintree.braintreep4psamplemerchant.CreateOrder;

import com.fasterxml.jackson.annotation.JsonProperty;

class Payee {

    private String emailAddress;

    @JsonProperty("email_address")
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty("email_address")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
