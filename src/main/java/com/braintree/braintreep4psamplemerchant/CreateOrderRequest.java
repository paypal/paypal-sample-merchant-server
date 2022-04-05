package com.braintree.braintreep4psamplemerchant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderRequest {
    private String intent;
    private List<PurchaseUnit> purchaseUnits;
    private ApplicationContext applicationContext;

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

    @JsonProperty("application_context")
    public ApplicationContext getApplicationContext() { return applicationContext; }

    @JsonProperty("application_context")
    public void setApplicationContext(ApplicationContext applicationContext) { this.applicationContext = applicationContext; }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class PurchaseUnit {
        private Amount amount;
        private Payee payee;

        public Amount getAmount() {
            return amount;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Payee getPayee() { return payee; }

        public void setPayee(Payee payee) { this.payee = payee; }

        public static class Amount {
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

        static class Payee {
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
    }

    public static class ApplicationContext {
        private String returnURL;
        private String cancelURL;

        @JsonProperty("return_url")
        public String getReturnURL() { return returnURL; }

        @JsonProperty("return_url")
        public void setReturnURL(String returnURL) { this.returnURL = returnURL; }

        @JsonProperty("cancel_url")
        public String getCancelURL() { return cancelURL; }

        @JsonProperty("cancel_url")
        public void setCancelURL(String cancelURL) { this.cancelURL = cancelURL; }
    }
}
