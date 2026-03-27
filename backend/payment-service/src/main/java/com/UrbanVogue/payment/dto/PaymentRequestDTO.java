package com.UrbanVogue.payment.dto;

public class PaymentRequestDTO {

    private String orderId;
    private Double amount;

    public PaymentRequestDTO() {}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}