package com.UrbanVogue.payment.service;

import com.UrbanVogue.payment.dto.PaymentRequestDTO;
import com.UrbanVogue.payment.dto.PaymentResponseDTO;
import com.UrbanVogue.payment.entity.Payment;
import com.UrbanVogue.payment.exception.PaymentProcessingException;
import com.UrbanVogue.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        System.out.println("request_id:: "+request.getOrderId());
        System.out.println("idempotency ::"+request.getIdempotencyKey());

        try {
            if (request.getIdempotencyKey() == null || request.getIdempotencyKey().isBlank()) {
                throw new PaymentProcessingException("Idempotency key is required");
            }

            Optional<Payment> existingPayment =
                    paymentRepository.findByIdempotencyKey(request.getIdempotencyKey());

            if (existingPayment.isPresent()) {
                return new PaymentResponseDTO(existingPayment.get().getStatus());
            }

            boolean isSuccess = Math.random() > 0.3; // 70% success rate
            String status = isSuccess ? "SUCCESS" : "FAILED";

            boolean modeOfPayProbability = Math.random() > 0.5; // 70% success rate
            String modeOfPay = modeOfPayProbability ? "UPI" : "INTERNET_BANKING";

            String transactionId = UUID.randomUUID().toString();

            Payment payment = new Payment();
            payment.setRequestID(request.getOrderId());
            payment.setAmount(request.getAmount());
            payment.setStatus(status);
            payment.setIdempotencyKey(request.getIdempotencyKey());
            payment.setTransactionId(transactionId);
            payment.setPaymentMode(modeOfPay);

            paymentRepository.save(payment);

            return new PaymentResponseDTO(status);

        } catch (DataIntegrityViolationException e) {
            // Race condition case: another request saved same idempotency key first
            Payment alreadySaved =
                    paymentRepository.findByIdempotencyKey(request.getIdempotencyKey())
                            .orElseThrow(() -> new PaymentProcessingException("Duplicate payment detected", e));

            return new PaymentResponseDTO(alreadySaved.getStatus());

        } catch (Exception e) {
            throw new PaymentProcessingException("Payment processing failed: " + e.getMessage(), e);
        }
    }
}