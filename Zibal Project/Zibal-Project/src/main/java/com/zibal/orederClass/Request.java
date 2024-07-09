package com.zibal.orederClass;

public class Request {
    private String merchant;
    private Long amount;
    private String callbackUrl;
    private String orderId;

    public Request(String merchant, long amount, String callbackUrl, String orderId) {
        this.merchant = merchant;
        this.amount = amount;
        this.callbackUrl = callbackUrl;
        this.orderId = orderId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
