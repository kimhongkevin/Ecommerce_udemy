package com.app.ecom.dto;

import com.app.ecom.model.OrderStatus;
import com.app.ecom.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private User user;

    private List<OrderItemDTO> items;

    private BigInteger totalAmount;
    private OrderStatus status;
}
