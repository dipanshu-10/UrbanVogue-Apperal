package com.UrbanVogue.payment.service;

import com.UrbanVogue.payment.dto.PaymentRequestDTO;
import com.UrbanVogue.payment.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {

        Random random = new Random();
        int chance = random.nextInt(100);

        if (chance < 70) {
            return new PaymentResponseDTO("SUCCESS");
        } else {
            return new PaymentResponseDTO("FAILED");
        }
    }
}