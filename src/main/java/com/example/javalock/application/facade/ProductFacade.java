package com.example.javalock.application.facade;

import com.example.javalock.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductService productService;

    public void decreaseInventoryWithOptimisticLock(Long id) {
        while (true) {
            try {
                productService.decreaseInventoryOptimisticLock(id);
                break;
            } catch (ObjectOptimisticLockingFailureException e) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
