package com.UrbanVogue.user.OrderModule.dto;

public class PaymentRequestDTO {

    private Long orderId;
    private Double amount;

    public PaymentRequestDTO(Long orderId, Double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Double getAmount() {
        return amount;
    }
}