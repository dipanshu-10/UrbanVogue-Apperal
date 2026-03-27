package com.UrbanVogue.user.OrderModule.dto;

public class PaymentRequestDTO {

    private String orderId;
    private Double amount;

    public PaymentRequestDTO(String orderId, Double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }
}