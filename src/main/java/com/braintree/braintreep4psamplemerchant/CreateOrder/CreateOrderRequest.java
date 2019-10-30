package com.braintree.braintreep4psamplemerchant.CreateOrder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequest {
    private String intent;
    private List<PurchaseUnit> purchaseUnits;
    private Payee payee;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    @JsonProperty("purchase_units")
    public List<PurchaseUnit> getPurchaseUnits() {
        return purchaseUnits;
    }

    @JsonProperty("purchase_units")
    public void setPurchaseUnits(List<PurchaseUnit> purchaseUnits) {
        this.purchaseUnits = purchaseUnits;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "intent='" + intent + '\'' +
                ", purchaseUnits=" + purchaseUnits +
                ", payee=" + payee +
                '}';
    }
}
