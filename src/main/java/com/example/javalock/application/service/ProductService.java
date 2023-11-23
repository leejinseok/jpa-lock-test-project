package com.example.javalock.application.service;

import com.example.javalock.infra.Product;
import com.example.javalock.infra.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void decreaseInventoryWithPessimisticLock(Long id) {
        Product product = productRepository.findByIdWithPessimisticLock(id).orElseThrow();
        product.updateInventory(product.getInventory() - 1);
    }

    @Transactional
    public void decreaseInventoryOptimisticLock(Long id) {
        Product product = productRepository.findByIdWithOptimisticLock(id).orElseThrow();
        product.updateInventory(product.getInventory() - 1);
    }


}
