package com.UrbanVogue.user.OrderModule.service;

import com.UrbanVogue.user.OrderModule.client.PaymentClient;
import com.UrbanVogue.user.OrderModule.client.ProductClient;
import com.UrbanVogue.user.OrderModule.dto.*;
import com.UrbanVogue.user.OrderModule.entity.Order;
import com.UrbanVogue.user.OrderModule.repository.OrderRepository;
import com.UrbanVogue.user.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private com.UrbanVogue.user.OrderModule.repository.OrderRepository orderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    //@Qualifier("orderJwtUtil")
    private JwtUtil jwtUtil;

    public OrderResponseDTO placeOrder(OrderRequestDTO request, String token) {

        // extract email from JWT
        String email = jwtUtil.extractEmail(token);

        // fetch product from admin service
        ProductResponseDTO product = productClient.getProduct(request.getProductId(),request.getQuantity());

        if (product == null) {
            return new OrderResponseDTO("Product not found", "FAILED", "FAILED");
        }

        // check stock availability
        //if (request.getQuantity() > product.getNumberOfPieces())
        if(product.getNumberOfPieces()==null){
            return new OrderResponseDTO("Out of stock", "FAILED", "FAILED");
        }

        // calculate total amount
        Double totalAmount = product.getPrice() * request.getQuantity();

        // create order object
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setCustomerEmail(email);
        order.setQuantity(request.getQuantity());
        order.setPrice(product.getPrice());
        order.setProductName(product.getName());
        order.setTotalAmount(totalAmount);
        order.setAddress(request.getAddress());

        // call payment service
        PaymentResponseDTO paymentResponse =
                paymentClient.processPayment(order.getId(), totalAmount);

        if ("SUCCESS".equals(paymentResponse.getStatus())) {

//            // update stock
            productClient.reduceStock(request.getProductId(), request.getQuantity());

            order.setPaymentStatus("SUCCESS");
            order.setOrderStatus("BOOKED");

            orderRepository.save(order);

            return new OrderResponseDTO(
                    "Order placed successfully",
                    "SUCCESS",
                    "BOOKED"
            );

        } else {

            order.setPaymentStatus("FAILED");
            order.setOrderStatus("FAILED");

            orderRepository.save(order);

            return new OrderResponseDTO(
                    "Payment failed, order not booked",
                    "FAILED",
                    "FAILED"
            );
        }
    }
}