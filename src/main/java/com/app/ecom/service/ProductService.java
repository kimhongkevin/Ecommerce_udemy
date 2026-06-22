package com.app.ecom.service;

import com.app.ecom.dto.ProductRequest;
import com.app.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;

    public void createProduct(ProductRequest productRequest){

    }
}
