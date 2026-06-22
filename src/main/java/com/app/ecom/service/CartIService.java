package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartIService {
    private final CartItemRepository cartItemRepo ;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepo.findById(request.getProductId());
        if(productOpt.isEmpty())
            return false;
        Product product = productOpt.get();

        if(product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOpt = userRepo.findById(request.getProductId());
        if(userOpt.isEmpty())
            return false;
        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepo.findByUserAndProduct(user,product);

        if(existingCartItem != null){
            // update cart item's quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepo.save(existingCartItem);
        } else {
            // add new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepo.save(cartItem);
        }

        return true;
    }
}
